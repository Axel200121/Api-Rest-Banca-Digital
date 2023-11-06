package com.banca.digital.repositories;

import com.banca.digital.entities.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria,String> {
}
