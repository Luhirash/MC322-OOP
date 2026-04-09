import java.util.LinkedList;
import java.util.Collections;

public class PileOfCards {
    
    protected LinkedList<Card> pile = new LinkedList<Card>();
    
    public void addCard(Card card) {
        pile.push(card);
    }

    public Card popCard() {
        return pile.pop();
    }

    public void clearPile() {
        pile.clear();
    }

    public int getSize(){
        return pile.size();
    }

    public void shuffle(){//embaralha a pilha
        Collections.shuffle(pile);
    }

    public boolean isEmpty(){
        return pile.isEmpty();
    }


}
