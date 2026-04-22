package Piles;
import java.util.ArrayList;
import Cards.Card;

/**
 * Representa a mão do jogador durante o combate, ou seja, o conjunto de cartas
 * disponíveis para jogar na rodada atual.
 *
 * <p>A mão possui um tamanho máximo fixo ({@link #maximumSize}) definido na
 * construção. O ciclo de vida das cartas na mão a cada rodada é:</p>
 * <ol>
 *   <li>Compra de cartas do {@link PurchasePile} via {@link #drawCards}, até
 *       que a mão esteja cheia ou o baralho esgote.</li>
 *   <li>O jogador escolhe e joga cartas individualmente via {@link #getCard}
 *       e {@link #removeCard}, que seguem para o {@link DiscardPile}.</li>
 *   <li>Ao fim do turno, as cartas restantes são devolvidas ao descarte
 *       via {@link #returnCards}.</li>
 * </ol>
 *
 * @see PurchasePile
 * @see DiscardPile
 * @see Cards.Card
 */
public class PlayerHand {
    
    private ArrayList<Card> hand = new ArrayList<Card>();

    /** Quantidade máxima de cartas que a mão pode conter simultaneamente. */
    private int maximumSize;

    /**
     * Constrói uma mão de jogador vazia com o tamanho máximo especificado.
     *
     * @param maximumSize número máximo de cartas que a mão pode conter
     */
    public PlayerHand(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**
     * Adiciona uma carta à mão do jogador.
     *
     * <p>Não verifica se a mão está cheia; use {@link #isFull()} antes de chamar
     * este método se o limite precisar ser respeitado.</p>
     *
     * @param card carta a ser adicionada
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Remove e retorna a carta na posição indicada.
     *
     * <p>Utilizado após o jogador escolher uma carta para jogá-la, antes de
     * enviá-la ao {@link DiscardPile}.</p>
     *
     * @param index índice da carta na mão (base 0)
     * @return a carta removida
     * @throws IndexOutOfBoundsException se o índice estiver fora do intervalo válido
     */
    public Card removeCard(int index) {
        return hand.remove(index);
    }

    /**
     * Retorna a quantidade atual de cartas na mão.
     *
     * @return número de cartas presentes na mão
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * Retorna o tamanho máximo permitido para a mão.
     *
     * @return capacidade máxima da mão
     */
    private int getMaximumSize() {
        return maximumSize;
    }

    /**
     * Retorna a lista interna de cartas da mão.
     *
     * @return lista de cartas atualmente na mão
     */
    private ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Verifica se a mão atingiu sua capacidade máxima.
     *
     * @return {@code true} se a mão estiver cheia; {@code false} caso contrário
     */
    public boolean isFull() {
        if (getHandSize() < getMaximumSize()) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Exibe no terminal todas as cartas presentes na mão, numeradas a partir de 1.
     *
     * <p>Cada linha exibe o número de seleção seguido das estatísticas da carta
     * via {@link Cards.Card#printCardStats()}.</p>
     */
    public void printHand() {
        ArrayList<Card> hand = getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(i + 1 + " - ");
            hand.get(i).printCardStats();
        }
    }

    /**
     * Verifica se a mão está vazia.
     *
     * @return {@code true} se não houver nenhuma carta na mão; {@code false} caso contrário
     */
    public boolean isEmpty(){
        return hand.isEmpty();
    }

    /**
     * Retorna a carta na posição indicada sem removê-la da mão.
     *
     * @param index índice da carta (base 0)
     * @return a carta na posição especificada
     * @throws IndexOutOfBoundsException se o índice estiver fora do intervalo válido
     */
    public Card getCard(int index){
        return hand.get(index);
    }

    /**
     * Compra cartas do baralho de compra até que a mão esteja cheia.
     *
     * <p>Se o {@link PurchasePile} estiver vazio antes de a mão encher, as cartas
     * do {@link DiscardPile} são recuperadas e embaralhadas de volta ao baralho
     * via {@link PurchasePile#retrieveCards(DiscardPile)}, garantindo continuidade.</p>
     *
     * @param drawPile    baralho de onde as cartas são compradas
     * @param discardPile pilha de descarte usada para reabastecimento do baralho quando necessário
     */
    public void drawCards(PurchasePile drawPile, DiscardPile discardPile) {

        System.out.println("Você comprou novas cartas!\n");
        while (!isFull()) {
            if(drawPile.isEmpty())
                drawPile.retrieveCards(discardPile);
            addCard(drawPile.popCard());
        }
    }

    /**
     * Devolve todas as cartas restantes na mão para o {@link DiscardPile}.
     *
     * <p>Chamado ao fim do turno do herói para descartar cartas não utilizadas.
     * As cartas são removidas da mão em ordem e adicionadas ao topo do descarte.</p>
     *
     * @param discardPile pilha de descarte que receberá as cartas devolvidas
     */
    public void returnCards(DiscardPile discardPile) {
        int handSize = hand.size();
        for (int i = 0; i < handSize; i++) {
            discardPile.addCard(hand.removeFirst());
        }
    }
}