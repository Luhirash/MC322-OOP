package Entities;
import java.util.ArrayList;
import Cards.*;

/**
 * Inimigo que representa Israel Adesanya no combate.
 * <p>
 * Israel Adesanya  é caracterizado por ser evasivo, mas ter pouca defesa,
 * com um conjunto de cartas que inclui ataques de dano simples,
 * e a habilidade de pedir tempo técnico {@link HealingCard}.
 * </p>
 *
 * @see Enemy
 * @see HealingCard
 */
public class IsraelAdesanya extends Enemy{
    
    /**
     * Constrói o inimigo Israel Adesanya.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public IsraelAdesanya(String name, int maxHealth, int maxStamina) {
        super(name, maxHealth, maxStamina);
    }

    /**
     * Conjunto de cartas disponíveis para Israel Adesanya usar em combate.
     * Inclui golpes de baixo dano, recuo defensivo e carta de tempo técnico.
     */
    private Card[] allenHits = {
        new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
        new DamageCard("empurrão", 2, 3, "desfere um soco com a mão direita na cabeça do inimigo"),
        new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo"),
        new ShieldCard("andar para trás", 1, 3, "dá um passo para trás, fungindo do inimigo"),
        new HealingCard("pedir tempo técnico", 4, 3, "pausa a luta e aplica 3 de intensidade de cura no herói")
    };

    /**
     * Exibe no console as intenções de Israel Adesanya para o turno atual.
     *
     * @param chosenCards lista de cartas que Israel Adesanya planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Israel Adesanya é um bom defensor, e pensa em usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Israel Adesanya.
     *
     * @return array de {@link Card} com os golpes de Israel Adesanya
     */
    @Override
    public Card[] getHits() {
        return allenHits;
    }
}