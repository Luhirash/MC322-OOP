package Core;
import java.util.Scanner;
import java.util.ArrayList;
import Cards.*;
import Core.GameManager.Events;
import Effects.*;
import Entities.*;
import Piles.*;

/**
 * Gerencia o fluxo de combate e coordena o sistema de efeitos de status.
 *
 * <p>Esta classe cumpre dois papéis principais:</p>
 * <ol>
 *   <li><b>Controlador de turnos:</b> orquestra a sequência de ações em cada rodada,
 *       executando o turno do herói ({@link #HeroTurn}) e o turno do inimigo ({@link #enemyTurn}),
 *       além de auxiliar na escolha do inimigo ({@link #chooseEnemy}).</li>
 *   <li><b>Publisher (padrão Observer):</b> mantém uma lista de {@link Effect efeitos de status}
 *       inscritos e os notifica a cada mudança de evento de turno ({@link #notifyEvent}).
 *       Efeitos se inscrevem via {@link #subscribe(Effect)} e se removem via
 *       {@link #unsubscribe(Effect)} quando expiram.</li>
 * </ol>
 *
 * <h2>Sequência de eventos por rodada</h2>
 * <pre>
 * HEROSTART  → efeitos do herói ativados no início (ex: Recuperação)
 *   [herói joga suas cartas]
 * HEROFINISH → efeitos do inimigo ativados no fim do turno do herói (ex: Sangramento no inimigo)
 *
 * ENEMYSTART → efeitos do inimigo ativados no início (ex: Recuperação no inimigo)
 *   [inimigo executa suas cartas]
 * ENEMYFINISH → efeitos do herói ativados no fim do turno do inimigo (ex: Sangramento no herói)
 * </pre>
 *
 * @see Effect
 * @see Bleeding
 * @see Healing
 * @see Strength
 */
public class Turns {

    private Hero hero;
    private Enemy enemy;
    private GameManager gameManager;

    public Turns(Hero hero, Enemy enemy, GameManager gameManager) {
        this.hero = hero;
        this.enemy = enemy;
        this.gameManager = gameManager;
    }

    /**
     * Executa a sequência de ações planejadas pelo inimigo no seu turno.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Dispara {@link Events#ENEMYSTART} e notifica efeitos.</li>
     *   <li>O inimigo executa cada carta da lista {@code chosenCards} contra o herói.</li>
     *   <li>Se o herói morrer durante o ataque, a sequência é interrompida.</li>
     *   <li>Ao final, dispara {@link Events#ENEMYFINISH} e notifica efeitos.</li>
     *   <li>Exibe o placar atualizado ({@link #printIntroduction}).</li>
     * </ol>
     *
     * @param chosenCards cartas escolhidas pelo inimigo no início da rodada
     *                    (via {@link Enemy#chooseCards(Card[])})
     * @param hero        herói que será o alvo dos ataques
     * @param enemy       inimigo que está executando o turno
     */
    public void enemyTurn(ArrayList<Card> chosenCards){
    
        gameManager.currentEvent = GameManager.Events.ENEMYSTART;
        gameManager.notifyEvent();

        for (int i = 0; i < chosenCards.size(); i++) {
            if (hero.isAlive()) {
                Effect effect = chosenCards.get(i).useCard(enemy, hero);
                if (effect != null)
                    gameManager.subscribe(effect);
                System.out.println();
            }
            else{
                System.out.println(hero.getName() + " foi derrotado!");
                break;
            }
        }      
        if (hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " encerrou seu turno\n");

            gameManager.currentEvent = Events.ENEMYFINISH;
            gameManager.notifyEvent();
        }

    }

    /**
     * Gerencia a interação com o jogador durante o turno do herói.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Restaura o fôlego e o escudo do herói ({@link Entity#newTurn()}).</li>
     *   <li>Dispara {@link Events#HEROSTART} e notifica efeitos (ex: Recuperação).</li>
     *   <li>Exibe as cartas disponíveis na mão ({@link PlayerHand#printHand()}).</li>
     *   <li>Aguarda a escolha do jogador: número da carta ou opção de encerrar o turno.</li>
     *   <li>Tenta jogar a carta escolhida ({@link Card#tryCard}). Se bem-sucedida, a carta
     *       é removida da mão e enviada ao descarte ({@link DiscardPile}).</li>
     *   <li>O loop continua até que o jogador encerre o turno, o fôlego acabe,
     *       a mão fique vazia, ou um dos combatentes seja derrotado.</li>
     *   <li>Ao final, dispara {@link Events#HEROFINISH} e notifica efeitos (ex: Sangramento no inimigo).</li>
     * </ol>
     *
     * @param scanner     entrada do usuário via terminal
     * @param hero        herói controlado pelo jogador
     * @param enemy       inimigo-alvo das ações do herói
     * @param playerHand  mão atual do jogador com as cartas disponíveis
     * @param discardPile pilha de descarte que recebe as cartas jogadas
     */
    public void HeroTurn(Scanner scanner, PlayerHand playerHand, DiscardPile discardPile) {
        hero.newTurn();
        gameManager.currentEvent = Events.HEROSTART;
        gameManager.notifyEvent();

        while (gameManager.currentEvent == Events.HEROSTART && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){

            App.pause(1000);
            //hero.printStats();

            System.out.println("\nFôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            System.out.println("Suas cartas disponíveis:");
            playerHand.printHand();
            
            int numCards = playerHand.getHandSize();
            int exitChoice = numCards + 1;
            System.out.println(exitChoice + " - Encerrar turno");
            System.out.print("\n Escolha sua ação: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida! Por favor, digite o número da carta.");
                scanner.next(); // Limpa a entrada incorreta do buffer
                System.out.print("Escolha sua ação: ");
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            if(choice >= 1 && choice <= numCards){
                Card chosenCard = playerHand.getCard(choice - 1);
                if(chosenCard.tryCard(hero)){//se for possivel usar a carta
                    Effect effect = chosenCard.useCard(hero, enemy);
                    if(effect != null)
                        gameManager.subscribe(effect);
                    //printIntroduction(hero, enemy);
                    playerHand.removeCard(choice - 1);
                    discardPile.addCard(chosenCard);//depois do uso a carta vai para descarte
                }
            } 
            else if(choice == exitChoice){
                System.out.println("Turno encerrado pelo jogador.");
                break;
            }
            else{
                System.out.println("Escolha inválida!");
            }

            if(hero.getStamina() <= 0){
                    App.pause(2000);
                    System.out.println("\nAcabou seu fôlego! Vez do inimigo");
                    break;
                }
            }
        if (enemy.isAlive()) {
            gameManager.currentEvent = Events.HEROFINISH;
            gameManager.notifyEvent();
        }
    }
}