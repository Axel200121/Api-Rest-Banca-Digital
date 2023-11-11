package com.banca.digital.repositories;

import com.banca.digital.entities.OperacionCuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta,String> {


    //List<OperacionCuenta> findByCuentaBancaria(String idCuenta);

