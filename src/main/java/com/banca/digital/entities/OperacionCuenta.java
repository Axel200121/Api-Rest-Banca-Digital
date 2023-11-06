package com.banca.digital.entities;

import com.banca.digital.enums.TipoOperacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacionCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaOperacion;

    private double monto;

    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;

    @ManyToOne
    private CuentaBancaria cuentaBancaria;

}
