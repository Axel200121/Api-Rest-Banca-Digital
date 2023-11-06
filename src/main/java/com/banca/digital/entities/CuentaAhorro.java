package com.banca.digital.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("SA") //Saving Account(cuenta de ahorro)
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAhorro extends  CuentaBancaria{
    private double tasaDeInteres;
}
