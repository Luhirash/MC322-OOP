package Core;
import java.util.Scanner;
import java.util.ArrayList;
import Cards.*;
import Core.GameManager.Events;
import Effects.*;
import Entities.*;
import Events.Battle;
import Piles.*;

/**
 * Gerencia o fluxo de turnos de uma batalha, coordenando ações do herói,
 * do inimigo e o sistema de efeitos de status.
 *
 * <p>Cada instância de {@code Turns} está vinculada a uma única {@link Battle} e
 * é responsável por dois papéis:</p>
 * <ol>
 *   <li><b>Controlador de turnos:</b> executa o turno do herói ({@link #HeroTurn})
 *       e o turno do inimigo ({@link #enemyTurn}), respeitando a ordem correta de
 *       eventos e notificações de efeitos.</li>
 *   <li><b>Coordenador de efeitos:</b> define o {@link GameManager#currentEvent}
 *       correto antes de cada notificação, garantindo que apenas os efeitos
 *       pertinentes atuem no momento adequado.</li>
 * </ol>
 *
 * <h2>Sequência de eventos por rodada</h2>
 * <pre>
 * HEROSTART   → efeitos ativos no início do turno do herói (ex: Recuperação do herói)
 *   [herói joga suas cartas]
 * HEROFINISH  → efeitos ativos no fim do turno do herói (ex: Sangramento no inimigo)
 *
 * ENEMYSTART  → efeitos ativos no início do turno do inimigo (ex: Recuperação do inimigo)
 *   [inimigo executa suas cartas]
 * ENEMYFINISH → efeitos ativos no fim do turno do inimigo (ex: Sangramento no herói)
 * </pre>
 *
 * @see GameManager
 * @see Effect
 * @see Battle
 */
public class Turns {

    private Hero hero;
    private Enemy enemy;
    private GameManager gameManager;

    /**
     * Constrói o controlador de turnos para uma batalha específica.
     *
     * @param hero        herói controlado pelo jogador
     * @param enemy       inimigo a ser enfrentado
     * @param gameManager gerenciador de eventos e efeitos compartilhado com a batalha
     */
    public Turns(Hero hero, Enemy enemy, GameManager gameManager) {
        this.hero = hero;
        this.enemy = enemy;
        this.gameManager = gameManager;
    }

    /**
     * Executa a sequência de ações do inimigo no seu turno.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Define {@link GameManager#currentEvent} para {@link Events#ENEMYSTART}
     *       e notifica os efeitos inscritos.</li>
     *   <li>O inimigo executa cada carta da lista {@code chosenCards} contra o herói,
     *       inscrevendo no {@link GameManager} qualquer efeito gerado.</li>
     *   <li>Se o herói for derrotado durante a sequência, o loop é interrompido.</li>
     *   <li>Ao final, define o evento para {@link Events#ENEMYFINISH} e notifica
     *       os efeitos (ex: sangramento aplicado ao herói).</li>
     * </ol>
     *
     * @param chosenCards cartas que o inimigo anunciou no início da rodada,
     *                    obtidas via {@link Enemy#chooseCards(Cards.Card[])}
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
     *   <li>Restaura o fôlego e o escudo do herói via {@link Entities.Entity#newTurn()}.</li>
     *   <li>Define {@link GameManager#currentEvent} para {@link Events#HEROSTART}
     *       e notifica os efeitos (ex: Recuperação do herói).</li>
     *   <li>Exibe o fôlego disponível e as cartas na mão via
     *       {@link PlayerHand#printHand()}.</li>
     *   <li>Aguarda a escolha do jogador: número da carta (1..N) ou encerrar o turno (N+1).</li>
     *   <li>Se a carta escolhida puder ser usada ({@link Cards.Card#tryCard}), ela é jogada,
     *       seu efeito (se houver) é inscrito no {@link GameManager}, a carta é removida
     *       da mão e enviada ao {@link DiscardPile}.</li>
     *   <li>O loop continua enquanto: o evento for {@link Events#HEROSTART}, ambos os
     *       combatentes estiverem vivos, a mão não estiver vazia e houver fôlego.</li>
     *   <li>Ao final, define o evento para {@link Events#HEROFINISH} e notifica
     *       os efeitos (ex: sangramento no inimigo).</li>
     * </ol>
     *
     * @param scanner     entrada do usuário via terminal
     * @param playerHand  mão atual do jogador com as cartas disponíveis
     * @param discardPile pilha de descarte que recebe as cartas utilizadas
     */
    public void HeroTurn(Scanner scanner, PlayerHand playerHand, DiscardPile discardPile) {
        hero.newTurn();
        hero.triggerTurnStartRelics();//executa reliquias de acao em turno 
        gameManager.currentEvent = Events.HEROSTART;
        gameManager.notifyEvent();

        while (gameManager.currentEvent == Events.HEROSTART && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){

            App.pause(1000);

            System.out.println("\nFôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            System.out.println("Suas cartas disponíveis:");
            playerHand.printHand();
            
            int numCards = playerHand.getHandSize();
            int exitChoice = numCards + 1;
            System.out.println(exitChoice + " - Encerrar turno");
            System.out.print("\n Escolha sua ação: ");

            int choice = -1;
            boolean entradaValida = false;
            while (!entradaValida) {
                System.out.print("Escolha sua ação: ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt(); // Lê apenas UMA vez
                    scanner.nextLine(); // Limpa o buffer
                    
                    if (choice > 0 && choice <= exitChoice) {
                        entradaValida = true; // Sucesso! Sai do loop
                    } else {
                        System.out.println("Erro: Digite um número entre 1 e " + exitChoice);
                    }
                } else {
                    System.out.println("Erro: Isso não é um número inteiro!");
                    scanner.next(); // Descarta a entrada de texto inválida
                }
            }
            
            if(choice >= 1 && choice <= numCards){
                Card chosenCard = playerHand.getCard(choice - 1);
                if(chosenCard.tryCard(hero)){//se for possivel usar a carta
                    Effect effect = chosenCard.useCard(hero, enemy);
                    if(effect != null)
                        gameManager.subscribe(effect);
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