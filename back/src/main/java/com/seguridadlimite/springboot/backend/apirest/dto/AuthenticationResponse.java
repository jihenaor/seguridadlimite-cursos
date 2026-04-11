package com.seguridadlimite.springboot.backend.apirest.dto;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.seguridadlimite.models.entity.Empresa;
import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.models.aprendiz.domain.AprendizId;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;

public class AuthenticationResponse {
    private String token;

    /** Rol directo de sl_personal.role (ej. ADMINISTRADOR, INSTRUCTOR, COORDINADOR).
     *  Para Empresa: "C". Para Trabajador: "T". */
    private String role;

    private String id;

    private String numerodocumento;

    private String nombreusuario;

    private String tienepassword;

    private Long idaprendiz;

    private String msg;

    public AuthenticationResponse(String token, Personal p) {
        this.token = token;
        this.numerodocumento = p.getNumerodocumento();
        this.role = p.getRole();   // valor directo de la BD, sin transformaciones
        this.id = p.getId().toString();
        this.nombreusuario = p.getNombrecompleto();
        this.tienepassword = (p.getPassword() == null || p.getPassword().trim().length() == 0) ? "N" : "S";
    }

    public AuthenticationResponse(String token, Empresa p) {
        this.token = token;
        this.role = "C";
        this.id = p.getId().toString();
        this.nombreusuario = p.getNombre();
        this.numerodocumento = p.getNumerodocumento();
    }

    public AuthenticationResponse(String token, Trabajador t) {
        this.token = token;
        this.role = "T";
        this.id = t.getId().toString();
        this.nombreusuario = t.getPrimernombre();
        this.numerodocumento = t.getNumerodocumento();
        this.idaprendiz = AprendizId.toLong(t.getIdaprendiz());
    }

    public AuthenticationResponse(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        msg = sw.toString();
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreusuario() { return nombreusuario; }
    public void setNombreusuario(String nombreusuario) { this.nombreusuario = nombreusuario; }

    public String getNumerodocumento() { return numerodocumento; }
    public void setNumerodocumento(String numerodocumento) { this.numerodocumento = numerodocumento; }

    public String getTienepassword() { return tienepassword; }
    public void setTienepassword(String tienepassword) { this.tienepassword = tienepassword; }

    public Long getIdaprendiz() { return idaprendiz; }
    public void setIdaprendiz(Long idaprendiz) { this.idaprendiz = idaprendiz; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
}
