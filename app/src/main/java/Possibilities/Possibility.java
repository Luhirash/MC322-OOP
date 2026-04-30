package Possibilities;

/**
 * Classe base que representa um possível resultado de um evento de escolha narrativa.
 *
 * <p>Em eventos do tipo {@link Events.Choice}, o jogador se depara com decisões
 * que geram consequências para o herói. Esta classe encapsula a descrição genérica
 * da consequência de uma escolha. Suas subclasses definem o impacto mecânico real
 * (ex: ganhar/perder vida, moedas ou cartas).</p>
 *
 * @see CoinPossibility
 * @see HealthPossibility
 * @see CardPossibility
 * @see Events.Choice
 */
public class Possibility {

    /** Texto descritivo da consequência gerada pela escolha. */
    private String description;

    /**
     * Constrói uma possibilidade com uma descrição narrativa.
     *
     * @param description a descrição textual do que essa possibilidade acarreta
     */
    public Possibility(String description) {
        this.description = description;
    }

    /**
     * Retorna a descrição narrativa da possibilidade.
     *
     * @return texto descritivo da consequência
     */
    public String getDescription() {
        return this.description;
    }
}