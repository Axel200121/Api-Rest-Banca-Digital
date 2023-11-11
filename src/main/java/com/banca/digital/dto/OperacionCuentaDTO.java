package com.banca.digital.dto;

import com.banca.digital.enums.TipoOperacion;
import lombok.Data;

import java.util.Date;

@Data
public class OperacionCuentaDTO {
    private Long id;
    private Date fechaOperacion;
    private double monto;
    private TipoOperacion tipoOperacion;
    private String descripcion;
}
