package com.seguridadlimite.models.aprendiz.application.extraerCertificado;

import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.util.EncodeFileToBase64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExtraerCertificadoTrabajadorServiceImplTest {

    @Mock
    private IAprendizDao aprendizDao;

    @Mock
    private EncodeFileToBase64 encodeFileToBase64;

    @InjectMocks
    private ExtraerCertificadoTrabajadorServiceImpl service;

    @BeforeEach
    void setUp() {
        // reservado para datos comunes de prueba
    }

    @Test
    void injectMocksConstruyeServicio() {
        Assertions.assertNotNull(service);
    }
}
