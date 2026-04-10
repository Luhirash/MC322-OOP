import java.util.Random;

/**
 * Pilha de compra que funciona como o baralho principal do herói.
 *
 * <p>É preenchida no início do jogo com instâncias aleatórias das cartas do herói
 * ({@link Hero#getHits()}) e embaralhada antes do primeiro turno. Durante o jogo,
 * o jogador compra cartas desta pilha para sua mão ({@link PlayerHand}).
 * Quando o baralho se esgota, as cartas da {@link DiscardPile pilha de descarte}
 * são recuperadas e reembaralhadas automaticamente.</p>
 *
 * <h2>Instanciação de cópias polimórficas</h2>
 * <p>O método {@link #fillPile(int)} não reutiliza as referências originais das cartas
 * do herói — ele cria <b>novas instâncias</b> de cada tipo de carta para que cada
 * cópia no baralho seja independente (ex: {@code wasUsed} separado para cada cópia).</p>
 *
 * @see PileOfCards
 * @see DiscardPile
 * @see PlayerHand
 */
public class PurchasePile extends PileOfCards {

    /**
     * Array com as cartas-modelo do herói usadas como base para popular o baralho.
     * <p>As cartas aqui são apenas referências para os templates; o baralho é composto
     * de cópias independentes criadas por {@link #fillPile(int)}.</p>
     */
    private Card[] differentCards;

    /**
     * Constrói a pilha de compra com base no conjunto de cartas-modelo fornecido.
     *
     * @param differentCards array de cartas-modelo do herói (normalmente {@link Hero#getHits()})
     */
    public PurchasePile(Card[] differentCards) {
        this.differentCards = differentCards;
    }

    /**
     * Preenche o baralho com cópias independentes de cartas escolhidas aleatoriamente.
     *
     * <p>A cada iteração, uma carta é sorteada aleatoriamente do array {@link #differentCards}
     * e uma nova instância do tipo correto é criada e adicionada ao baralho. A criação
     * de novas instâncias (e não reutilização de referências) é necessária para preservar
     * o polimorfismo e garantir que cada carta no baralho seja independente.</p>
     *
     * <p>Tipos suportados: {@link bleedingCard}, {@link HealingCard}, {@link DamageCard}
     * e {@link ShieldCard}. Cartas não reconhecidas são tratadas como {@link ShieldCard}.</p>
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
     *
     * <p>Chamado automaticamente pelo loop principal em {@link App#main} quando o
     * baralho de compra está vazio. As cartas do descarte são transferidas uma a uma
     * para esta pilha e em seguida embaralhadas, reiniciando o ciclo de compra.</p>
     *
     * @param discardPile a pilha de descarte cujas cartas serão recuperadas e embaralhadas
     */
    public void retrieveCards(PileOfCards discardPile) {
        System.out.println("Baralho vazio! Embaralhando pilha de descarte\n");
        while(!discardPile.isEmpty()){
            addCard(discardPile.popCard());
        }
        shuffle();
    }
}