package com.banca.digital;

import com.banca.digital.entities.*;
import com.banca.digital.enums.EstadoCuenta;
import com.banca.digital.enums.TipoOperacion;
import com.banca.digital.exceptions.ClienteNotFoundException;
import com.banca.digital.repositories.ClienteRepository;
import com.banca.digital.repositories.CuentaBancariaRepository;
import com.banca.digital.repositories.OperacionCuentaRepository;
import com.banca.digital.services.BancoService;
import com.banca.digital.services.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {



	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(BancoService bancoService){
		return  args -> {
			bancoService.consultar();
		};
	}

	//@Bean
	CommandLineRunner start(CuentaBancariaService cuentaBancariaService){
		return args -> {
			Stream.of("Axel","Edgar","Marco","Julio").forEach(nombre->{
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre+"@gmail.com");
				cuentaBancariaService.saveCliente(cliente);
			});
			cuentaBancariaService.listClientes().forEach(cliente1 -> {
				try {
					cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 9000, cliente1.getId());
					cuentaBancariaService.saveCuentaBancariaAhorro(120000,5.5, cliente1.getId());
					List<CuentaBancaria> cuentaBancarias = cuentaBancariaService.listCuentasBancarias();
					for (CuentaBancaria cuentaBancaria: cuentaBancarias){
						for (int i = 0; i<10; i++){
							cuentaBancariaService.credit(cuentaBancaria.getId(), 10000+Math.random()*120000,"Credito");
							cuentaBancariaService.debit(cuentaBancaria.getId(), 1000+Math.random()*9000,"Debito");
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			});
		};
	}

}
