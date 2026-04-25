package Possibilities;
import Cards.Card;

public class CardPossibility extends Possibility {
    
    private Card card;

    public CardPossibility(String description, Card card) {
        super(description);
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }

    
}
