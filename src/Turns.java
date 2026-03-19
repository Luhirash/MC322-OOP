//import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Turns {
    
    public void enemyTurn(ArrayList<Card> chosenCards, Hero hero, Enemy enemy){
        for (int i = 0; i < chosenCards.size(); i++) {
            if (hero.isAlive()) {
                chosenCards.get(i).useCard(enemy, hero);
                System.out.println();
            }
            else{
                System.out.println(hero.getName() + "foi derrotado!");
                break;
            }
        }      
        if (hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " encerrou seu turno\n");
            printIntroduction(hero, enemy);
        }

    }


    public void HeroTurn(Scanner scanner, Hero hero, Enemy enemy, PlayerHand playerHand, DiscardPile discardPile) {
        hero.newTurn();
        boolean playerTurn = true;

        while (playerTurn && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){

            App.pause(1000);
            printIntroduction(hero, enemy);
            hero.printStats();

            System.out.println("Fôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            playerHand.printHand();
            
            int numCards = playerHand.getHandSize();
            int exitChoice = numCards + 1;
            System.out.println(exitChoice + " - Encerrar turno");
            System.out.print("\n Escolha sua ação: ");
            int choice = scanner.nextInt();
            if(choice >= 1 && choice <= numCards){
                Card chosenCard = playerHand.getCard(choice - 1);
                if(chosenCard.tryCard(hero, enemy)){//se for possivel usar a carta
                    playerHand.useCard(choice - 1);
                    discardPile.addCard(chosenCard);//depois do uso a carta vai para descarte
                }
            } 
            else if(choice == exitChoice){
                playerTurn = false;
                System.out.println("Turno encerrado pelo jogador.");
            }
            else{
                System.out.println("Escolha inválida!");
            }

            if(hero.getStamina() <= 0){
                    App.pause(2000);
                    System.out.println("\nAcabou seu fôlego! Vez do inimigo");
                    playerTurn = false;
                }
            }
    }

    public void printIntroduction(Hero hero, Enemy enemy) {
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
    }
}


