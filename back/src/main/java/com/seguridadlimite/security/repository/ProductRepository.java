package com.seguridadlimite.security.repository;

import com.seguridadlimite.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @PreAuthorize("hasAuthority('WRITE_CURSOS')")
    Product save(Product product);

}
