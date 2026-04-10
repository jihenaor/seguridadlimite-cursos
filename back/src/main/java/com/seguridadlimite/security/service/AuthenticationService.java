package com.seguridadlimite.security.service;

import com.seguridadlimite.models.personal.application.PersonalService;
import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.security.dto.AuthenticationRequest;
import com.seguridadlimite.security.dto.AuthenticationResponse;
import com.seguridadlimite.security.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private JwtService jwtService;

    /**
     * Login usado por {@code /auth/authenticate}: misma fuente que el flujo principal ({@code sl_personal}),
     * no la tabla {@code users} (Spring Security JDBC), que en este proyecto no se utiliza.
     */
    public AuthenticationResponse login(AuthenticationRequest authRequest) {

        Personal personal = personalService.findByLogin(
                authRequest.getUsername(), authRequest.getPassword());

        if (personal == null) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        Role role = Role.fromPersonalRoleCode(personal.getRole());
        String jwt = jwtService.generateToken(
                authRequest.getUsername(),
                buildExtraClaims(personal, role));

        return new AuthenticationResponse(jwt);
    }

    private static Map<String, Object> buildExtraClaims(Personal personal, Role role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", personal.getNombrecompleto());
        extraClaims.put("role", role.name());
        extraClaims.put("permissions",
                role.getPermissions().stream().map(Enum::name).collect(Collectors.toList()));
        return extraClaims;
    }
}
