package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByCountry(String country);
    List<Address> findByCity(String city);
    List<Address> findByZipCode(String zipCode);
}