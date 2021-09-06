package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByName(String name);

    Optional<Object> findAllById(long id);
}
