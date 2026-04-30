package Events;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.*;
import Piles.*;
import Cards.Card;
import Core.App;
import Core.GameManager;
import Core.Turns;

/**
 * Encapsula uma única luta entre o herói e um inimigo.
 *
 * <p>Cada instância de {@code Battle} representa um combate completo e é descartada
 * ao seu término. A classe é responsável por:</p>
 * <ul>
 *   <li>Orquestrar o loop de rodadas via {@link #executeBattle}.</li>
 *   <li>Coordenar os turnos do herói e do inimigo através de {@link Turns}.</li>
 *   <li>Exibir o estado inicial ({@link #printStart}), o placar por rodada
 *       ({@link #printIntroduction}) e o resultado final ({@link #battleResults}).</li>
 *   <li>Limpar efeitos e desinscrevê-los do {@link GameManager} ao fim da luta.</li>
 * </ul>
 *
 * @see Turns
 * @see GameManager
 * @see Hero
 * @see Enemy
 */
public class Battle extends Event{
    
    private Enemy enemy;
    private Scanner scanner;
    private String name;
    private String description;

    /**
     * Constrói uma nova batalha com os combatentes e dependências fornecidos.
     *
     * @param enemy       o inimigo a ser enfrentado nesta luta
     * @param scanner     entrada do usuário via terminal
     */
    public Battle(Enemy enemy, Scanner scanner) {
        this.enemy = enemy;
        this.scanner = scanner;
        this.name = "Batalha";
        this.description = "Inimigo: " + enemy.getName();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Inicia o evento batalha, usando seu método principal: execute battle.
     */
    public void startEvent(Hero hero) {
        printStart(hero);
        executeBattle(hero);
        battleResults(hero);
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
     */
    public void printIntroduction(Hero hero) {
        App.pause(300);
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
        App.pause(300);
    }

    /**
     * Executa o loop completo de combate até que um dos combatentes seja derrotado.
     *
     * <p>Sequência de cada rodada:</p>
     * <ol>
     *   <li>O inimigo escolhe suas cartas e anuncia suas intenções.</li>
     *   <li>O jogador compra cartas e realiza seu turno via {@link Turns#HeroTurn}.</li>
     *   <li>As cartas restantes na mão são devolvidas ao descarte.</li>
     *   <li>Se o inimigo ainda estiver vivo, seu turno é executado via {@link Turns#enemyTurn}.</li>
     * </ol>
     *
     * <p>Ao encerrar a batalha (independentemente do resultado), os efeitos de ambos os
     * combatentes são limpos e todos os inscritos no {@link GameManager} são removidos,
     * evitando efeitos residuais em lutas futuras.</p>
     *
     * @param hero herói que vai atuar na batalha
     */
    public void executeBattle(Hero hero) {
        GameManager gameManager = new GameManager();
        PurchasePile drawPile = new PurchasePile(hero.getHits());
        drawPile.fillPile(hero.getHits().size());
        drawPile.shuffle();
        DiscardPile discardPile = new DiscardPile();
        PlayerHand playerHand = new PlayerHand(3);
        Turns battleTurns = new Turns(hero, enemy, gameManager);

        hero.triggerBattleStartRelics();//ativa as reliquias de inicio de batalha 

        ArrayList<Card> enemyCards = new ArrayList<>();

        while (hero.isAlive() && enemy.isAlive()){

            enemyCards = enemy.chooseCards(enemy.getHits());
            enemy.printIntentions(enemyCards);

            playerHand.drawCards(drawPile, discardPile);
            battleTurns.HeroTurn(scanner, playerHand, discardPile);
            playerHand.returnCards(discardPile);
            
            if (enemy.isAlive()){
                enemy.newTurn();
                // pause(2000);
                printIntroduction(hero);
                battleTurns.enemyTurn(enemyCards);
                printIntroduction(hero);
            }
        }
        hero.clearEffects();
        hero.setShield(0);
        enemy.clearEffects();
        gameManager.unsubscribeAll();
    }

    /**
     * Exibe o resultado final da luta, indicando vitória ou derrota do herói.
     */
    private boolean battleResults(Hero hero) {

        System.out.println("\n--Fim da luta--");
        if(hero.isAlive()){
            int enemyCoins = enemy.getEnemyValue();
            System.out.println("Anderson silva ganhou a luta!\n");
            System.out.println("Assim, receberá " + enemyCoins + " moedas para gastar pelo caminho.");
            hero.addCoins(enemyCoins);
            return true;
        }
        else{
            System.out.println("Anderson silva foi derrotado!\n");
            return false;
        }

    }

    /**
     * Exibe a mensagem de início da luta seguida do placar inicial.
     *
     * <p>Deve ser chamado uma única vez, imediatamente antes de {@link #executeBattle}.</p>
     */
    public void printStart(Hero hero) {
        System.out.println("\n=== A Luta Começou! ===");
        printIntroduction(hero);
    }

    public String getEnemyName() {
        return this.enemy.getName();
    }
}