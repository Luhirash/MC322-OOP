import java.util.Scanner;
import java.util.ArrayList;

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
            new ShieldCard("focar", 2, 5, "concentra-se no próximo movimento adverário, reduzindo o dano causado"),
            new ShieldCard("desviar", 3, 8, "se esquiva do ataque do inimigo"),
            new ShieldCard("andar para trás", 1, 2, "dá um passo para trás, fungindo do inimigo"),
            new ShieldCard("agachar", 2, 4, "busca se esconder do inimigo rapidamente")
        };

        PurchasePile drawPile = new PurchasePile(hits);
        drawPile.fillPile(hits.length);
        drawPile.shuffle();

        DiscardPile discardPile = new DiscardPile();
        ArrayList<Card> enemyCards = new ArrayList<>();
        PlayerHand playerHand = new PlayerHand(3); 


        Turns turns = new Turns();

        System.out.println("=== A Luta Começou! ===");
        System.out.println(hero.getName() + " VS " + enemy.getName() + "\n");

        while (hero.isAlive() && enemy.isAlive()){

            if(drawPile.isEmpty())
                drawPile.retrieveCards(discardPile);

            enemyCards = enemy.chooseCards(hits);
            enemy.printIntentions(enemyCards);

            playerHand.drawCards(scanner, drawPile);
            turns.HeroTurn(scanner, hero, enemy, playerHand, discardPile);//passei o turno do heroi para turns
            playerHand.returnCards(discardPile);
            
            if (enemy.isAlive()){
                enemy.newTurn();
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

    public static void pause(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) { 
            System.out.println("Putz");
        }
    }
}
