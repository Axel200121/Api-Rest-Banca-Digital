package com.banca.digital.services.impl;

import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.exceptions.BalanceInsuficienteException;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;
import com.banca.digital.repositories.ClienteRepository;
import com.banca.digital.repositories.CuentaBancariaRepository;
import com.banca.digital.repositories.OperacionCuentaRepository;
import com.banca.digital.services.CuentaBancariaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;





    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Guardando un nuevo cliente");
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return clienteBBDD;
    }

    @Override
    public CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long idCliente) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public CuentaActual saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long idCliente) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public List<Cliente> listClientes() {
        return null;
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String idCuenta) throws CuentaBancariaNotFoundException {
        return null;
    }

    @Override
    public void debit(String idCuenta, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public void credit(String idCuenta, double monto, String descripcion) throws CuentaBancariaNotFoundException {

    }

    @Override
    public void transfer(String idCuentaPropietario, String idCuentaDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return null;
    }
}
