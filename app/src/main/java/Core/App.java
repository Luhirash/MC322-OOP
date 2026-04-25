package Core;
import java.util.Scanner;

import Entities.*;
import Events.Battle;
import Piles.*;

/**
 * Classe principal que inicializa e executa o jogo de combate baseado em cartas.
 *
 * <p>O fluxo do jogo é o seguinte:</p>
 * <ol>
 *   <li>Uma árvore de inimigos é criada via {@link EnemyNode#createTree()}, e o combate
 *       começa pelo nó raiz.</li>
 *   <li>O baralho do herói é montado, embaralhado e as cartas são distribuídas na mão.</li>
 *   <li>Cada luta é gerenciada por {@link Battle}, que alterna turnos entre herói e inimigo
 *       até que um dos dois seja derrotado.</li>
 *   <li>Ao vencer, o jogador escolhe o próximo inimigo navegando pela árvore.</li>
 *   <li>O jogo termina quando o herói é derrotado ou todos os inimigos são vencidos.</li>
 * </ol>
 *
 * @see Hero
 * @see Enemy
 * @see Battle
 * @see EnemyNode
 * @see PurchasePile
 * @see DiscardPile
 * @see PlayerHand
 */
public class App {

    /**
     * Ponto de entrada do programa.
     *
     * <p>Responsável por:</p>
     * <ul>
     *   <li>Instanciar o herói.</li>
     *   <li>Criar o {@link GameManager} compartilhado entre as batalhas.</li>
     *   <li>Criar o baralho de compra ({@link PurchasePile}), preenchê-lo e embaralhá-lo.</li>
     *   <li>Criar a pilha de descarte ({@link DiscardPile}) e a mão do jogador ({@link PlayerHand}).</li>
     *   <li>Construir a árvore de inimigos e iniciar pelo nó raiz.</li>
     *   <li>Executar o loop principal, instanciando uma {@link Battle} para cada luta.</li>
     *   <li>Após cada vitória, permitir ao jogador escolher o próximo inimigo.</li>
     *   <li>Exibir o resultado final ao término do jogo.</li>
     * </ul>
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);


        GameManager gameManager = new GameManager();

        PurchasePile drawPile = new PurchasePile(hero.getHits());
        drawPile.fillPile(hero.getHits().length);
        drawPile.shuffle();


        DiscardPile discardPile = new DiscardPile();
        PlayerHand playerHand = new PlayerHand(3); 

        EnemyNode rootEnemyNode = new EnemyNode();
        EnemyNode currentNode = rootEnemyNode.createTree();
        boolean gameOn = true;

        System.out.println("Seu herói, Anderson Silva, busca o cinturão do UFC, então começará sua jornada lutando contra: " + currentNode.getEnemy().getName());
        while (gameOn) {
            Enemy enemy = currentNode.getEnemy();
            Battle battle = new Battle(enemy, gameManager, scanner, drawPile, discardPile, playerHand);

            battle.printStart(hero);
            battle.executeBattle(hero);
            battle.battleResults(hero);
            
            if (!hero.isAlive()) {
                gameOn = false;
            }
            else if (currentNode.isLeaf()) {
                gameOn = false;
                System.out.println("Anderson Silva derrotou todos os inimigos em seu caminho");
            }
            else {
                currentNode = currentNode.chooseNextEnemy(scanner);
            }

        }

        scanner.close();
    }

    /**
     * Pausa a execução do programa pelo tempo especificado.
     *
     * <p>Utilizado para criar pausas dramáticas entre as ações de combate,
     * tornando a leitura do terminal mais agradável ao jogador.</p>
     *
     * @param miliseconds duração da pausa em milissegundos
     */
    public static void pause(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) { 
            System.out.println("Putz");
        }
    }
}