import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

public class Pagamento {

    @ParameterizedTest
    @CsvSource({
            "100.0, 50.0, true",
            "50.0, 50.0, true",
            "100.0, 150.0, false",
            "0.0, 10.0, false"
    })
    void deveRealizarPagamentoParaValoresValidos(double saldoInicial, double valorPagamento, boolean esperado) {
        DigitalWallet wallet = new DigitalWallet("User", saldoInicial);
        wallet.verify();

        assumeTrue(wallet.isVerified() && !wallet.isLocked(),
                "Pré-condição: carteira deve estar verificada e desbloqueada.");

        boolean resultado = wallet.pay(valorPagamento);
        assertEquals(esperado, resultado, "Pagamento deve retornar " + esperado);

        if (resultado) {
            assertEquals(saldoInicial - valorPagamento, wallet.getBalance(), "O saldo deve ser debitado corretamente.");
        } else {
            assertEquals(saldoInicial, wallet.getBalance(), "O saldo não deve ser alterado em caso de falha.");
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, -1.0, -10.0 })
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet wallet = new DigitalWallet("User", 100.0);
        wallet.verify();

        assumeTrue(wallet.isVerified() && !wallet.isLocked());

        assertThrows(IllegalArgumentException.class, () -> wallet.pay(valor),
                "Pagamento não pode ter valor <= 0.");
        assertEquals(100.0, wallet.getBalance(), "O saldo não deve ser alterado após tentativa de pagamento inválida.");
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet unverifiedWallet = new DigitalWallet("User", 100.0);
        assumeFalse(unverifiedWallet.isVerified());
        assertThrows(IllegalStateException.class, () -> unverifiedWallet.pay(10.0),
                "Pagamento em carteira não verificada deve lançar exceção.");

        DigitalWallet lockedWallet = new DigitalWallet("User", 100.0);
        lockedWallet.verify();
        lockedWallet.lock();
        assumeTrue(lockedWallet.isLocked());
        assertThrows(IllegalStateException.class, () -> lockedWallet.pay(10.0),
                "Pagamento em carteira bloqueada deve lançar exceção.");
    }
}