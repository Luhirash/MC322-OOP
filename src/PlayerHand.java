import java.util.ArrayList;

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

    private int getHandSize() {
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
            System.out.println("Sua mão já está cheia!");
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
}
