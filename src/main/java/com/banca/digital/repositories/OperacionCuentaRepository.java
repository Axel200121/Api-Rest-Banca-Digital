package com.banca.digital.repositories;

import com.banca.digital.entities.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta,String> {
}
