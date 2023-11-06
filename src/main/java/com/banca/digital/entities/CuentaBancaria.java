package com.banca.digital.entities;

import com.banca.digital.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
//todo: relacionar varias clases a una sola tabla a un campo determinado
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Hace simulacion a la herencia
@DiscriminatorColumn(name = "TIPO", length = 4)
//
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaBancaria {

    @Id
    private String id;

    private double balance;

    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    private List<OperacionCuenta> operacionesCuentas;
}
