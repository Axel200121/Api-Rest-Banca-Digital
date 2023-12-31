package com.banca.digital.services;

import com.banca.digital.dto.*;
import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.exceptions.BalanceInsuficienteException;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    ClienteDTO saveCliente(ClienteDTO clienteDTO);

    ClienteDTO  getCliente(Long idCliente) throws  ClienteNotFoundException;

    ClienteDTO updateCliente(ClienteDTO clienteDTO);

    void deleteCliente(Long idCliente);

    CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long idCliente) throws ClienteNotFoundException;

    CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long idCliente) throws ClienteNotFoundException;

    List<ClienteDTO> listClientes();

    CuentaBancariaDTO getCuentaBancaria(String idCuenta) throws CuentaBancariaNotFoundException;

    void debit(String idCuenta, double monto, String descripcion) throws  CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String idCuenta, double monto, String descripcion) throws  CuentaBancariaNotFoundException;

    void transfer(String idCuentaPropietario, String idCuentaDestinatario, double monto) throws CuentaBancariaNotFoundException,BalanceInsuficienteException;

    List<CuentaBancariaDTO> listCuentasBancarias();

    //List<OperacionCuentaDTO> listHistorialDeCuenta(String idCuenta);
}
