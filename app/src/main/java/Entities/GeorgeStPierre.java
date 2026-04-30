package Entities;
import java.util.ArrayList;
import java.util.List;
import Cards.*;

/**
 * Inimigo que representa George St-Pierre no combate.
 * <p>
 * George St-Pierre é caracterizado por agilidade e golpes rápidos,
 * com um conjunto de cartas que inclui ataques de dano variados, defesa
 * e a habilidade de causar sangramento com {@link BleedingCard}.
 * Sua apresentação destaca sua leveza e velocidade.
 * </p>
 *
 * @see Enemy
 * @see BleedingCard
 */
public class GeorgeStPierre extends Enemy{
    
    /**
     * Constrói o inimigo George St-Pierre.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public GeorgeStPierre(String name, int maxHealth, int maxStamina, int value) {
        super(name, maxHealth, maxStamina, value);
    }

    /**
     * Conjunto de cartas disponíveis para George St-Pierre usar em combate.
     * Inclui golpes de alto dano, recuo defensivo e carta de sangramento.
     */
    private ArrayList<Card> stPierreHits = new ArrayList<>(List.of(
        new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
        new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
        new ShieldCard("melhorar posicionamento", 3, 8, "lentamente muda sua posição"),
        new BleedingCard("desgastar adversário", 3, 2, "mantém a pressão, desgastando o adversário")

    ));

    /**
     * Exibe no console as intenções de George St-Pierre para o turno atual.
     *
     * @param chosenCards lista de cartas que George St-Pierre planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("George St-Pierre é muito inteligente, então vai usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para George St-Pierre.
     *
     * @return array de {@link Card} com os golpes de George St-Pierre
     */
    @Override
    public ArrayList<Card> getHits() {
        return stPierreHits;
    }
}