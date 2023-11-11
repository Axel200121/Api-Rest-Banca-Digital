package com.banca.digital.dto;

import com.banca.digital.enums.EstadoCuenta;
import lombok.Data;

import java.util.Date;

@Data
public class CuentaAhorroDTO extends CuentaBancariaDTO {
    private String id;
    private double balance;
    private Date fechaCreacion;
    private EstadoCuenta  estadoCuenta;
    private ClienteDTO clienteDTO;
    private double tasaDeInteres;
}
