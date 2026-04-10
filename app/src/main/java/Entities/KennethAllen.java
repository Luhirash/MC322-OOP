package Entities;
import java.util.ArrayList;

import Cards.Card;
import Cards.DamageCard;
import Cards.ShieldCard;
import Cards.bleedingCard;

/**
 * Inimigo que representa Kenneth Allen no combate.
 * <p>
 * Kenneth Allen  é caracterizado por ser fraco e ter pouca defesa,
 * com um conjunto de cartas que inclui ataques de dano variados,
 * e a habilidade desferir uma paulistinha em seu adversário {@link bleedingCard}.
 * </p>
 *
 * @see Enemy
 * @see bleedingCard
 */
public class KennethAllen extends Enemy{
    
    /**
     * Constrói o inimigo Kenneth Allen.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public KennethAllen(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Conjunto de cartas disponíveis para Kenneth Allen usar em combate.
     * Inclui golpes de alto dano, recuo defensivo e carta de sangramento.
     */
    private Card[] allenHits = {
        new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
        new DamageCard("empurrão", 2, 3, "desfere um soco com a mão direita na cabeça do inimigo"),
        new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
        new bleedingCard("paulistinha", 5, 2, "Acerta uma joelhada na coxa, causando 2 de intensidade de veneno")
    };

    /**
     * Exibe no console as intenções de Kenneth Allen para o turno atual.
     *
     * @param chosenCards lista de cartas que Kenneth Allen planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Kenneth Allen é fraco, mas eficiente, então vai usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Kenneth Allen.
     *
     * @return array de {@link Card} com os golpes de Kenneth Allen
     */
    @Override
    public Card[] getHits() {
        return allenHits;
    }
}