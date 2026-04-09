import java.util.LinkedList;
import java.util.Collections;

/**
 * Classe base que representa uma pilha genérica de cartas.
 * <p>
 * Utiliza uma {@link LinkedList} internamente para suportar operações de pilha
 * (push/pop). É estendida por {@link PurchasePile} (pilha de compra/baralho)
 * e {@link DiscardPile} (pilha de descarte).
 * </p>
 *
 * @see PurchasePile
 * @see DiscardPile
 */
public class PileOfCards {
    
    /** Estrutura interna que armazena as cartas da pilha. */
    protected LinkedList<Card> pile = new LinkedList<Card>();
    
    /**
     * Adiciona uma carta ao topo da pilha.
     *
     * @param card carta a ser adicionada
     */
    public void addCard(Card card) {
        pile.push(card);
    }

    /**
     * Remove e retorna a carta do topo da pilha.
     *
     * @return carta removida do topo
     */
    public Card popCard() {
        return pile.pop();
    }

    /**
     * Remove todas as cartas da pilha.
     */
    public void clearPile() {
        pile.clear();
    }

    /**
     * Retorna a quantidade de cartas na pilha.
     *
     * @return número de cartas presentes
     */
    public int getSize(){
        return pile.size();
    }

    /**
     * Embaralha aleatoriamente as cartas da pilha.
     */
    public void shuffle(){
        Collections.shuffle(pile);
    }

    /**
     * Verifica se a pilha está vazia.
     *
     * @return {@code true} se não houver cartas; {@code false} caso contrário
     */
    public boolean isEmpty(){
        return pile.isEmpty();
    }


}