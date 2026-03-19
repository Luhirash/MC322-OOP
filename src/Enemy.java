import java.util.Random;
import java.util.ArrayList;

public class Enemy extends Entity {
    
    public Enemy(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    public void attack(Hero hero, DamageCard damageCard){
        this.spendStamina(damageCard.getStaminaCost());
        hero.receiveDamage(damageCard.getDamageInflicted());
        System.out.println(this.getName() + " golpeou com um " + damageCard.getName() + " causando " + damageCard.getDamageInflicted() + " de dano");
    }

    public ArrayList<Card> chooseCards(Card[] hits) {
        ArrayList<Card> chosenCards = new ArrayList<Card>();
        int availableStamina = getMaxStamina();
        while (availableStamina > 0) {
            Card possibleCard = chooseRandomCard(hits);
            if (availableStamina >= possibleCard.getStaminaCost()) {
                chosenCards.add(possibleCard);
                availableStamina -= possibleCard.getStaminaCost();
            }
            else
                break;
        }
        return chosenCards;
    }

    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println(getName() + " pretende fazer essas ações em seu próximo turno:");
        printChosenCards(chosenCards);
    }

    private void printChosenCards(ArrayList<Card> chosenCards) {
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println();
    }

    private Card chooseRandomCard(Card[] hits) {
        Random number = new Random();
        int action = number.nextInt(6);//ataques do inimigo escolhidos
        return hits[action];
    }

}
