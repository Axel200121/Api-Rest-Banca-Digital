package com.banca.digital.web;

import com.banca.digital.dto.ClienteDTO;
import com.banca.digital.entities.Cliente;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.services.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

   @Autowired
   private CuentaBancariaService cuentaBancariaService;


   @GetMapping("/clientes")
    public List<ClienteDTO> getAllClientes(){
       return cuentaBancariaService.listClientes();
   }


   @GetMapping("/clientes/{id}")
   public ClienteDTO getCliente(@PathVariable(name = "id") Long idCliente)  throws ClienteNotFoundException{
      return cuentaBancariaService.getCliente(idCliente);
   }

   @PostMapping("/clientes")
   public ClienteDTO SaveCliente(@RequestBody ClienteDTO clienteDTO){
      return cuentaBancariaService.saveCliente(clienteDTO);
   }

   @PutMapping("/clientes/{idCliente}")
   public ClienteDTO updateCliente(@PathVariable Long idCliente, @RequestBody ClienteDTO clienteDTO){
      clienteDTO.setId(idCliente);
      return cuentaBancariaService.updateCliente(clienteDTO);
   }

   @DeleteMapping("/clientes/{idCliente}")
   public void deleteCLiente(@PathVariable Long idCliente){
      cuentaBancariaService.deleteCliente(idCliente);
   }
}
