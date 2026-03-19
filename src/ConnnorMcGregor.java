import java.util.ArrayList;

public class ConnnorMcGregor extends Enemy{
    
    public ConnnorMcGregor(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    private Card[] mcGregorHits = {
        new DamageCard("chute na cabeça", 7, 12, "desfere um chute com a perna direita na cabeça do inimigo"),
        new DamageCard("direto", 5, 8, "desfere um soco com a mão direita na cabeça do inimigo"),
        new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
    };

    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("Connor McGregor é leve e rápido, então vai usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    public Card[] getHits() {
        return mcGregorHits;
    }
}
