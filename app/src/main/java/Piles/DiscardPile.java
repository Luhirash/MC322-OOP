package Piles;

/**
 * Pilha de descarte que recebe as cartas usadas ou devolvidas pelo jogador.
 * <p>
 * Estende {@link PileOfCards} com a funcionalidade de receber todas as cartas
 * da mão do jogador de uma só vez. Quando o baralho de compra ({@link PurchasePile})
 * esvazia, as cartas do descarte são transferidas de volta para ele.
 * </p>
 *
 * @see PileOfCards
 * @see PurchasePile
 * @see PlayerHand
 */

public class DiscardPile extends PileOfCards{
    
    /**
     * Recebe todas as cartas da mão do jogador, removendo-as da mão e adicionando-as ao descarte.
     *
     * @param hand a mão do jogador cujas cartas serão transferidas para o descarte
     */
    public void receiveCards(PlayerHand hand) {
        while(hand.getHandSize() > 0) {
            addCard(hand.removeCard(0));
        }
    }
}