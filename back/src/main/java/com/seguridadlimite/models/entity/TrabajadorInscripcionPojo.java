package com.seguridadlimite.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class TrabajadorInscripcionPojo {
	private Long id;
	private String tipodocumento;
	private String numerodocumento;
	private String primernombre;
	private String segundonombre;
	private String primerapellido;
	private String segundoapellido;
	private String nombrecompleto;
	private String genero;
	private String nacionalidad;
	private String tiposangre;
	private String fechanacimiento;
	private String ocupacion;
	private String departamentodomicilio;
	private String ciudaddomicilio;
	private String direcciondomicilio;
	private String celular;
	private String correoelectronico;
	private String adjuntodocumento;
	private String ext;
	private String valido;
	private String base64a;
	private String base64b;
	private String empresa;
	private String nit;
	private String representantelegal;
	private String exception;
	private Long idaprendiz;
	private Long idenfasis;
	private String nombreenfasis;
	@Size(max = 40)
	private String otroenfasis;
	private String nombrenivel;
	private Long idnivel;
	private String inscripcionconscaner;
	private String pagocurso;
	private String tieneexperienciaaltura;
	private String labordesarrolla;
	private String regimenafiliacionseguridadsocial;
	private String tipovinculacionlaboral;

	private String otralabor;
/*
	private String tipovinculacionlaboral;
	private String regimenafiliacionseguridadsocial;
	private String documentoidentidad;
	private String ultimopagoseguridadsocial;
	private String afiliacionseguridadsocial;
	private String certificadoaptitudmedica;
*/
	private String eps;
	private String arl;
	private String sabeleerescribir;
	private String niveleducativo;
	private String cargoactual;
	private String alergias;
	private String medicamentos;
	private String enfermedades;
	private String lesiones;
	private String drogas;
	private String nombrecontacto;
	private String telefonocontacto;
	private String parentescocontacto;
	private String embarazo;
	private String mesesgestacion;
	private String estadoinscripcion;
	private String exteteorica;
	private String extepractica;
	private String exteenfasis;
	private Long idasistencia;
	private Double eingreso;
	private String fechainscripcion;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat
			(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es-CO", timezone = "America/Bogota")
	private Date fechalimiteinscripcion;
	private boolean existeinscripcionabierta;
	private boolean asistenciacompleta;
	private boolean aprendizContinuaAprendizaje;

	private String fechaencuesta;

	private String fechaUltimaAsistencia;
	private boolean inscripcionporlector;
	private boolean evaluacionAbierta;
}
