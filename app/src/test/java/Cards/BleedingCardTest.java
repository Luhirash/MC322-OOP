package Cards;

import Entities.Entity;
import Entities.Enemy;
import Entities.Hero;
import Effects.Effect;
import Effects.Bleeding;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes responsável por verificar o comportamento da carta de sangramento (BleedingCard).
 */
class BleedingCardTest {
    
    /**
     * Classe interna utilitária (Dummy) que simula um Herói atacante para os testes.
     */
    class attacker extends Hero {
        public attacker() { super("Anderson Silva", 100, 10); }
    }
    
    /**
     * Classe interna utilitária (Dummy) que simula um Inimigo alvo para os testes.
     */
    class target extends Enemy {
        public target() { super("Connor Mcgregor", 100, 10, 10); }
        @Override public Card[] getHits() { return new Card[0]; }
        @Override public void printIntentions(java.util.ArrayList<Card> c) {}
    }

    /**
     * Verifica se a carta de sangramento é inicializada corretamente com os valores passados no construtor.
     * Valida o nome, custo de fôlego (stamina) e o atributo principal (intensidade do sangramento).
     */
    @Test
    void CorrectCardIntialization() {
        BleedingCard corte = new BleedingCard("Golpe Lascerante", 3, 4, "Um corte profundo");

        assertEquals("Golpe Lascerante", corte.getName(), "The name must be correct");
        assertEquals(3, corte.getStaminaCost(), "The stamina cost must be correct");
        assertEquals(4, corte.getMainStat(), "The mainstat must be the same as the intensity");
    }

    /**
     * Verifica o fluxo completo de uso de uma BleedingCard.
     * Garante que o fôlego do atacante seja deduzido e que um efeito do tipo Bleeding seja retornado 
     * e aplicado ao alvo correto, com a intensidade correta.
     */
    @Test
    void SpendStaminaAndApplyBleedingEffect() {
        BleedingCard cotovelada = new BleedingCard("Cotovelada Cortante", 4, 5, "Abre o supercílio");
        Entity attacker = new attacker();
        Entity target = new target();
        Effect generated_Effect = cotovelada.useCard(attacker, target);

        assertEquals(6, attacker.getStamina(), "The attacker must spend stamina");

        assertNotNull(generated_Effect, "The card must return effect type");
        assertInstanceOf(Bleeding.class, generated_Effect, "The effect must be from bleeding type");
        assertEquals("Sangramento", generated_Effect.getName(), "The general name os the effect must be Sangramento");
        assertEquals(5, generated_Effect.getIntensity(), "The effect must have the correct intensity");

        assertEquals(target, generated_Effect.getOwner(), "The target mus receive the bleeding, not the attacker");
    }

}