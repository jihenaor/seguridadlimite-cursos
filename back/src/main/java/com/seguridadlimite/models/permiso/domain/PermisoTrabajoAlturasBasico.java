package com.seguridadlimite.models.permiso.domain;

import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.personal.dominio.PersonalBasico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permiso_trabajo_alturas")
public class PermisoTrabajoAlturasBasico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @Column(name = "fecha_inicio", nullable = false, length = 10)
    private String fechaInicio;

    @Column(name = "valido_desde", nullable = false, length = 10)
    private String validoDesde;

    @Column(name = "valido_hasta")
    private String validoHasta;

    @Column(name = "hora_inicio")
    private String horaInicio;

    @Column(name = "hora_final")
    private String horaFinal;

    @Column(name = "id_nivel")
    private Long idNivel;

    @JoinColumn(name = "id_nivel", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Nivel nivel;

    private Integer idpersonaautoriza1;

    @JoinColumn(name = "idpersonaautoriza1", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalBasico personaautoriza1;

    private Integer idresponsableemeergencias;

    @JoinColumn(name = "idresponsableemeergencias", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalBasico responsableemeergencias;

    @Column(name = "codigoministerio", length = 20)
    private String codigoministerio;

    @Column(name = "cupoinicial", nullable = false)
    private Integer cupoinicial;

    @Column(name = "cupofinal", nullable = false)
    private Integer cupofinal;

    private Integer dias;
}
