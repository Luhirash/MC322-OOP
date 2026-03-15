import java.util.LinkedList;

public class StackOfCards {
    
    protected LinkedList<Card> stack = new LinkedList<Card>();
    
    public void addCard(Card card) {
        stack.push(card);
    }

    public Card popCard() {
        return stack.pop();
    }

    public void clearStack() {
        stack.clear();
    }
}
