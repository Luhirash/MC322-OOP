package Piles;
import java.util.LinkedList;

import Cards.Card;

import java.util.Collections;

/**
 * Classe base que representa uma pilha genérica de cartas.
 *
 * <p>Utiliza internamente uma {@link LinkedList} para suportar eficientemente
 * as operações de pilha (empilhar/desempilhar pelo topo). Serve de base para
 * {@link PurchasePile} (o baralho de compra do herói) e {@link DiscardPile}
 * (a pilha de descarte das cartas usadas).</p>
 *
 * <h2>Fluxo das cartas no jogo</h2>
 * <pre>
 * PurchasePile → (compra) → PlayerHand → (uso) → DiscardPile
 *      ↑                                               |
 *      └────── (baralho vazio: retrieveCards) ─────────┘
 * </pre>
 *
 * @see PurchasePile
 * @see DiscardPile
 */
public class PileOfCards {
    
    /**
     * Estrutura interna que armazena as cartas desta pilha.
     * <p>O topo da pilha corresponde ao início da lista ({@code push}/{@code pop}).</p>
     */
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
     * <p>Usado por {@link PlayerHand#drawCards(PurchasePile)} para comprar cartas.</p>
     *
     * @return carta removida do topo da pilha
     * @throws java.util.NoSuchElementException se a pilha estiver vazia
     */
    public Card popCard() {
        return pile.pop();
    }

    /**
     * Remove todas as cartas da pilha, deixando-a vazia.
     */
    public void clearPile() {
        pile.clear();
    }

    /**
     * Retorna a quantidade de cartas atualmente na pilha.
     *
     * @return número de cartas presentes
     */
    public int getSize(){
        return pile.size();
    }

    /**
     * Embaralha aleatoriamente as cartas da pilha.
     * <p>Chamado pelo {@link PurchasePile} após o preenchimento inicial
     * e após recuperar cartas do descarte.</p>
     */
    public void shuffle(){
        Collections.shuffle(pile);
    }

    /**
     * Verifica se a pilha está vazia.
     *
     * @return {@code true} se não houver cartas na pilha; {@code false} caso contrário
     */
    public boolean isEmpty(){
        return pile.isEmpty();
    }


}