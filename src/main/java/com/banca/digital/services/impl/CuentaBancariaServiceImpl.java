package com.banca.digital.services.impl;

import com.banca.digital.entities.*;
import com.banca.digital.enums.TipoOperacion;
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

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (cliente == null){
            throw  new ClienteNotFoundException("Cliente no encontrado");
        }
        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualBBDD = cuentaBancariaRepository.save(cuentaActual);

        return cuentaActualBBDD;

    }

    @Override
    public CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long idCliente) throws ClienteNotFoundException {

        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (cliente == null){
            throw  new ClienteNotFoundException("Cliente no encontrado");
        }
        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);

        CuentaAhorro cuentaAhorroBBDD = cuentaBancariaRepository.save(cuentaAhorro);

        return cuentaAhorroBBDD;
    }

    @Override
    public List<Cliente> listClientes() {
        return  clienteRepository.findAll();
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String idCuenta) throws CuentaBancariaNotFoundException {
        //todo: buscamos por id una cuenta, si esque no la encuentra manda una excepcion
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        return  cuentaBancaria;
    }

    @Override
    public void debit(String idCuenta, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(idCuenta);
        if (cuentaBancaria.getBalance() < monto){ //monto es mayor a lo que tengo
            throw  new BalanceInsuficienteException("Balance insuficiente");
        }
        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        cuentaBancaria.setBalance(cuentaBancaria.getBalance()-monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void credit(String idCuenta, double monto, String descripcion) throws CuentaBancariaNotFoundException {

        CuentaBancaria cuentaBancaria = getCuentaBancaria(idCuenta);

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        cuentaBancaria.setBalance(cuentaBancaria.getBalance()+monto);
        cuentaBancariaRepository.save(cuentaBancaria);

    }

    @Override
    public void transfer(String idCuentaPropietario, String idCuentaDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        //tranferiri dinero
        debit(idCuentaPropietario,monto,"Transferencia a: " + idCuentaDestinatario);
        credit(idCuentaDestinatario, monto, "Transaferencia de: "+idCuentaPropietario);
    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return  cuentaBancariaRepository.findAll();
    }
}
