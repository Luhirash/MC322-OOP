package Cards;

import Entities.Entity;
import Entities.Enemy;
import Entities.Hero;
import Effects.Effect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes que assegura o correto funcionamento das cartas de defesa/escudo (ShieldCard).
 */
class ShieldCardTest {
    
    /**
     * Dummy do herói que usará a carta de escudo.
     */
    class attacker extends Hero {
        public attacker() { super("Anderson Silva", 100, 10); }
    }

    /**
     * Dummy do inimigo no contexto da batalha.
     */
    class target extends Enemy {
        public target() { super("enemy", 100, 10, 10); }
        @Override public Card[] getHits() { return new Card[0]; }
        @Override public void printIntentions(java.util.ArrayList<Card> c) {}
    }

    /**
     * Confere se a inicialização da carta de escudo mapeia corretamente 
     * a quantidade de dano bloqueado para a propriedade base MainStat da carta.
     */
    @Test
    void CorrectCardIntialization() {
        ShieldCard desviar = new ShieldCard("Desviar", 3, 8, "Esquiva do ataque");

        assertEquals("Desviar", desviar.getName(), "The card name must be correct");
        assertEquals(3, desviar.getStaminaCost(), "The stamina cost must be correct");
        assertEquals(8, desviar.getDamageBlocked(), "The intensity must be correct");

        assertEquals(8, desviar.getMainStat(), "The mainstat must be the intensity");
    }

    /**
     * Verifica o fluxo de uso do escudo. Assegura que fôlego é consumido,
     * nenhum efeito prolongado é retornado, o escudo do alvo continua zero, e 
     * o status de Shield do atacante é atualizado corretamente.
     */
    @Test
    void SpendStaminaAndAttackerReceiveShield() {
        ShieldCard focar = new ShieldCard("Focar", 2, 5, "Concentra a guarda");
        Entity attacker = new attacker(); 
        Entity target = new target();
        Effect generated_effect = focar.useCard(attacker, target);

        assertNull(generated_effect, "ShieldCard must not return type effect");

        assertEquals(8, attacker.getStamina(), "The attacker must spend the exact amount of stamina(2)");

        assertEquals(5, attacker.getShield(), "Attacker must receive shield points");
        assertEquals(0, target.getShield(), "The target must not receive shield");
    }

}