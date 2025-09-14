import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

class SaldoInicialTest {

    @Test
    void deveConfigurarSaldoInicialCorreto() {
        DigitalWallet wallet = new DigitalWallet("Ana", 100.0);
        assertEquals(100.0, wallet.getBalance(), 0.001);
    }

    @Test
    void deveLancarExcecaoParaSaldoInicialNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalWallet("Ana", -10.0));
    }
}
