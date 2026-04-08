package com.seguridadlimite.models.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Data
public class AuditableEntity {
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonIgnore
    private Date createAt;

    @Basic(optional = false)
    @NotNull
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore
    private Date updateAt;
}
