import java.util.Random;
import java.util.ArrayList;

public abstract class Enemy extends Entity {
    
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

    public abstract void printIntentions(ArrayList<Card> chosenCards);
    public abstract Card[] getHits();


    public abstract void applyEffects(Turns turns, Hero hero);//inimigos aplicam efeitos também

    protected void printChosenCards(ArrayList<Card> chosenCards) {
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
    }

    private Card chooseRandomCard(Card[] hits) {
        Random number = new Random();
        int action = number.nextInt(hits.length);//ataques do inimigo escolhidos
        return hits[action];
    }

}