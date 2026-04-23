package Entities;

import Cards.Card;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes dedicada a validar as mecânicas fundamentais compartilhadas por
 * todas as entidades do jogo (vida, escudo, controle de fôlego e transição de turnos).
 */
public class EntityTest {

    /**
     * Classe utilitária (Dummy) para testar os métodos implementados na classe abstrata Entity.
     */
    class Dummy extends Entity {
        public Dummy(String name, int maxHealth, int maxStamina) {
            super(name, maxHealth, maxStamina);
    }
        @Override
        public Card[] getHits(){
            return new Card[0];
        }

    /**
     * Verifica o comportamento da entidade ao receber dano quando não possui pontos de escudo.
     * O dano deve ser subtraído diretamente dos pontos de vida.
     */
    @Test
    void LosesHealthWithoutShield(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.receiveDamage(20);
        assertEquals(80, target.getHealth(), "The target must lose 20 health");
        assertTrue(target.isAlive(), "The target must be alive");
    }

    /**
     * Verifica a absorção completa de dano pelo escudo.
     * Ocorre quando o dano recebido é estritamente menor que a quantidade de escudo atual.
     * A vida deve permanecer intacta.
     */
    @Test
    void LosesShieldWhenDamageIsLessThanShieldPoints(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(20);
        assertEquals(100, target.getHealth(), "The target must not lose health");
        assertEquals(10, target.getShield(), "The target shield points must decrease by 20");
    }

    /**
     * Verifica a absorção parcial de dano e a quebra do escudo.
     * Ocorre quando o dano recebido é maior que a quantidade de escudo. O escudo 
     * deve ser zerado e o valor de dano excedente deve ser deduzido da vida da entidade.
     */
    @Test
    void LosesHealthAndBreakShieldWhenDamageIsHigherThanShieldPoints(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(50);
        assertEquals(80, target.getHealth(), "The target must lose 20 health");
        assertEquals(0, target.getShield(), "The target shield points must be 0(break)");

    }

    /**
     * Verifica o estado da entidade após receber um ataque fatal.
     * A vida não deve ficar negativa (deve ser cravada em 0), o escudo deve ser 
     * destruído e a flag isAlive deve indicar que a entidade não sobreviveu.
     */
    @Test
    void MustDieAndZeroAllStatusWhenDamageIsFatal(){
        Entity target = new Dummy("Alvo", 100, 10);
        target.gainShield(30);
        target.receiveDamage(200);
        assertEquals(0, target.getHealth(), "The target must have 0 health(dead)");
        assertEquals(0, target.getShield(), "The target shield points must be 0(break)");
        assertFalse(target.isAlive(), "isAlive must be false");

    }

    /**
     * Testa o mecanismo de reset que ocorre na transição de turnos da entidade.
     * Garante que o fôlego (stamina) seja totalmente restaurado para o máximo e 
     * que os pontos de escudo remanescentes da rodada anterior sejam removidos.
     */
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