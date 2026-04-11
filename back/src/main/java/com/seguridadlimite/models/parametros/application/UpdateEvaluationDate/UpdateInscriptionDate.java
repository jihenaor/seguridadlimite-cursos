package com.seguridadlimite.models.parametros.application.UpdateEvaluationDate;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.parametros.infraestructure.IParametrosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInscriptionDate {

	private final IParametrosDao dao;
}
