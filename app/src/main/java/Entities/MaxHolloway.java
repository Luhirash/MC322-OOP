package Entities;
import java.util.ArrayList;
import Cards.*;

/**
 * Inimigo que representa Max Holloway no combate.
 * <p>
 * Max Holloway é caracterizado por agilidade e golpes rápidos e sequenciais,
 * com um conjunto de cartas que inclui ataques de dano variados, pouca defesa,
 * e a habilidade de causar sangramento com {@link BleedingCard}.
 * Sua apresentação destaca sua leveza e velocidade.
 * </p>
 *
 * @see Enemy
 * @see BleedingCard
 */
public class MaxHolloway extends Enemy{
    
    /**
     * Constrói o inimigo Max Holloway.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public MaxHolloway(String name, int maxHealth, int maxStamina, int value) {
        super(name, maxHealth, maxStamina, value);
    }

    /**
     * Conjunto de cartas disponíveis para Max Holloway usar em combate.
     * Inclui golpes de alto dano, recuo defensivo e carta de sangramento.
     */
    private Card[] hollowayHits = {
        new DamageCard("socos rápidos", 6, 12, "desfere vários socos em sequência"),
        new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
        new BleedingCard("mini golpes", 4, 2, "Acerta golpes que causam dano aos poucos"),
        new BleedingCard("cotovelada cortante", 5, 3, "uma cotovelada precisa na cabeça que aplica 4 de intensidade de sangramento no inimigo"),
    };

    /**
     * Exibe no console as intenções de Max Holloway para o turno atual.
     *
     * @param chosenCards lista de cartas que Max Holloway planeja usar neste turno
     */
    @Override
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Max Holloway é veloz, por isso pretende usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Max Holloway.
     *
     * @return array de {@link Card} com os golpes de Max Holloway
     */
    @Override
    public Card[] getHits() {
        return hollowayHits;
    }
}