package Entities;
import java.util.ArrayList;
import Cards.*;

/**
 * Inimigo que representa Francis Ngannou no combate.
 * <p>
 * Francis Ngannou é caracterizado por uma força incrível, mas pouco fôlego
 * com um conjunto de cartas que inclui ataques de dano variados, defesa
 * e a habilidade de causar sangramento com {@link bleedingCard}.
 * Sua apresentação destaca sua força e durabilidade.
 * </p>
 *
 * @see Enemy
 * @see bleedingCard
 */
public class FrancisNgannou extends Enemy{
    
    /**
     * Constrói o inimigo Francis Ngannou.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public FrancisNgannou(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Conjunto de cartas disponíveis para Francis Ngannou usar em combate.
     * Inclui golpes de alto dano, e carta de sangramento.
     */
    private Card[] ngannouHits = {
        new DamageCard("Overhand de direita", 8, 20, "desfere soco muito poderoso, mas que gasta toda sua energia"),
        new DamageCard("Uppercut", 5, 10, "desfere um soco ascendente"),
        new ShieldCard("Intimidação física", 2, 4, "coloca medo no adversário"),
        new BleedingCard("cruzado atordoador", 5, 4, "Acerta um direto de raspão na testa do adversário")
    };

    /**
     * Exibe no console as intenções de Francis Ngannou para o turno atual.
     *
     * @param chosenCards lista de cartas que Francis Ngannou planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Francis Ngannou é pesado, então vai usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Francis Ngannou.
     *
     * @return array de {@link Card} com os golpes de Francis Ngannou
     */
    @Override
    public Card[] getHits() {
        return ngannouHits;
    }
}