package com.banca.digital.exceptions;

public class BalanceInsuficienteException extends Exception{
    public BalanceInsuficienteException(String message) {
        super(message);
    }
}
