package Piles;

import Cards.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes responsável por validar o comportamento do baralho de compra (PurchasePile).
 * Garante que o baralho seja preenchido corretamente e que consiga recuperar cartas da pilha de descarte.
 */
public class PurchasePileTest {
    
    /**
     * Verifica se o método fillPile preenche o baralho corretamente com a quantidade desejada 
     * de novas instâncias de cartas. O preenchimento deve ser baseado no molde (HeroMold) 
     * fornecido no construtor. Garante que as cartas geradas não sejam nulas e que a 
     * quantidade exata (neste caso, 100) seja inserida no baralho.
     */
    @Test
    void MustFillThePileWithNewInstancesOfCards(){
        Card [] HeroMold = {
            new BleedingCard("bleeding", 2, 3, "causes bleeding"),
            new DamageCard("punch", 4, 6, "throw a punch"),
            new ShieldCard("Block", 2, 4, "block an incoming attack"),
            new HealingCard("first aid", 2, 2, "uses first aid kit to heal")
        };

        PurchasePile deck = new PurchasePile(HeroMold);

        deck.fillPile(100);
        assertFalse(deck.isEmpty());

        int generatedCards = 0;
        while(!deck.isEmpty()){
            Card pulled_Card = deck.popCard();
            assertNotNull(pulled_Card, "The generated cards must not be NULL");
            generatedCards ++;
        }

        assertEquals(100, generatedCards, "The deck must have the desire amount of cards(100)");
    }

    /**
     * Verifica se o método retrieveCards transfere corretamente todas as cartas de uma 
     * pilha de descarte (PileOfCards) de volta para o baralho de compra.
     * Assegura que, após a operação, a pilha de descarte fique completamente vazia e que 
     * a quantidade exata de cartas resgatadas esteja disponível no baralho de compra.
     */
    @Test
    void RetriveAllDiscardedCardWhenDeckIsEmpty(){
        Card[] emptyMold = new Card[0];

        PurchasePile deck = new PurchasePile(emptyMold);

        PileOfCards discarded = new PileOfCards(){};
        discarded.addCard(new DamageCard("punch", 1, 4, "damage"));
        discarded.addCard(new ShieldCard("block", 1, 4, "shield"));
        discarded.addCard(new HealingCard("aid", 1, 4, "heal"));

        deck.retrieveCards(discarded);

        assertTrue(discarded.isEmpty());//it  must be empty after retrievied
        assertFalse(deck.isEmpty());

        int cards_deck = 0;
        while(!deck.isEmpty()){
            deck.popCard();
            cards_deck ++;
        }
        assertEquals(3, cards_deck, "The (3) cards must go from the discard to the deck");
    }
}