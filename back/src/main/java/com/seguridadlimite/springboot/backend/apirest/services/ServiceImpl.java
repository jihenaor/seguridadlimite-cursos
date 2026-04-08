package com.seguridadlimite.springboot.backend.apirest.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ServiceImpl{

	private Date StringToDate(String date) {
		Date fecha = null;

	    if (date != null) {
	      if (date.toString().length() == 8) {
	        Calendar c = Calendar.getInstance();
	
	        c.set(Integer.parseInt(date.substring(0, 4)), 
			Integer.parseInt(date.substring(4, 6)) - 1, 
			Integer.parseInt(date.substring(6, 8)),
			0,
			0,
			0);
	        fecha = c.getTime();
	      }
	    }
	    return fecha;
	}
	   
}
