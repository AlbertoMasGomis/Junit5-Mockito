package org.example.models;

import org.example.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        //cuenta.setPersona("Andres");
        String esperado = "Andres";
        String realidad = cuenta.getPersona();
        Assertions.assertEquals(esperado, realidad);

    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        cuenta.debito(new BigDecimal("100"));
        assertEquals(cuenta.getSaldo().intValue(), 8800);
        assertEquals(cuenta.getSaldo().toPlainString(), "8800.9997");
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        cuenta.credito(new BigDecimal("100"));
        assertEquals(cuenta.getSaldo().intValue(), 9000);
        assertEquals(cuenta.getSaldo().toPlainString(), "9000.9997");
    }

    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("Alberto", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1001));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }
}