package Piles;
import java.util.ArrayList;

import Cards.Card;

public class PlayerHand {
    
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int maximumSize;

    public PlayerHand(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card useCard(int index) {
        return hand.remove(index);
    }

    public int getHandSize() {
        return hand.size();
    }

    private int getMaximumSize() {
        return maximumSize;
    }

    private ArrayList<Card> getHand() {
        return hand;
    }

    public boolean isFull() {
        if (getHandSize() < getMaximumSize()) {
            return false;
        }
        else {
            return true;
        }
    }

    public void printHand() {
        ArrayList<Card> hand = getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(i + 1 + " - ");
            hand.get(i).printCardStats();
        }
    }

    public boolean isEmpty(){
        return hand.isEmpty();
    }

    public Card getCard(int index){
        return hand.get(index);
    }

    public void drawCards( PurchasePile drawPile) {
        System.out.println("Você comprou novas cartas!\n");
        while (!isFull()) {
            addCard(drawPile.popCard());
        }
    }

    public void returnCards(DiscardPile discardPile) {
        int handSize = hand.size();
        for (int i = 0; i < handSize; i++) {
            discardPile.addCard(hand.removeFirst());
        }
    }
}
