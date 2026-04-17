package Core;
import java.util.Scanner;

import Entities.*;
import Piles.*;

/**
 * Classe principal que inicializa e executa o jogo de combate baseado em cartas.
 *
 * <p>O fluxo do jogo é o seguinte:</p>
 * <ol>
 *   <li>O jogador escolhe um inimigo entre os disponíveis.</li>
 *   <li>O baralho do herói é montado aleatoriamente, embaralhado e distribuído na mão.</li>
 *   <li>A cada rodada, o inimigo anuncia suas intenções e o herói joga suas cartas.</li>
 *   <li>Após o turno do herói, o inimigo executa as cartas anunciadas.</li>
 *   <li>O combate continua até que o herói ou o inimigo seja derrotado (vida chega a zero).</li>
 * </ol>
 *
 * @see Hero
 * @see Enemy
 * @see Turns
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
     *   <li>Instanciar o herói e os inimigos disponíveis.</li>
     *   <li>Criar o baralho de compra ({@link PurchasePile}), preenchê-lo e embaralhá-lo.</li>
     *   <li>Criar a pilha de descarte ({@link DiscardPile}) e a mão do jogador ({@link PlayerHand}).</li>
     *   <li>Permitir que o jogador escolha o inimigo.</li>
     *   <li>Executar o loop principal de combate, alternando turnos do herói e do inimigo.</li>
     *   <li>Exibir o resultado final da luta.</li>
     * </ul>
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);

        Enemy[] enemies = {
            new JonJones( "Jon Jones", 42, 11),
            new ConnnorMcGregor("Connor McGregor", 30, 14),
            new KennethAllen("Kenneth Allen", 22, 9)
        };

        GameManager gameManager = new GameManager();

        PurchasePile drawPile = new PurchasePile(hero.getHits());
        drawPile.fillPile(hero.getHits().length);
        drawPile.shuffle();

        DiscardPile discardPile = new DiscardPile();

        PlayerHand playerHand = new PlayerHand(3); 

        System.out.println("Escolha seu inimigo:");
        for (int i = 0; i < enemies.length; i++) {
            System.out.print(i + 1 + " - " );
            System.out.println(enemies[i].getName() + " (Vida: " + enemies[i].getMaxHealth() + ") (Fôlego: " + enemies[i].getMaxStamina() + ")");
        } 
        int choice = scanner.nextInt();
        while (choice < 1 || choice > enemies.length) {
            System.out.println("Escolha inválida! Tente novamente:");
            choice = scanner.nextInt();
        }
        Enemy enemy = enemies[choice -1];

        
        Battle battle = new Battle(hero, enemy, gameManager, scanner);

        battle.printStart();
        battle.executeBattle(drawPile, discardPile, playerHand);
        battle.printResults();

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