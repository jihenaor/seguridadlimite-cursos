package com.seguridadlimite.models.aprendiz.infraestructure.dto;

import lombok.Getter;

@Getter
public class AprendizNoSeQueEsEstosDTO {
    private long id;

    private AprendizNoSeQueEsEstosDTO() {
    }

    public static class Builder {
        private long id;

        public Builder(long id) {
            this.id = id;

        }


        public AprendizNoSeQueEsEstosDTO build() {
            return new AprendizNoSeQueEsEstosDTO();
        }
    }
}
