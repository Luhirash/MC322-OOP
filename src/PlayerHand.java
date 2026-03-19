import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("Suas cartas disponíveis:");
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

    public void drawCards(Scanner scanner, PurchasePile drawPile) {
        int choice = 1;
        if (isEmpty())
            System.out.println("Sua mão está vazia!");
        else
            printHand();
        while(!isFull() && choice == 1) {
            System.out.println("O que você deseja?");
            System.out.println("1 - comprar carta");
            System.out.println("2 - seguir para seu turno");
            choice = scanner.nextInt();
            while (choice != 1 && choice != 2) {
                System.out.println("Escolha inválida!");
                choice = scanner.nextInt();
            }

            if (choice == 1) {
                Card card = drawPile.popCard();
                System.out.println("\nEsta foi a carta que você comprou:");
                card.printCardStats();
                System.out.println();
                addCard(card);
            }
            else 
                break;
        }
        if (isFull())
            System.out.println("Sua mão está cheia!");
    }

}
