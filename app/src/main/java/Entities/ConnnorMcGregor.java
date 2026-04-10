package Entities;
import java.util.ArrayList;

import Cards.Card;
import Cards.DamageCard;
import Cards.ShieldCard;
import Cards.bleedingCard;

/**
 * Inimigo que representa Connor McGregor no combate.
 * <p>
 * Connor McGregor é caracterizado por agilidade e golpes rápidos,
 * com um conjunto de cartas que inclui ataques de dano variados, defesa
 * e a habilidade de causar sangramento com {@link bleedingCard}.
 * Sua apresentação destaca sua leveza e velocidade.
 * </p>
 *
 * @see Enemy
 * @see bleedingCard
 */
public class ConnnorMcGregor extends Enemy{
    
    /**
     * Constrói o inimigo Connor McGregor.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public ConnnorMcGregor(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Conjunto de cartas disponíveis para Connor McGregor usar em combate.
     * Inclui golpes de alto dano, recuo defensivo e carta de sangramento.
     */
    private Card[] mcGregorHits = {
        new DamageCard("chute na cabeça", 7, 12, "desfere um chute com a perna direita na cabeça do inimigo"),
        new DamageCard("direto", 5, 8, "desfere um soco com a mão direita na cabeça do inimigo"),
        new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
        new bleedingCard("raspão na testa", 3, 2, "Acerta um direto de raspão na testa do adversário")
    };

    /**
     * Exibe no console as intenções de Connor McGregor para o turno atual.
     *
     * @param chosenCards lista de cartas que Connor McGregor planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Connor McGregor é leve e rápido, então vai usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Connor McGregor.
     *
     * @return array de {@link Card} com os golpes de Connor McGregor
     */
    @Override
    public Card[] getHits() {
        return mcGregorHits;
    }
}