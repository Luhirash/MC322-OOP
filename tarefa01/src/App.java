import java.util.Scanner;


public class App {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 36, 10);
        Enemy enemy = new Enemy( "Jon Jones", 42, 12);
        DamageCard[] hits = {
            new DamageCard("jab", 3, 5),
            new DamageCard("direto", 5, 8),
            new DamageCard("chute na perna", 6, 10),
            new DamageCard("chute na cabeça", 7, 12)
        };

        ShieldCard focus = new ShieldCard("focar", 3, 8);
        Turns turns = new Turns(hero, enemy, hits, focus);
        System.out.println("=== A Luta Começou! ===");

        System.out.println(hero.getName() + " VS " + enemy.getName());

        while (hero.isAlive() && enemy.isAlive()){

            hero.newTurn();
            boolean playerTurn = true;
            DamageCard firstAttackCard = turns.chooseCard();
            DamageCard secondAttackCard = turns.chooseCard();
            while (secondAttackCard == firstAttackCard)
                secondAttackCard = turns.chooseCard();


            while (playerTurn && enemy.isAlive() && !turns.allCardsUsed(firstAttackCard, secondAttackCard, focus)) {
                
                pause(1000);
                turns.printIntroduction();
                pause(1000);
                turns.printHeroTurn(firstAttackCard, secondAttackCard);

                int choice = scanner.nextInt();

                while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                        System.out.println("Escolha inválida! Digite novamente: ");
                        choice = scanner.nextInt();
                }

                if (choice == 1) {
                    firstAttackCard.tryCard(hero, enemy);
                }
                else if (choice == 2) {
                    secondAttackCard.tryCard(hero,enemy);
                }
                else if(choice == 3) {
                    focus.tryCard(hero,enemy);
                }
                else if (choice == 4){
                    playerTurn = false;//isso acaba o laço
                    System.out.println("Turno encerrado");
                    break;
                }

                if (hero.getStamina() <= 0) {
                    pause(2000);
                    System.out.println("\nAcabou sua estamina! Vez do inimigo");
                    playerTurn = false;
                }
            }

            firstAttackCard.setWasUsed(false);
            secondAttackCard.setWasUsed(false);
            focus.setWasUsed(false);

            if (enemy.isAlive()){
                enemy.newTurn();
                turns.printIntroduction();
                turns.enemyTurn();
                playerTurn = true;
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
