package com.seguridadlimite.models.aprendiz.application.buscarporidgrupo;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAprendizByIdGrupo {
    private final IAprendizDao aprendizRepositorio;

    @Autowired
    public FindAprendizByIdGrupo(IAprendizDao aprendizRepositorio) {
        this.aprendizRepositorio = aprendizRepositorio;
    }

    public List<Aprendiz> buscar(Long idgrupo) {
//        return aprendizRepositorio.findByIdgrupo(idgrupo);
        return null;
    }
}
