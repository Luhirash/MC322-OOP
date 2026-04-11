package Piles;
import java.util.ArrayList;
import Cards.*;

/**
 * Representa a mão do jogador: o conjunto de cartas disponíveis para uso no turno atual.
 *
 * <p>A mão tem um tamanho máximo fixo ({@link #maximumSize}) e é reabastecida a cada turno
 * comprando cartas do baralho ({@link PurchasePile}). Ao final do turno, as cartas
 * restantes na mão são devolvidas à pilha de descarte ({@link DiscardPile}).</p>
 *
 * <h2>Ciclo de vida da mão por turno</h2>
 * <ol>
 *   <li>{@link #drawCards(PurchasePile)} — compra cartas do baralho até atingir {@link #maximumSize}.</li>
 *   <li>{@link #printHand()} — exibe as cartas disponíveis para o jogador escolher.</li>
 *   <li>{@link #removeCard(int)} — remove e retorna a carta escolhida (ela vai para o descarte após o uso).</li>
 *   <li>{@link #returnCards(DiscardPile)} — ao fim do turno, devolve as cartas não usadas ao descarte.</li>
 * </ol>
 *
 * @see PurchasePile
 * @see DiscardPile
 * @see Turns#HeroTurn
 */
public class PlayerHand {
    
    /**
     * Lista interna que armazena as cartas atualmente na mão do jogador.
     */
    private ArrayList<Card> hand = new ArrayList<Card>();

    /**
     * Quantidade máxima de cartas que a mão pode conter ao mesmo tempo.
     * <p>Definida na construção e usada por {@link #isFull()} e {@link #drawCards(PurchasePile)}
     * para controlar quantas cartas são compradas por turno.</p>
     */
    private int maximumSize;

    /**
     * Constrói uma mão de jogador vazia com o tamanho máximo especificado.
     *
     * @param maximumSize quantidade máxima de cartas que a mão pode conter simultaneamente
     */
    public PlayerHand(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**
     * Adiciona uma carta à mão do jogador.
     * <p>Chamado internamente por {@link #drawCards(PurchasePile)} durante a compra de cartas.</p>
     *
     * @param card carta a ser adicionada à mão
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Remove e retorna a carta na posição indicada da mão.
     *
     * <p>Usada em dois contextos:</p>
     * <ul>
     *   <li>Em {@link Turns#HeroTurn}: remove a carta escolhida pelo jogador para jogá-la
     *       e enviá-la ao descarte.</li>
     *   <li>Em {@link DiscardPile#receiveCards(PlayerHand)}: esvazia a mão transferindo
     *       cada carta ao descarte.</li>
     * </ul>
     *
     * @param index índice (baseado em zero) da carta a ser removida
     * @return a carta removida da mão
     * @throws IndexOutOfBoundsException se o índice estiver fora do intervalo válido
     */
    public Card removeCard(int index) {
        return hand.remove(index);
    }

    /**
     * Retorna a quantidade de cartas atualmente na mão.
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
     * @return {@code true} se a mão estiver cheia ({@code handSize >= maximumSize});
     *         {@code false} se ainda há espaço para mais cartas
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
     * Exibe no console as cartas disponíveis na mão, numeradas a partir de 1.
     *
     * <p>Formato exibido para cada carta:</p>
     * <pre>
     * 1 - NomeDaCarta (AtributoPrincipal: X | Custo: Y)
     * 2 - ...
     * </pre>
     * <p>Chamado por {@link Turns#HeroTurn} antes de cada escolha do jogador.</p>
     */
    public void printHand() {
        ArrayList<Card> hand = getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(i + 1 + " - ");
            hand.get(i).printCardStats();
        }
    }

    /**
     * Verifica se a mão está vazia (sem nenhuma carta disponível).
     *
     * @return {@code true} se não houver cartas na mão; {@code false} caso contrário
     */
    public boolean isEmpty(){
        return hand.isEmpty();
    }

    /**
     * Retorna (sem remover) a carta na posição indicada da mão.
     *
     * <p>Usada por {@link Turns#HeroTurn} para inspecionar a carta escolhida antes
     * de tentar jogá-la via {@link Card#tryCard(Hero, Enemy, Turns)}.</p>
     *
     * @param index índice (baseado em zero) da carta a consultar
     * @return a carta na posição indicada, sem removê-la da mão
     * @throws IndexOutOfBoundsException se o índice estiver fora do intervalo válido
     */
    public Card getCard(int index){
        return hand.get(index);
    }

    /**
     * Compra cartas do baralho até que a mão atinja sua capacidade máxima.
     *
     * <p>Exibe a mensagem "Você comprou novas cartas!" e adiciona cartas do topo do
     * baralho ({@link PurchasePile#popCard()}) até que {@link #isFull()} retorne
     * {@code true}. Chamado no início de cada turno do herói pelo loop em {@link App#main}.</p>
     *
     * @param drawPile baralho de onde as cartas serão compradas
     */
    public void drawCards(PurchasePile drawPile) {
        System.out.println("Você comprou novas cartas!\n");
        while (!isFull()) {
            addCard(drawPile.popCard());
        }
    }

    /**
     * Devolve todas as cartas restantes na mão para a pilha de descarte.
     *
     * <p>Chamado ao final do turno do herói (em {@link App#main}) para garantir que
     * nenhuma carta fique retida na mão entre os turnos. As cartas são removidas
     * da mão e adicionadas ao descarte na mesma ordem em que estavam.</p>
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