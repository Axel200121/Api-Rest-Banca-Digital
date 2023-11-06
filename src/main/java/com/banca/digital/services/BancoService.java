package com.banca.digital.services;

import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.CuentaBancaria;
import com.banca.digital.repositories.CuentaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    public void consultar(){
        CuentaBancaria cuentaBancariaBBDD = cuentaBancariaRepository.findById("192ada0a-fc0d-4b02-bf01-1816a5d31948").orElse(null);
        if (cuentaBancariaBBDD != null){
            System.out.println("*******************************");
            System.out.println("ID: "+cuentaBancariaBBDD.getId());
            System.out.println("Balancce: "+cuentaBancariaBBDD.getBalance());
            System.out.println("Estado de cuenta: "+cuentaBancariaBBDD.getEstadoCuenta());
            System.out.println("Fecha de creacion: "+cuentaBancariaBBDD.getFechaCreacion());
            System.out.println("Cliente: "+cuentaBancariaBBDD.getCliente().getNombre());

            if (cuentaBancariaBBDD instanceof CuentaActual){
                System.out.println("Sobregiro: "+((CuentaActual) cuentaBancariaBBDD).getSobregiro());
            }else if(cuentaBancariaBBDD instanceof CuentaAhorro){
                System.out.println("Tasa de interes: "+((CuentaAhorro) cuentaBancariaBBDD).getTasaDeInteres());
            }
            cuentaBancariaBBDD.getOperacionesCuentas().forEach(operacionCuenta -> {
                System.out.println("---------------------------------------");
                System.out.println("Tipo de operacion: "+ operacionCuenta.getTipoOperacion());
                System.out.println("Fecha de operacion: "+ operacionCuenta.getFechaOperacion());
                System.out.println("Monto: "+ operacionCuenta.getMonto());
            });
        }
    }
}
