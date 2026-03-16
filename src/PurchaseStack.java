import java.util.Random;

public class PurchaseStack extends StackOfCards {

    private Card[] differentCards;

    public PurchaseStack(Card[] differentCards) {
        this.differentCards = differentCards;
    }

    protected void fillStack(int numberOfCards) {

        for (int i = 0; i < numberOfCards; i++) {

            Random number = new Random();
            int index = number.nextInt(differentCards.length);

            Card originalCard = differentCards[index];

            if (originalCard instanceof DamageCard) {
                super.addCard(new DamageCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
            else {
                super.addCard(new ShieldCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
        }
    }
}