import java.util.Random;

/**
 * Pilha de compra que funciona como o baralho principal do herói.
 * <p>
 * É preenchida com cópias aleatórias das cartas do herói no início do jogo
 * e embaralhada. Quando esgota, as cartas da {@link DiscardPile pilha de descarte}
 * são recuperadas e embaralhadas novamente para reabastecê-la.
 * </p>
 *
 * @see PileOfCards
 * @see DiscardPile
 */
public class PurchasePile extends PileOfCards {

    /** Array com as cartas-modelo usadas para popular o baralho. */
    private Card[] differentCards;

    /**
     * Constrói a pilha de compra com base no conjunto de cartas disponíveis.
     *
     * @param differentCards array de cartas-modelo que serão copiadas para o baralho
     */
    public PurchasePile(Card[] differentCards) {
        this.differentCards = differentCards;
    }

    /**
     * Preenche o baralho com cópias instanciadas de cartas escolhidas aleatoriamente.
     * <p>
     * Cada carta é instanciada como uma nova cópia do tipo correto (não referência à original),
     * preservando o polimorfismo. O número de cartas adicionadas é igual a {@code numberOfCards}.
     * </p>
     *
     * @param numberOfCards quantidade de cartas a adicionar ao baralho
     */
    protected void fillPile(int numberOfCards) {

        for (int i = 0; i < numberOfCards; i++) {

            Random number = new Random();
            int index = number.nextInt(differentCards.length);

            Card originalCard = differentCards[index];

            if (originalCard instanceof bleedingCard) {
                bleedingCard pc = (bleedingCard) originalCard;
                super.addCard(new bleedingCard(pc.getName(), pc.getStaminaCost(), pc.getMainStat(), pc.get_description()));
            }
            else if (originalCard instanceof HealingCard) {
                HealingCard tc = (HealingCard) originalCard;
                super.addCard(new HealingCard(tc.getName(), tc.getStaminaCost(), tc.getMainStat(), tc.get_description()));
            }
            else if (originalCard instanceof DamageCard) {
                super.addCard(new DamageCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
            else {
                super.addCard(new ShieldCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
        }
    }

    /**
     * Reabastece o baralho transferindo todas as cartas da pilha de descarte.
     * <p>
     * Chamado automaticamente quando o baralho está vazio. As cartas são transferidas
     * do descarte para cá e em seguida embaralhadas.
     * </p>
     *
     * @param discardPile a pilha de descarte de onde as cartas serão recuperadas
     */
    public void retrieveCards(PileOfCards discardPile) {
        System.out.println("Baralho vazio! Embaralhando pilha de descarte\n");
        while(!discardPile.isEmpty()){
            addCard(discardPile.popCard());
        }
        shuffle();
    }
}