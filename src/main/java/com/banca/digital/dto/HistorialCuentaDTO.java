package com.banca.digital.dto;

import lombok.Data;

import java.util.List;

@Data
public class HistorialCuentaDTO {
    private String id;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperacionCuentaDTO> operacionCuentaDTOS;
}
