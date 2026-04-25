package Entities;
import java.util.ArrayList;
import Cards.*;

/**
 * Inimigo que representa Khabib Nurmagomedov no combate.
 * <p>
 * Khabib Nurmagomedov é caracterizado por alta fôlego e um conjunto de cartas que inclui
 * golpes de dano pesado e a habilidade de ganhar bônus de força extra com {@link StrengthCard}.
 * Sua apresentação destaca sua energia e poder.
 * </p>
 *
 * @see Enemy
 * @see StrengthCard
 */
public class KhabibNurmagomedov extends Enemy{
    
    /**
     * Constrói o inimigo Khabib Nurmagomedov.
     *
     * @param name       nome do inimigo
     * @param maxHealth  vida máxima
     * @param maxStamina fôlego máxima
     */
    public KhabibNurmagomedov(String name, int maxHealth, int maxStamina, int value) {
        super(name, maxHealth, maxStamina, value);
    }

    /**
     * Conjunto de cartas disponíveis para Khabib Nurmagomedov usar em combate.
     * Inclui golpes de dano, desvio defensivo e carta de força extra.
     */
    private Card[] khabibHits = {
        new DamageCard("queda de double leg", 6, 8, "derruba seu adversário"),
        new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
        new ShieldCard("Intimidação física", 2, 4, "coloca medo no adversário"),
        new StrengthCard("pressão na grade" , 3, 2, "aumenta o dano de Khabib conforme a intensidade do efeito")
    };

    /**
     * Exibe no console as intenções de Khabib Nurmagomedov para o turno atual.
     *
     * @param chosenCards lista de cartas que Khabib Nurmagomedov planeja usar neste turno
     */
    public void printIntentions(ArrayList<Card> chosenCards) {
        System.out.println("----------------------------------");
        System.out.println("Khabib Nurmagomedov é forte e resistente, então quer usar:");
        for (int i = 0; i < chosenCards.size(); i++)
            chosenCards.get(i).printCardStats();
        System.out.println("----------------------------------\n");
    }

    /**
     * Retorna o array de cartas disponíveis para Khabib Nurmagomedov.
     *
     * @return array de {@link Card} com os golpes de Khabib Nurmagomedov
     */
    public Card[] getHits() {
        return khabibHits;
    }

}