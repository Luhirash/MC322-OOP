import java.util.Random;

public class PurchasePile extends PileOfCards {

    private Card[] differentCards;

    public PurchasePile(Card[] differentCards) {
        this.differentCards = differentCards;
    }

    protected void fillPile(int numberOfCards) {

        for (int i = 0; i < numberOfCards; i++) {//agora ele coloca na pilha as cartas de efeito tambem

            Random number = new Random();
            int index = number.nextInt(differentCards.length);

            Card originalCard = differentCards[index];

            if (originalCard instanceof bleedingCard) {
                bleedingCard pc = (bleedingCard) originalCard;
                super.addCard(new bleedingCard(pc.getName(), pc.getStaminaCost(), pc.getMainStat(), pc.get_description()));
            }
            else if (originalCard instanceof timeoutCard) {
                timeoutCard tc = (timeoutCard) originalCard;
                super.addCard(new timeoutCard(tc.getName(), tc.getStaminaCost(), tc.getMainStat(), tc.get_description()));
            }
            else if (originalCard instanceof DamageCard) {
                super.addCard(new DamageCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
            else {
                super.addCard(new ShieldCard(originalCard.getName(), originalCard.getStaminaCost(), originalCard.getMainStat(), originalCard.get_description()));
            }
        }
    }

    public void retrieveCards(PileOfCards discardPile) {
        System.out.println("Baralho vazio! Embaralhando pilha de descarte\n");
        while(!discardPile.isEmpty()){//enquanto houver termos
            addCard(discardPile.popCard());//sai do descarte e vai pra compra
        }
        shuffle();//embaralha novamente a pilha de compra
    }
}