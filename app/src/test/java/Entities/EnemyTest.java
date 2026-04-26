package Entities;

import Cards.Card;
import Cards.DamageCard;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes que verifica o comportamento específico dos inimigos (Enemy) no combate,
 * com foco na execução de ataques e lógica de escolha de cartas.
 */
public class EnemyTest {
    
    /**
     * Classe utilitária (Dummy) criada para instanciar e testar a classe abstrata Enemy.
     */
    class EnemyDummy extends Enemy {
        public EnemyDummy(String name, int maxHealth, int maxStamina, int value) {
            super(name, maxHealth, maxStamina, value);
        }
        @Override
        public ArrayList<Card> getHits() {
            return new ArrayList<Card>(); }
        @Override
        public void printIntentions(ArrayList<Card> chosenCards) {}
    }
    
    /**
     * Classe utilitária (Dummy) que representa o alvo (Herói) para receber os ataques do inimigo nos testes.
     */
    class HeroDummy extends Hero {
        public HeroDummy() { super("Alvo", 100, 10); }
    }

    /**
     * Testa o fluxo de ataque do inimigo contra um herói utilizando uma carta de dano.
     * Verifica se o fôlego (stamina) do inimigo é reduzido corretamente e se a vida do
     * herói diminui na exata proporção do dano da carta.
     */
    @Test
    void AttackHeroSpendingStaminaAndCausingDamage(){
        Enemy enemy = new EnemyDummy("enemydummy", 50, 10, 10);
        Hero hero = new HeroDummy(); 
        
        DamageCard punch = new DamageCard("punch", 3, 15, "throw a punch");
        enemy.attack(hero, punch);
        assertEquals(7, enemy.getStamina(), "The enemy attack must spend his stamina");
        assertEquals(85, hero.getHealth(), "The enemy attack must decrease the hero life the exact amount of the damage from the attack");
    }

    /**
     * Verifica se o inimigo obedece ao limite de cartas jogadas por turno.
     * Dado fôlego suficiente e cartas de baixo custo, o inimigo deve escolher no máximo
     * 3 cartas, que é o limite imposto pela mecânica do jogo.
     */
    @Test
    void MustAttackThreeTimesMaximumIfHasStaminaEnough(){
        Enemy enemy = new EnemyDummy("enemydummy", 50, 10, 10);
        
        ArrayList<Card> lightHits = new ArrayList<Card>(List.of(
            new DamageCard("light punch", 1, 2, "throws a jab")
    ));

        ArrayList<Card> chosenCards = enemy.chooseCards(lightHits);

        assertEquals(3, chosenCards.size(), "Must buy the maximum amount of three cards");
    }

    /**
     * Verifica se o inimigo interrompe a compra de cartas corretamente quando seu 
     * fôlego acaba, não ultrapassando o limite da sua energia atual, mesmo
     * não tendo atingido o limite máximo de cartas.
     */
    @Test
    void StopChoosingCardsWhenStaminaIsOut(){
        Enemy enemy = new EnemyDummy("enemydummy", 50, 5, 10);

        ArrayList<Card> Hits = new ArrayList<Card>(List.of(
            new DamageCard("Punch", 3, 10, "normal punch")
        ));

        ArrayList<Card> chosenCards = enemy.chooseCards(Hits);
        assertEquals(1, chosenCards.size(), "Must pick only one card(the amount of stamina available");
    }

}