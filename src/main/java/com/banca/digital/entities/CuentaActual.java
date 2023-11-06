package com.banca.digital.entities;
//Sobregiro -> sobrepasa el creidto o los fondos de lo establecido

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("CA") //Current Account(cuenta actual)
@NoArgsConstructor
@AllArgsConstructor
public class CuentaActual extends CuentaBancaria {
    private double sobregiro;
}
