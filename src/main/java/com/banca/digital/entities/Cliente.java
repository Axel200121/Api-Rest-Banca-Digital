package com.banca.digital.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    //Un cliente tiene muchas cuentas bancarias
    //mappedBy -> Hace referencia a la relacion entre clases
    @OneToMany(mappedBy = "cliente")
    private List<CuentaBancaria> cuentasBancarias;
}
