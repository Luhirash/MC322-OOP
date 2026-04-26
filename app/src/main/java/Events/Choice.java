package Events;
import java.util.Scanner;

import Possibilities.*;
import Entities.Hero;
import Cards.Card;

public class Choice extends Event{
    
    private String description;
    private CardPossibility cardPossibility;
    private HealthPossibility healthPossibility;
    private CoinPossibility coinPossibility;
    private Scanner scanner;
    private String name;

    public Choice(String description, CardPossibility cardPossibility, HealthPossibility healthPossibility, CoinPossibility coinPossibility, Scanner scanner) {
        this.description = description;
        this.healthPossibility = healthPossibility;
        this.cardPossibility = cardPossibility;
        this.coinPossibility = coinPossibility;
        this.scanner = scanner;
        this.name = "Escolha";
    }

    public void startEvent(Hero hero) {
        introduceChoice();
        int choice = getChoice();
        usePossibility(hero, choice);
    }

    private void introduceChoice() {
        System.out.println(description);
        System.out.println("    Qual você escolherá?");
        System.out.println("1 - " + cardPossibility.getDescription() + ". Assim, adicionar " + cardPossibility.getCard().getName() + " ao seu baralho");
        if (healthPossibility.getHealthChange() > 0) System.out.println("2 - " + healthPossibility.getDescription() + " Assim, recuperar " + healthPossibility.getHealthChange() + " de vida.");
        else System.out.println("2 - " + healthPossibility.getDescription() + ". Assim, perder " + -healthPossibility.getHealthChange() + " de vida.");
        if (coinPossibility.getCoins() > 0) System.out.println("3 - " + coinPossibility.getDescription() + ". Assim, ganhar " + coinPossibility.getCoins() + " moedas");
        else System.out.println("3 - " + coinPossibility.getDescription() + ". Assim, perder " + -coinPossibility.getCoins() + " moedas");
    }

    private int getChoice() {
        int choice = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Escolha sua ação: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt(); // Lê apenas UMA vez
                scanner.nextLine(); // Limpa o buffer
                
                if (choice > 0 && choice <= 3) {
                    entradaValida = true; // Sucesso! Sai do loop
                } else {
                    System.out.println("Erro: Digite um número entre 1 e 3");
                }
            } else {
                System.out.println("Erro: Isso não é um número inteiro!");
                scanner.next(); // Descarta a entrada de texto inválida
            }
        }
        return choice;
    }

    private void usePossibility(Hero hero, int choice) {
        if (choice == 1) {
            Card newCard = cardPossibility.getCard(); 
            System.out.println(hero.getName() + " agora pode usar " + newCard.getName() + " durante suas lutas.");
            hero.addCard(newCard);
        }
        else if (choice == 2) {
            int trueHealing = hero.gainHealth(healthPossibility.getHealthChange());
            if (trueHealing < 0) System.out.println("Você perdeu " + trueHealing + " de vida.");
            else System.out.println("Você ganhou " + trueHealing + " de vida.");
        }
        else if (choice == 3) {
            int coins = coinPossibility.getCoins();
            hero.addCoins(coins);
            if (coins > 0) System.out.println("Você ganhou " + coins + " moedas.");
            else System.out.println("Você perdeu " + coins + " moedas.");
        }
        
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }
}
