import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

class Deposito {

    DigitalWallet dw;

    @BeforeEach
    void init() {
        dw = new DigitalWallet("User", 0.0);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, 25.5, 300.0, 0.05 })
    void deveDepositarValoresValidos(double valor) {
        double antes = dw.getBalance();
        dw.deposit(valor);
        assertEquals(antes + valor, dw.getBalance(), 0.001,
                "Depósito válido.");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, -1.0, -50.0 })
    void deveLancarExcecaoParaDepositoInvalido(double valor) {
        double antes = dw.getBalance();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> dw.deposit(valor));
        assertEquals("Amount must be > 0", e.getMessage());
        assertEquals(antes, dw.getBalance(),
                "Saldo não deve ser alterado após tentativa de depósito inválido.");
    }
}