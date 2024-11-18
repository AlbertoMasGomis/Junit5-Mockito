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

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Alberto", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Claudia", new BigDecimal("1500.123"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));

        assertEquals("2000.123", cuenta2.getSaldo().toPlainString());
        assertEquals("2000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Alberto", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Claudia", new BigDecimal("1500.123"));


        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));

        assertEquals("2000.123", cuenta2.getSaldo().toPlainString());
        assertEquals("2000", cuenta1.getSaldo().toPlainString());

        banco.addCuentas(cuenta1);
        banco.addCuentas(cuenta2);
        cuenta1.setBanco(banco);
        cuenta2.setBanco(banco);
        assertEquals(2,banco.getCuentas().size());

    }
}