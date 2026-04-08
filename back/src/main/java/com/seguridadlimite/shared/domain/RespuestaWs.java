package com.seguridadlimite.shared.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RespuestaWs<T> {
    private String mensaje;
    private boolean error;
    private T data;

    public RespuestaWs(String mensaje, boolean error, T data) {
        this.mensaje = mensaje;
        this.error = error;
        this.data = data;
    }

    public static <T> RespuestaWs<T> ok(String mensaje, T data) {
        return new RespuestaWs<>(mensaje, false, data);
    }

    public static <T> RespuestaWs<T> error(String mensaje) {
        return new RespuestaWs<>(mensaje, true, null);
    }
} 