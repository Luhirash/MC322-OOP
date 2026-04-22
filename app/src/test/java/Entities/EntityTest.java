package Entities;

import Cards.Card;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    class Dummy extends Entity {
        public Dummy(String name, int maxHealth, int maxStamina) {
            super(name, maxHealth, maxStamina);
    }
        @Override
        public Card[] getHits(){
            return new Card[0];
        }

    @Test
    void LosesHealthWithoutShield(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.receiveDamage(20);
        assertEquals(80, target.getHealth(), "The target must lose 20 health");
        assertTrue(target.isAlive(), "The target must be alive");
    }

    @Test
    void LosesShieldWhenDamageIsLessThanShieldPoints(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(20);
        assertEquals(100, target.getHealth(), "The target must not lose health");
        assertEquals(10, target.getShield(), "The target shield points must decrease by 20");
    }

    @Test
    void LosesHealthAndBreakShieldWhenDamageIsHigherThanShieldPoints(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(50);
        assertEquals(80, target.getHealth(), "The target must lose 20 health");
        assertEquals(0, target.getShield(), "The target shield points must be 0(break)");

    }

    @Test
    void MustDieAndZeroAllStatusWhenDamageIsFatal(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(200);
        assertEquals(0, target.getHealth(), "The target must have 0 health(dead)");
        assertEquals(0, target.getShield(), "The target shield points must be 0(break)");
        assertFalse(target.isAlive(), "isAlive must be false");

    }

    @Test
    void ZeroShieldAndFullStaminaAtTheStartOfTurn(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.spendStamina(7);
        target.newTurn();
        assertEquals(10, target.getStamina(), "The target must have full stamina");
        assertEquals(0, target.getShield(), "The target shield points must be 0");
    }

    
}
}
