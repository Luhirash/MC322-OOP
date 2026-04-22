package Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes focada na criação e nas propriedades iniciais da entidade do jogador (Hero).
 */
public class HeroTest {

    /**
     * Assegura que o herói seja instanciado com os atributos corretos baseados 
     * nos parâmetros informados no construtor (vida máxima e fôlego inicial).
     */
    @Test
    void HeroHasCorrectAtributes(){
        Hero hero = new Hero("Anderson Silva", 36, 10);
        assertEquals(36, hero.getHealth(), "A vida inicial deve ser igual à máxima passada no construtor");
        assertEquals(10, hero.getStamina(), "A stamina deve ser inicializada corretamente");
    }
    
}