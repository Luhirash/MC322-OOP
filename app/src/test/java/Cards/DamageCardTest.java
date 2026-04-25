package Cards;

import Entities.Entity;
import Entities.Enemy;
import Entities.Hero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes que valida o comportamento das cartas de dano direto (DamageCard).
 */
class DamageCardTest {
    
    /**
     * Classe interna utilitária que simula um atacante e permite injetar um valor de bônus de força (strength bonus).
     */
    class Attacker extends Hero {
        private int mocked_strenght;

        public Attacker(int strenght) { 
            super("Anderson Silva", 100, 10);
            this.mocked_strenght = strenght;
        }
        @Override 
        public int getStrengthBonus() { 
            return this.mocked_strenght; 
        }
    }
    
    /**
     * Classe interna utilitária que simula um alvo passivo (saco de pancadas) para receber o dano.
     */
    class Target extends Enemy {
        public Target() { super("punch bag", 100, 10, 10); }
        @Override public Card[] getHits() { return new Card[0]; }
        @Override public void printIntentions(java.util.ArrayList<Card> c) {}
    }

    /**
     * Verifica se os valores informados no construtor da DamageCard são devidamente atribuídos
     * à instância, em especial o valor de dano infligido sendo o atributo principal (MainStat).
     */
    @Test
    void CorrectInitializationOfTheCard() {
        DamageCard kick = new DamageCard("high kick", 7, 12, "high kick");

        assertEquals("high kick", kick.getName());
        assertEquals(7, kick.getStaminaCost());
        assertEquals(12, kick.getDamageInflicted());
        assertEquals(12, kick.getMainStat()); 
    }

    /**
     * Testa o uso da carta de dano quando o atacante não possui bônus de força.
     * Verifica o consumo de fôlego, garante que nenhum Effect é retornado, e que o dano
     * subtraído da vida do alvo é exatamente o dano base da carta.
     */
    @Test
    void CauseNormalDamageWithoutBonus() {
        DamageCard jab = new DamageCard("Jab", 3, 5, "jab");

        Entity attacker_without_bonus = new Attacker(0); 
        Entity target = new Target();

        assertNull(jab.useCard(attacker_without_bonus, target), "DamageCard dont return Effects");

        assertEquals(7, attacker_without_bonus.getStamina(), "Must spend the exact amount of stamina(3)");
        assertEquals(95, target.getHealth(), "Target must receive only base damage(5)");
    }

    /**
     * Testa o uso da carta de dano quando o atacante possui um bônus de força (modificador).
     * Verifica se a vida final do alvo contabiliza corretamente o dano base da carta somado ao bônus do atacante.
     */
    @Test
    void CauseExtraDamageWithBonus() {
        DamageCard cross = new DamageCard("cross", 5, 8, "cross");

        Entity attacker_with_bonus = new Attacker(10); 
        Entity target = new Target();

        cross.useCard(attacker_with_bonus, target);

        assertEquals(5, attacker_with_bonus.getStamina(), "Must spend the exact amount of stamina(5)");

        assertEquals(82, target.getHealth(), "Target must receive base damage plus bonus damage");
    }

}