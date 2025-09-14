package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DepositoTest {

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 50.5, 100.0})
    void deveDepositarValoresValidos(double amount) {
        DigitalWallet wallet = new DigitalWallet("Ana", 0.0);
        wallet.verify();
        wallet.deposit(amount);
        assertEquals(amount, wallet.getBalance(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -10.0, -0.01})
    void deveLancarExcecaoParaDepositoInvalido(double amount) {
        DigitalWallet wallet = new DigitalWallet("Ana", 0.0);
        wallet.verify();
        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(amount));
    }
}