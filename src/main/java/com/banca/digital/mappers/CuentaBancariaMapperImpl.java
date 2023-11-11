package com.banca.digital.mappers;

import com.banca.digital.dto.ClienteDTO;
import com.banca.digital.dto.CuentaActualDTO;
import com.banca.digital.dto.CuentaAhorroDTO;
import com.banca.digital.dto.OperacionCuentaDTO;
import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.OperacionCuenta;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMapperImpl {

    //Pasamos entidad cliente a cliente dto
    public ClienteDTO mapearDeCliente(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente,clienteDTO);
        return clienteDTO;
    }

    //Convertimos un DTO  auna entidad
    public Cliente mapearDeClienteDTO(ClienteDTO clientedto){
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clientedto,cliente);
        return cliente;
    }

    //Mapear de entidad cuenta ahorro a un dto
    public CuentaAhorroDTO mapearDeCuentaAhorro(CuentaAhorro cuentaAhorro){
        CuentaAhorroDTO  cuentaAhorroDTO  = new CuentaAhorroDTO();
        BeanUtils.copyProperties(cuentaAhorro,cuentaAhorroDTO); // se pasa todo de una entidad a un dto
        cuentaAhorroDTO.setClienteDTO(mapearDeCliente(cuentaAhorro.getCliente()));
        cuentaAhorroDTO.setTipo(cuentaAhorro.getClass().getSimpleName());
        return  cuentaAhorroDTO;
    }

    //Convertimos un DTO a una entidad
    public CuentaAhorro mapearDeCuentaAhorroDTO(CuentaAhorroDTO cuentaAhorroDTO){
        CuentaAhorro cuentaAhorro  = new CuentaAhorro();
        BeanUtils.copyProperties(cuentaAhorroDTO,cuentaAhorro);
        cuentaAhorro.setCliente(mapearDeClienteDTO(cuentaAhorroDTO.getClienteDTO()));
        return  cuentaAhorro;
    }


    //Mapear de entidad cuenta actual a un dto
    public CuentaActualDTO mapearDeCuentaActual(CuentaActual cuentaActual){
        CuentaActualDTO  cuentaActualDTO  = new CuentaActualDTO();
        BeanUtils.copyProperties(cuentaActual,cuentaActualDTO); // se pasa todo de una entidad a un dto
        cuentaActualDTO.setClienteDTO(mapearDeCliente(cuentaActual.getCliente()));
        cuentaActualDTO.setTipo(cuentaActual.getClass().getSimpleName());
        return  cuentaActualDTO;
    }

    //Convertimos un DTO a una entidad
    public CuentaActual mapearDeCuentaActualDTO(CuentaActualDTO cuentaActualDTO){
        CuentaActual cuentaActual  = new CuentaActual();
        BeanUtils.copyProperties(cuentaActualDTO,cuentaActual);
        cuentaActual.setCliente(mapearDeClienteDTO(cuentaActualDTO.getClienteDTO()));
        return  cuentaActual;
    }

    public OperacionCuentaDTO mapearDeOperacionCuentaa(OperacionCuenta operacionCuenta){
        OperacionCuentaDTO operacionCuentaDTO = new OperacionCuentaDTO();
        BeanUtils.copyProperties(operacionCuenta,operacionCuentaDTO);
        return operacionCuentaDTO;
    }




}
