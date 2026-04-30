package Possibilities;

/**
 * Resultado de escolha que altera o saldo de moedas do herói.
 *
 * <p>Pode representar tanto um ganho (encontrar dinheiro) quanto uma
 * perda (pagar por algo, ser roubado). O valor mecânico é aplicado
 * via {@link Entities.Hero#addCoins(int)}.</p>
 *
 * @see Possibility
 * @see Events.Choice
 */
public class CoinPossibility extends Possibility {
    
    /** Quantidade de moedas a ser adicionada (positivo) ou removida (negativo). */
    private int coin;

    /**
     * Constrói uma possibilidade envolvendo moedas.
     *
     * @param description narrativa da ação (ex: "Observar atentamente o seu entorno")
     * @param coin        valor em moedas alterado (positivo para ganho, negativo para perda)
     */
    public CoinPossibility(String description, int coin) {
        super(description);
        this.coin = coin;
    }

    /**
     * Retorna a variação do saldo de moedas desta possibilidade.
     *
     * @return valor em moedas (positivo ou negativo)
     */
    public int getCoins() {
        return this.coin;
    }
}