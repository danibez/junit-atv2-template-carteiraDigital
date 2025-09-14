import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

class EstornoTest {

    static Stream<Arguments> valoresEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0, 5.0, 5.0),
            Arguments.of(50.0, 0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void refundComCarteiraValida(double inicial, double valor, double saldoEsperado) {
        DigitalWallet wallet = new DigitalWallet("Ana", inicial);
        wallet.verify();
        wallet.refund(valor);
        assertEquals(saldoEsperado, wallet.getBalance(), 0.001);
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void deveLancarExcecaoParaRefundInvalido(double inicial, double valor, double saldoEsperado) {
        DigitalWallet wallet = new DigitalWallet("Ana", inicial);
        wallet.verify();
        assertThrows(IllegalArgumentException.class, () -> wallet.refund(-valor));
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet wallet = new DigitalWallet("Ana", 100.0);
        assertThrows(IllegalStateException.class, () -> wallet.refund(10.0));
        wallet.verify();
        wallet.lock();
        assertThrows(IllegalStateException.class, () -> wallet.refund(10.0));
    }
}
