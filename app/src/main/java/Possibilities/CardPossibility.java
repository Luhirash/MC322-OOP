package Possibilities;
import Cards.Card;

/**
 * Resultado de escolha que adiciona uma nova carta ao baralho do herói.
 *
 * <p>Representa momentos narrativos onde o lutador aprende uma nova técnica
 * ou adquire um novo truque (ex: descobrir o suco secreto, aprender a se agachar).
 * A carta é permanentemente adicionada ao deck de combate.</p>
 *
 * @see Possibility
 * @see Cards.Card
 * @see Events.Choice
 */
public class CardPossibility extends Possibility {
    
    /** A carta de combate que será concedida ao herói. */
    private Card card;

    /**
     * Constrói uma possibilidade que recompensa o jogador com uma carta.
     *
     * @param description narrativa da ação (ex: "Encontra um barman estranho")
     * @param card        instância da {@link Card carta} que será adicionada ao baralho
     */
    public CardPossibility(String description, Card card) {
        super(description);
        this.card = card;
    }

    /**
     * Retorna a carta vinculada a esta possibilidade.
     *
     * @return a carta a ser concedida
     */
    public Card getCard() {
        return this.card;
    }
}