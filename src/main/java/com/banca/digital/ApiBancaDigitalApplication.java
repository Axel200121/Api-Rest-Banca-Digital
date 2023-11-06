package com.banca.digital;

import com.banca.digital.entities.Cliente;
import com.banca.digital.entities.CuentaActual;
import com.banca.digital.entities.CuentaAhorro;
import com.banca.digital.entities.OperacionCuenta;
import com.banca.digital.enums.EstadoCuenta;
import com.banca.digital.enums.TipoOperacion;
import com.banca.digital.repositories.ClienteRepository;
import com.banca.digital.repositories.CuentaBancariaRepository;
import com.banca.digital.repositories.OperacionCuentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository){
		return args -> {
			Stream.of("Axel","Edgar","Marco","Julio").forEach(nombre->{
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre+"@gmail.com");
				clienteRepository.save(cliente);
			});

			//Asignamos cuentas bancarias
			clienteRepository.findAll().forEach(cliente -> {
				CuentaActual cuentaActual = new CuentaActual();
				cuentaActual.setId(UUID.randomUUID().toString());
				cuentaActual.setBalance(Math.random()*90000);
				cuentaActual.setFechaCreacion(new Date());
				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaActual.setCliente(cliente);
				cuentaActual.setSobregiro(90000);
				cuentaBancariaRepository.save(cuentaActual);

				CuentaAhorro cuentaAhorro = new CuentaAhorro();
				cuentaAhorro.setId(UUID.randomUUID().toString());
				cuentaAhorro.setBalance(Math.random()*90000);
				cuentaAhorro.setFechaCreacion(new Date());
				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaAhorro.setCliente(cliente);
				cuentaAhorro.setTasaDeInteres(5.5);
				cuentaBancariaRepository.save(cuentaAhorro);
			});

			//Agregamos la operaciones
			cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
				for (int i=0; i < 10; i++){
					OperacionCuenta operacionCuenta = new OperacionCuenta();
					operacionCuenta.setFechaOperacion(new Date());
					operacionCuenta.setMonto(Math.random()*12000);
					operacionCuenta.setTipoOperacion(Math.random() > 05 ? TipoOperacion.DEBITO : TipoOperacion.DEBITO);
					operacionCuenta.setCuentaBancaria(cuentaBancaria);
					operacionCuentaRepository.save(operacionCuenta);
				}
			});
		};
	}

}
