package Core;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.*;
import Piles.*;
import Cards.Card;

public class Battle {
    
    private Hero hero;
    private Enemy enemy;
    private GameManager gameManager;
    private Scanner scanner;

    public Battle(Hero hero, Enemy enemy, GameManager gameManager, Scanner scanner) {
        this.hero = hero;
        this.enemy = enemy;
        this.gameManager = gameManager;
        this.scanner = scanner;
    }

    /**
     * Exibe no terminal o placar atual da luta com os status de ambos os combatentes.
     *
     * <p>Formato exibido:</p>
     * <pre>
     * ----------------------------------
     * Hero (Vida: X/MaxVida) (Bloqueio: Y)
     * VS
     * Enemy (Vida: X/MaxVida) (Bloqueio: Y) [Efeitos: ...]
     * ----------------------------------
     * </pre>
     *
     * @param hero  herói cujos status serão exibidos
     * @param enemy inimigo cujos status serão exibidos
     */
    public void printIntroduction() {
        App.pause(300);
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
        App.pause(300);
    }
 
    private Turns createBattleTurns() {
        return new Turns(hero, enemy, gameManager);
    }

    public void executeBattle(PurchasePile drawPile, DiscardPile discardPile, PlayerHand playerHand) {
        Turns battleTurns = createBattleTurns();
        ArrayList<Card> enemyCards = new ArrayList<>();

        while (hero.isAlive() && enemy.isAlive()){

            if(drawPile.isEmpty())
                drawPile.retrieveCards(discardPile);

            enemyCards = enemy.chooseCards(enemy.getHits());
            enemy.printIntentions(enemyCards);

            playerHand.drawCards(drawPile);
            battleTurns.HeroTurn(scanner, playerHand, discardPile);
            playerHand.returnCards(discardPile);
            
            if (enemy.isAlive()){
                enemy.newTurn();
                // pause(2000);
                printIntroduction();
                battleTurns.enemyTurn(enemyCards);
                printIntroduction();
            }
        }
        hero.clearEffects();
        enemy.clearEffects();
        gameManager.unsubscribeAll();
    }

    public void printResults() {

        System.out.println("\n--Fim da luta--");
        if(hero.isAlive()){
            System.out.println("Anderson silva ganhou a luta!");
        }
        else{
            System.out.println("Anderson silva foi derrotado!");
        }

    }

    public void printStart() {
        System.out.println("\n=== A Luta Começou! ===");
        printIntroduction();
    }
}
