package com.seguridadlimite.models.personal.application;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.models.personal.infraestructure.PersonalRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonalService {

	private static final Logger log = LoggerFactory.getLogger(PersonalService.class);

	private static final int MIN_PLAIN_PASSWORD_LENGTH = 8;
	private static final int BCRYPT_MAX_BYTES = 72;

	private final PersonalRepository dao;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<Personal> findAll() {
		return (List<Personal>) dao.findAll();
	}

	@Transactional(readOnly = true)
	public Personal findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Personal> findByTipo(String tipo) {
		if (tipo.equals("I")) {
			return dao.findInstructores();
		} else {
			return dao.findSupervisor();
		}
	}

	/**
	 * Busca por documento o email (sin validar contraseña). Usado por el filtro JWT.
	 */
	@Transactional(readOnly = true)
	public Personal findByLogin(String login) {
		if (!StringUtils.hasText(login)) {
			return null;
		}
		return dao.findBylogin(login.trim());
	}

	/**
	 * Autenticación: BCrypt o contraseña legada en texto plano (se re-hashea al primer login correcto).
	 */
	@Transactional
	public Personal authenticate(String login, String rawPassword) {
		if (!StringUtils.hasText(login) || !StringUtils.hasText(rawPassword)) {
			return null;
		}
		Personal p = dao.findBylogin(login.trim());
		if (p == null) {
			log.warn("Autenticación: no hay personal para login (documento o email no coincide)");
			return null;
		}
		String stored = p.getPassword();
		if (stored == null) {
			log.warn("Autenticación: personal id={} sin password en BD", p.getId());
			return null;
		}
		if (isBcryptHash(stored)) {
			if (passwordEncoder.matches(rawPassword, stored)) {
				return p;
			}
			log.warn("Autenticación: contraseña no coincide (hash BCrypt) para personal id={}", p.getId());
			return null;
		}
		if (rawPassword.equals(stored)) {
			p.setPassword(passwordEncoder.encode(rawPassword));
			dao.save(p);
			return p;
		}
		log.warn("Autenticación: contraseña no coincide (legado texto plano) para personal id={}", p.getId());
		return null;
	}

	public boolean matchesJwtSubject(Personal p, String subject) {
		if (p == null || !StringUtils.hasText(subject)) {
			return false;
		}
		String s = subject.trim();
		if (p.getNumerodocumento() != null && s.equals(p.getNumerodocumento().trim())) {
			return true;
		}
		return p.getEmail() != null && s.equalsIgnoreCase(p.getEmail().trim());
	}

	/**
	 * Codifica una contraseña en claro para guardarla (nunca la almacena tal cual).
	 */
	public String encodePlainPassword(String raw) {
		validatePlainPassword(raw);
		return passwordEncoder.encode(raw);
	}

	public void validatePlainPassword(String raw) {
		if (!StringUtils.hasText(raw) || raw.length() < MIN_PLAIN_PASSWORD_LENGTH) {
			throw new IllegalArgumentException(
					"La contraseña debe tener al menos " + MIN_PLAIN_PASSWORD_LENGTH + " caracteres");
		}
		if (raw.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > BCRYPT_MAX_BYTES) {
			throw new IllegalArgumentException("La contraseña es demasiado larga");
		}
	}

	@Transactional
	public Personal updatePasswordIfSubjectMatches(Long id, String jwtSubject, String newPlain, String confirmPlain) {
		if (id == null) {
			throw new IllegalArgumentException("Id requerido");
		}
		if (!StringUtils.hasText(newPlain) || !newPlain.equals(confirmPlain)) {
			throw new IllegalArgumentException("Las contraseñas no coinciden");
		}
		Personal p = findById(id);
		if (p == null || !matchesJwtSubject(p, jwtSubject)) {
			throw new SecurityException("No autorizado para modificar esta cuenta");
		}
		validatePlainPassword(newPlain);
		p.setPassword(passwordEncoder.encode(newPlain));
		return dao.save(p);
	}

	/**
	 * Crea o actualiza personal; la contraseña solo se cambia si viene {@code newPassword} en el cuerpo JSON.
	 */
	@Transactional
	public Personal mergeAndSave(Personal incoming) {
		if (incoming == null) {
			throw new IllegalArgumentException("Datos inválidos");
		}
		Personal target;
		if (incoming.getId() == null) {
			target = new Personal();
			target.setAucodestad(StringUtils.hasText(incoming.getAucodestad()) ? incoming.getAucodestad() : "A");
			target.setPersonaapoyo(StringUtils.hasText(incoming.getPersonaapoyo()) ? incoming.getPersonaapoyo() : "N");
			target.setPersonaautoriza(
					StringUtils.hasText(incoming.getPersonaautoriza()) ? incoming.getPersonaautoriza() : "N");
			target.setResponsableemergencias(
					StringUtils.hasText(incoming.getResponsableemergencias()) ? incoming.getResponsableemergencias() : "N");
			target.setCoordinadoralturas(
					StringUtils.hasText(incoming.getCoordinadoralturas()) ? incoming.getCoordinadoralturas() : "N");
			String initial = incoming.getNewPassword();
			if (!StringUtils.hasText(initial) || isBcryptHash(initial)) {
				throw new IllegalArgumentException("Debe indicar una contraseña inicial (campo newPassword)");
			}
			validatePlainPassword(initial);
			target.setPassword(passwordEncoder.encode(initial));
		} else {
			target = dao.findById(incoming.getId())
					.orElseThrow(() -> new IllegalArgumentException("Personal no encontrado"));
			applyIncomingScalars(incoming, target, false);
			if (StringUtils.hasText(incoming.getNewPassword())) {
				validatePlainPassword(incoming.getNewPassword());
				target.setPassword(passwordEncoder.encode(incoming.getNewPassword()));
			}
		}
		if (incoming.getId() == null) {
			applyIncomingScalars(incoming, target, true);
		}
		return dao.save(target);
	}

	private void applyIncomingScalars(Personal src, Personal dst, boolean isCreate) {
		if (StringUtils.hasText(src.getTipodocumento())) {
			dst.setTipodocumento(src.getTipodocumento());
		}
		if (StringUtils.hasText(src.getNumerodocumento())) {
			dst.setNumerodocumento(src.getNumerodocumento());
		}
		if (StringUtils.hasText(src.getNombrecompleto())) {
			dst.setNombrecompleto(src.getNombrecompleto());
		}
		if (StringUtils.hasText(src.getEntrenador())) {
			dst.setEntrenador(src.getEntrenador());
		}
		if (StringUtils.hasText(src.getSupervisor())) {
			dst.setSupervisor(src.getSupervisor());
		}
		if (StringUtils.hasText(src.getPersonaapoyo())) {
			dst.setPersonaapoyo(src.getPersonaapoyo());
		}
		if (StringUtils.hasText(src.getAucodestad())) {
			dst.setAucodestad(src.getAucodestad());
		}
		if (StringUtils.hasText(src.getEmail())) {
			dst.setEmail(src.getEmail());
		}
		if (StringUtils.hasText(src.getRole())) {
			dst.setRole(src.getRole());
		}
		if (src.getNumerocelular() != null) {
			dst.setNumerocelular(src.getNumerocelular());
		}
		if (StringUtils.hasText(src.getPersonaautoriza())) {
			dst.setPersonaautoriza(src.getPersonaautoriza());
		}
		if (StringUtils.hasText(src.getResponsableemergencias())) {
			dst.setResponsableemergencias(src.getResponsableemergencias());
		}
		if (StringUtils.hasText(src.getCoordinadoralturas())) {
			dst.setCoordinadoralturas(src.getCoordinadoralturas());
		}
		if (isCreate && !StringUtils.hasText(dst.getRole())) {
			dst.setRole("INSTRUCTOR");
		}
	}

	private static boolean isBcryptHash(String s) {
		return s != null && (s.startsWith("$2a$") || s.startsWith("$2b$") || s.startsWith("$2y$"));
	}

	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}

	public Personal findByNumerodocumento(String numerodocumento) {
		return dao.findBynumerodocumento(numerodocumento);
	}

	/**
	 * @deprecated Usar {@link #findByLogin(String)} o {@link #authenticate(String, String)}.
	 */
	@Deprecated
	public Personal findByLogin(String login, String password) {
		if (password == null || password.isEmpty()) {
			return findByLogin(login);
		}
		return authenticate(login, password);
	}

	public String exporterReport(String reportFormat) throws FileNotFoundException, JRException {
		List<Personal> l = (List<Personal>) dao.findAll();
		String pdfBase64 = "";
		File file = ResourceUtils.getFile("classpath:personal.jrxml");

		JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(l);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createBy", "xx");

		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

		pdfBase64 = Base64.getEncoder().encodeToString(pdf);
		return pdfBase64;
	}
}
