public class DiscardPile extends PileOfCards{
    
    public void receiveCards(PlayerHand hand) {
        while(hand.getHandSize() > 0) {
            addCard(hand.useCard(0));
        }
    }
}
