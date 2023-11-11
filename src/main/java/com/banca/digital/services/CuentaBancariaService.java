package com.banca.digital.services;

import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.exceptions.BalanceInsuficienteException;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    Cliente saveCliente(Cliente cliente);

    CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long idCliente) throws ClienteNotFoundException;

    CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long idCliente) throws ClienteNotFoundException;

    List<Cliente> listClientes();

    CuentaBancaria getCuentaBancaria(String idCuenta) throws CuentaBancariaNotFoundException;

    void debit(String idCuenta, double monto, String descripcion) throws  CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String idCuenta, double monto, String descripcion) throws  CuentaBancariaNotFoundException;

    void transfer(String idCuentaPropietario, String idCuentaDestinatario, double monto) throws CuentaBancariaNotFoundException,BalanceInsuficienteException;

    List<CuentaBancaria> listCuentasBancarias();
}
