package com.banca.digital.services.impl;

import com.banca.digital.dto.*;
import com.banca.digital.entities.*;
import com.banca.digital.enums.TipoOperacion;
import com.banca.digital.exceptions.BalanceInsuficienteException;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;
import com.banca.digital.mappers.CuentaBancariaMapperImpl;
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
import java.util.stream.Collectors;

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

    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;





    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {

        log.info("Guardando un nuevo cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO); //convertimos DTO a entidad
        Cliente clienteBBDD = clienteRepository.save(cliente); // guardamos el cliente convertido a la BD
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public ClienteDTO getCliente(Long idCliente) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()->new ClienteNotFoundException("Cliente no encontrado"));
        return  cuentaBancariaMapper.mapearDeCliente(cliente);
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        log.info("Actualizando cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public void deleteCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }

    @Override
    public CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long idCliente) throws ClienteNotFoundException {

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

        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualBBDD);

    }

    @Override
    public CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long idCliente) throws ClienteNotFoundException {

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

        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroBBDD);
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());
        return  clienteDTOS;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String idCuenta) throws CuentaBancariaNotFoundException {
        //todo: buscamos por id una cuenta, si esque no la encuentra manda una excepcion
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        if (cuentaBancaria instanceof  CuentaAhorro){
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);

        }else{
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
        }
    }

    @Override
    public void debit(String idCuenta, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

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

        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

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
    public List<CuentaBancariaDTO> listCuentasBancarias() {
       List<CuentaBancaria> cuentaBancarias = cuentaBancariaRepository.findAll();//toda la lista de entidades
       List<CuentaBancariaDTO> cuentaBancariaDTOS = cuentaBancarias.stream().map(cuentaBancaria -> { //creamos una lista de DTOS
           if (cuentaBancaria  instanceof CuentaAhorro){
               CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
               return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
           }else{
               CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
               return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
           }
       }).collect(Collectors.toList());
       return cuentaBancariaDTOS;
    }

    /*@Override
    public List<OperacionCuentaDTO> listHistorialDeCuenta(String idCuenta) {
        List<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancaria (idCuenta);
        return operacionesCuenta.stream().map(operacionCuenta ->
            cuentaBancariaMapper.mapearDeOperacionCuentaa(operacionCuenta)
        ).collect(Collectors.toList());
    }*/
}
