package com.banca.digital.mappers;

import com.banca.digital.dto.ClienteDTO;
import com.banca.digital.entities.Cliente;
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


}
