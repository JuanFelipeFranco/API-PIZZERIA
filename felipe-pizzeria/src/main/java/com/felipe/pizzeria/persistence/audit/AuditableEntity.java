package com.felipe.pizzeria.persistence.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass //superclaseque puede ser heredada de otras clases
public class AuditableEntity {
    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDated;
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDated;

    @Column(name="created_by")
    @CreatedBy
    private String createdBy;

    @Column(name="modified_by")
    @LastModifiedBy
    private String modifiedBy;

}
