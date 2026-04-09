//import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Turns {

    public enum Events {
        HEROSTART, HEROFINISH, ENEMYSTART, ENEMYFINISH
    }
    public Events currentEvent = Events.HEROSTART;

    private ArrayList<Effect> subscriberList = new ArrayList<Effect>();

    public void subscribe(Effect effect) {
        if (effect.getIndex(effect.getOwner().getEffects()) == -1)
            subscriberList.add(effect); //Se o efeito ainda não existe (não está aplicado no dono), ele é adicionado
        effect.getOwner().applyEffect(effect); //De qualquer maneira, é aplicado no dono(se ele ja tiver ativo adiciona acumulos)
    }

    public void unsubscribe(Effect effect) {
        int idx = effect.getIndex(subscriberList);
        if (idx != -1)
            subscriberList.remove(idx); //remove o efeito da lista (se tiver o mesmo nome e dono)
    }
    
    public void notifyEvent() {
        // Itera sobre cópia para evitar ConcurrentModificationException(o array lista muda de tamanho)
        ArrayList<Effect> copy = new ArrayList<Effect>(subscriberList);
        for (Effect effect : copy)
            effect.beNotified(this);
    }

    public void enemyTurn(ArrayList<Card> chosenCards, Hero hero, Enemy enemy){
        currentEvent = Events.ENEMYSTART;
        notifyEvent();

        // Inimigo aplica seus efeitos no início do turno
        enemy.applyEffects(this, hero);

        for (int i = 0; i < chosenCards.size(); i++) {
            if (hero.isAlive()) {
                chosenCards.get(i).useCard(enemy, hero);
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

            currentEvent = Events.ENEMYFINISH;
            notifyEvent();
            
            printIntroduction(hero, enemy);
        }

    }


    public void HeroTurn(Scanner scanner, Hero hero, Enemy enemy, PlayerHand playerHand, DiscardPile discardPile) {
        hero.newTurn();
        currentEvent = Events.HEROSTART;
        notifyEvent();

        while (currentEvent == Events.HEROSTART && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){

            App.pause(1000);
            printIntroduction(hero, enemy);
            hero.printStats();

            System.out.println("Fôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            System.out.println("Suas cartas disponíveis:");
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
                currentEvent = Events.HEROFINISH;
                notifyEvent();
                System.out.println("Turno encerrado pelo jogador.");
            }
            else{
                System.out.println("Escolha inválida!");
            }

            if(hero.getStamina() <= 0){
                    App.pause(2000);
                    System.out.println("\nAcabou seu fôlego! Vez do inimigo");
                currentEvent = Events.HEROFINISH;
                notifyEvent();
                }
            }
    }

    public void printIntroduction(Hero hero, Enemy enemy) {
        App.pause(300);
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
        App.pause(300);
    }

    public Enemy chooseEnemy(Enemy[] enemies, Scanner scanner) {
        System.out.println("Escolha seu inimigo:");
        for (int i = 0; i < enemies.length; i++) {
            System.out.print(i + 1 + " - " );
            System.out.println(enemies[i].getName() + " (Vida: " + enemies[i].getMaxHealth() + ") (Fôlego: " + enemies[i].getMaxStamina() + ")");
        } 
        int choice = scanner.nextInt();
        while (choice < 1 || choice > enemies.length) {
            System.out.println("Escolha inválida!. Tente novamente:");
            choice = scanner.nextInt();
        }
        return enemies[choice -1];
    }

}