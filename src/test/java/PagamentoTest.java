import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;
import org.junit.jupiter.params.provider.ValueSource;

class PagamentoTest {

    @ParameterizedTest
    @CsvSource({
        "100.0, 50.0, true",
        "50.0, 100.0, false",
        "0.01, 0.01, true"
    })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {
        DigitalWallet wallet = new DigitalWallet("Ana", inicial);
        wallet.verify();
        boolean resultado = wallet.pay(valor);
        assertEquals(esperado, resultado);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -10.0})
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet wallet = new DigitalWallet("Ana", 100.0);
        wallet.verify();
        assertThrows(IllegalArgumentException.class, () -> wallet.pay(valor));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet wallet = new DigitalWallet("Ana", 100.0);
        assertThrows(IllegalStateException.class, () -> wallet.pay(10.0));
        wallet.verify();
        wallet.lock();
        assertThrows(IllegalStateException.class, () -> wallet.pay(10.0));
    }
}
