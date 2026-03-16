import java.util.LinkedList;
import java.util.Collections;

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

    public int getSize(){
        return stack.size();
    }

    public void shuffle(){//embaralha a pilha
        Collections.shuffle(stack);
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }


}
