import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

class SaldoInicial {
    DigitalWallet dw;

    @BeforeEach
    public void setUp() {
        dw = new DigitalWallet("User", 0);
    }

    @Test
    void deveConfigurarSaldoInicialCorreto() {
        assertEquals(0, dw.getBalance());
    }

    @Test
    void deveLancarExcecaoParaSaldoInicialNegativo() {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class,
                () -> new DigitalWallet("User", -1));
        Assertions.assertEquals("Negative initial balance", excecao.getMessage());
    }

    @Test
    void deveLancarExcecaoParaProprietarioInvalido() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new DigitalWallet(null, 100.0));
        assertEquals("Owner required", e.getMessage());
    }
}