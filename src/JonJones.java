import java.util.ArrayList;

public class JonJones extends Enemy{
    
    public JonJones(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    private Card[] jonesHits = {
        new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
        new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
        new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo")
    };

    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("Jon Jones tem muita energia! Por isso pretende usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    public Card[] getHits() {
        return jonesHits;
    }
}
