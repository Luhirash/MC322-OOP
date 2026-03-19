import java.util.Random;

public class PurchasePile extends PileOfCards {

    private Card[] differentCards;

    public PurchasePile(Card[] differentCards) {
        this.differentCards = differentCards;
    }

    protected void fillPile(int numberOfCards) {

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

    public void retrieveCards(PileOfCards discardPile) {
        System.out.println("\n Baralho vazio! Embaralhando pilha de descarte");
        while(!discardPile.isEmpty()){//enquanto houver termos
            addCard(discardPile.popCard());//sai do descarte e vai pra compra
        }
        shuffle();//embaralha novamente a pilha de compra
    }
}


