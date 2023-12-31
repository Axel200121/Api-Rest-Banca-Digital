package com.banca.digital;

import com.banca.digital.dto.ClienteDTO;
import com.banca.digital.dto.CuentaActualDTO;
import com.banca.digital.dto.CuentaAhorroDTO;
import com.banca.digital.dto.CuentaBancariaDTO;
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
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setNombre(nombre);
				clienteDTO.setEmail(nombre+"@gmail.com");
				cuentaBancariaService.saveCliente(clienteDTO);
			});
			cuentaBancariaService.listClientes().forEach(cliente1 -> {
				try {
					cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 9000, cliente1.getId());
					cuentaBancariaService.saveCuentaBancariaAhorro(120000,5.5, cliente1.getId());
					List<CuentaBancariaDTO> cuentaBancarias = cuentaBancariaService.listCuentasBancarias();
					for (CuentaBancariaDTO cuentaBancaria: cuentaBancarias){
						for (int i = 0; i<10; i++){
							String idCuenta;
							if (cuentaBancaria instanceof CuentaAhorroDTO){
								idCuenta = String.valueOf(((CuentaAhorroDTO) cuentaBancaria).getId());
							}else{
								idCuenta = String.valueOf(((CuentaActualDTO) cuentaBancaria).getId());
							}
							cuentaBancariaService.credit(idCuenta, 10000+Math.random()*120000,"Credito");
							cuentaBancariaService.debit(idCuenta, 1000+Math.random()*9000,"Debito");
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			});
		};
	}

}