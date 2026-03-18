import java.util.Scanner;


public class App {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);
        Enemy enemy = new Enemy( "Jon Jones", 42, 11);

        Card[] hits = {
            new DamageCard("jab", 3, 5, "desfere um soco com mão esquerda na cabeça do inimigo"),
            new DamageCard("direto", 5, 8, "desfere um soco com a mão direita na cabeça do inimigo"),
            new DamageCard("chute na perna", 6, 10, "desfere um chute com a perna direita na perna do inimigo"),
            new DamageCard("chute na cabeça", 7, 12, "desfere um chute com a perna direita na cabeça do inimigo"),
            new DamageCard("soco cruzado", 5, 9, "desfere um soco lateral na cabeça do inimigo"),
            new DamageCard("uppercut", 6, 11, "desfere um soco ascendente na cabeça do inimigo"),
            new ShieldCard("focar", 3, 8, "concentra-se no próximo movimento adverário, reduzindo o dano causado")
        };

        PurchaseStack drawPile = new PurchaseStack(hits);
        drawPile.fillStack(7);
        drawPile.shuffle();

        StackOfCards discardPile = new StackOfCards();

        PlayerHand playerHand = new PlayerHand(2); 


        Turns turns = new Turns(hero, enemy, drawPile, discardPile, playerHand, hits);



        System.out.println("=== A Luta Começou! ===");

        System.out.println(hero.getName() + " VS " + enemy.getName());

        while (hero.isAlive() && enemy.isAlive()){

            turns.HeroTurn(scanner);//passei o turno do heroi para turns

            if (enemy.isAlive()){
                enemy.newTurn();
                turns.printIntroduction();
                turns.enemyTurn();
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

    public static void pause(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) { 
            System.out.println("Putz");
        }
    }
}
