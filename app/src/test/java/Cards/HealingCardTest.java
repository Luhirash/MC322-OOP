package Cards;

import Entities.Entity;
import Entities.Enemy;
import Entities.Hero;
import Effects.Effect;
import Effects.Healing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes que verifica a funcionalidade das cartas de cura (HealingCard).
 */
class HealingCardTest {

    /**
     * Classe interna (Dummy) que representa o herói que executará a cura.
     */
    class Dummyhero extends Hero {
        public Dummyhero() { super("Anderson Silva", 100, 10); }
    }

    /**
     * Classe interna (Dummy) que representa um inimigo no combate durante o uso da carta.
     */
    class DummyEnemy extends Enemy {
        public DummyEnemy() { super("Enemy", 100, 10); }
        @Override public Card[] getHits() { return new Card[0]; }
        @Override public void printIntentions(java.util.ArrayList<Card> c) {}
    }

    /**
     * Assegura que a HealingCard seja inicializada corretamente, guardando o nome,
     * custo de fôlego e o status de intensidade da cura apropriadamente.
     */
    @Test
    void CorrectCardIntialization() {
        HealingCard timeout = new HealingCard("time out", 4, 2, "time out");

        assertEquals("time out", timeout.getName(), "The card name must be correct");
        assertEquals(4, timeout.getStaminaCost(), "The stamina cost must be correct");
        assertEquals(2, timeout.getMainStat(), "The intensity must be correct");
    }

    /**
     * Verifica o fluxo de execução da HealingCard. Atesta o consumo de fôlego,
     * e garante a geração de um efeito do tipo "Healing", que deve ser atrelado 
     * ao próprio atacante (usuário da carta), e não ao alvo/inimigo.
     */
    @Test
    void SpendStaminaAndApplyEffect() {
        HealingCard secretJuice = new HealingCard("Secret juice", 2, 3, "different water");
        Entity attacker = new Dummyhero(); // Fôlego = 10
        Entity target = new DummyEnemy();

        Effect generatedEffect = secretJuice.useCard(attacker, target);
        assertEquals(8, attacker.getStamina(), "The attacker must spend stamina to use the card");

        assertNotNull(generatedEffect, "The card must return type Effect");
        assertInstanceOf(Healing.class, generatedEffect, "The effect must be a healing class");
        assertEquals("Recuperação", generatedEffect.getName(), "The general name of the effect must be Recuperação");
        assertEquals(3, generatedEffect.getIntensity(), "The intensity of the effect must be the same of the card");
        assertEquals(attacker, generatedEffect.getOwner(), "The attacker must receive the effect not the target");
    }
}