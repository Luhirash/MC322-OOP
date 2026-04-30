package Core;
import java.util.Scanner;

import Entities.*;
import Events.*;
import Piles.*;


/**
 * Classe principal que inicializa e executa o jogo de combate baseado em cartas.
 *
 * <p>O fluxo do jogo é o seguinte:</p>
 * <ol>
 *   <li>Uma árvore de eventos é criada via {@link EventTree#createTree()}, e o combate
 *       começa pelo nó raiz.</li>
 *   <li>Cada luta é gerenciada por {@link Battle}, que alterna turnos entre herói e inimigo
 *       até que um dos dois seja derrotado.</li>
 *   <li>Ao acabar um evento, o jogador escolhe o próximo evento navegando pela árvore.</li>
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
     *   <li>Construir a árvore de eventos e iniciar pelo nó raiz.</li>
     *   <li>Executar o loop principal, iniciando um {@link Event} para cada ação do joog.</li>
     *   <li>Após cada evento, permitir ao jogador escolher o próximo evento.</li>
     *   <li>Exibir o resultado final ao término do jogo.</li>
     * </ul>
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);

        EventTree events = new EventTree(scanner);
        EventNode currentEventNode = events.createTree();

        boolean gameOn = true;

        System.out.println("Seu herói, Anderson Silva, busca o cinturão do UFC, então começará sua jornada lutando contra: " + currentEventNode.getEvent().getDescription());
        while (gameOn) {
            Event event = currentEventNode.getEvent();
            event.startEvent(hero);

            if (!hero.isAlive()) {
                gameOn = false;
            }
            else if (currentEventNode.isLeaf()) {
                gameOn = false;
                System.out.println("Anderson Silva derrotou todos os inimigos em seu caminho");
            }
            else {
                currentEventNode = currentEventNode.chooseNextEvent(scanner);
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