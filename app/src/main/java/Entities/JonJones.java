package Entities;
import java.util.ArrayList;
import Cards.*;

/**
 * Inimigo que representa Jon Jones no combate.
 * <p>
 * Jon Jones é caracterizado por alta fôlego e um conjunto de cartas que inclui
 * golpes de dano pesado e a habilidade de ganhar bônus de força extra com {@link StrengthCard}.
 * Sua apresentação destaca sua energia e poder.
 * </p>
 *
 * @see Enemy
 * @see StrengthCard
 */
public class JonJones extends Enemy{
    
    /**
     * Constrói o inimigo Jon Jones.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public JonJones(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Conjunto de cartas disponíveis para Jon Jones usar em combate.
     * Inclui golpes de dano, desvio defensivo e carta de força extra.
     */
    private Card[] jonesHits = {
        new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
        new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
        new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo"),
        new StrengthCard("força extra" , 3, 2, "aumenta o dano de JonJones conforme a intensidade do efeito")
    };

    /**
     * Exibe no console as intenções de Jon Jones para o turno atual.
     *
     * @param chosenCards lista de cartas que Jon Jones planeja usar neste turno
     */
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Jon Jones tem muita energia! Por isso pretende usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Jon Jones.
     *
     * @return array de {@link Card} com os golpes de Jon Jones
     */
    public Card[] getHits() {
        return jonesHits;
    }

}