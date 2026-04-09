import java.util.Scanner;
import java.util.ArrayList;

/**
 * Classe principal do jogo de luta baseado em cartas.
 * <p>
 * Inicializa os personagens, o baralho e controla o loop principal da partida,
 * alternando turnos entre o herói e o inimigo escolhido até que um dos dois seja derrotado.
 * </p>
 */
public class App {

    /**
     * Ponto de entrada do programa.
     * <p>
     * Cria o herói, os inimigos, as pilhas de cartas e executa o loop de combate,
     * alternando entre o turno do herói e o turno do inimigo.
     * </p>
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);

        Enemy[] enemies = {
            new JonJones( "Jon Jones", 42, 11),
            new ConnnorMcGregor("Connor McGregor", 30, 14)
        };

        //Turns e criado antes para ser passado para cartas de efeito
        Turns turns = new Turns();

        PurchasePile drawPile = new PurchasePile(hero.getHits());
        drawPile.fillPile(hero.getHits().length);
        drawPile.shuffle();

        DiscardPile discardPile = new DiscardPile();
        ArrayList<Card> enemyCards = new ArrayList<>();
        PlayerHand playerHand = new PlayerHand(3); 

        Enemy enemy = turns.chooseEnemy(enemies, scanner);

        System.out.println("=== A Luta Começou! ===");
        System.out.println(hero.getName() + " VS " + enemy.getName() + "\n");

        while (hero.isAlive() && enemy.isAlive()){

            if(drawPile.isEmpty())
                drawPile.retrieveCards(discardPile);

            enemyCards = enemy.chooseCards(enemy.getHits());
            enemy.printIntentions(enemyCards);

            playerHand.drawCards(drawPile);
            turns.HeroTurn(scanner, hero, enemy, playerHand, discardPile);
            playerHand.returnCards(discardPile);
            
            if (enemy.isAlive()){
                enemy.newTurn();
                pause(2000);
                turns.printIntroduction(hero, enemy);
                turns.enemyTurn(enemyCards, hero, enemy);
            }
        }

        System.out.println("\n--Fim da luta--");
        if(hero.isAlive()){
            System.out.println("Anderson silva ganhou a luta!");
        }
        else{
            System.out.println("Anderson silva foi derrotado!");
        }

        scanner.close();
    }

    /**
     * Pausa a execução do programa pelo tempo especificado.
     *
     * @param miliseconds tempo de pausa em milissegundos
     */
    public static void pause(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) { 
            System.out.println("Putz");
        }
    }
}