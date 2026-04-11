package com.seguridadlimite.models.entity;

import lombok.extern.slf4j.Slf4j;

import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
@Slf4j
public class RespuestaWs {
	
	String msg;

	public RespuestaWs() {
		msg = "";
	}

	public RespuestaWs(String msg) {
		this.msg = msg;
	}

	public RespuestaWs(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		log.info(sStackTrace);
		
		this.msg = e.getMessage() + " " + (e.getCause() != null ? e.getCause().getMessage() : " ")
				+ sStackTrace;
	}
}
