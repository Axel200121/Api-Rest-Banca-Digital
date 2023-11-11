package com.banca.digital.web;

import com.banca.digital.dto.CuentaBancariaDTO;
import com.banca.digital.exceptions.CuentaBancariaNotFoundException;
import com.banca.digital.services.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/cuentas/{idCuenta}")
    public CuentaBancariaDTO listasrtDatosDeUnaCuentaBancaria(@PathVariable String idCuenta) throws CuentaBancariaNotFoundException {
        return  cuentaBancariaService.getCuentaBancaria(idCuenta);
    }

    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> getAllCuentasBancarias(){
        return  cuentaBancariaService.listCuentasBancarias();
    }


}
