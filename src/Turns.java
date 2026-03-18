import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Turns {
    
    private Hero hero;
    private Enemy enemy;
    private PurchaseStack drawPile;
    private StackOfCards discardPile;//Pile porque nao esta organizado(stack e organizado)
    private PlayerHand playerHand;
    private Card[] hits;

    public Turns(Hero hero, Enemy enemy, PurchaseStack drawPile, StackOfCards discardPile, PlayerHand playerHand, Card[] hits) {
        this.hero = hero;
        this.enemy = enemy;
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.playerHand = playerHand;
        this.hits = hits;
    }

    public Card chooseCard() {
        Random number = new Random();
        int action = number.nextInt(6);//ataques do inimigo escolhidos

        return this.hits[action];
    }

    public void enemyTurn(){
        while(enemy.getStamina() > 0){        
            Card Card = chooseCard();//o array tem tipos shield e dano
            if(Card instanceof DamageCard){
                if(enemy.getStamina() >= Card.getStaminaCost() && hero.isAlive()){
                    DamageCard damageCard = (DamageCard) Card;//isso e casting
                    App.pause(1000);
                    enemy.attack(hero, damageCard);//aqui eu transferi o método para dentro de enemy
                    if(! hero.isAlive()){//se o ataque matou
                        break;
                    }
                }
                else if(enemy.getStamina() < 3){
                    break;//esse e o minimo de stamina para o golpe minimo-forçado a encerrar
                }
                else if (hero.isAlive()){
                    continue;//pode ser que tenha energia suficiente para outro golpe
                }
            }
            else{//shield card
                if (enemy.getStamina() >= Card.getStaminaCost() && hero.isAlive()) {
                    ShieldCard shieldCard = (ShieldCard) Card;
                    App.pause(1000);
                    System.out.println(enemy.getName() + " aumentou seus reflexos\n");
                    enemy.gainShield(shieldCard.getDamageBlocked());
                    enemy.spendStamina(shieldCard.getStaminaCost());
                }
                else{//se nao rodar if - ou hero morreu ou nao tem menos de 3 de energia
                    break;
                }
            }
        }
        if (hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " encerrou seu turno");
        }
    }

    public void HeroTurn(Scanner scanner) {
        hero.newTurn();
        boolean playerTurn = true;
        //fase de compra
        for(int i = 0; i < 4; i++){
            if(drawPile.isEmpty()){
                System.out.println("\n Baralho vazio! Embaralhando pilha de descarte");
                while(!discardPile.isEmpty()){//enquanto houver termos
                    drawPile.addCard(discardPile.popCard());//sai do descarte e vai pra compra
                }
                drawPile.shuffle();//embaralha novamente a pilha de compra
            }
            if(!drawPile.isEmpty() && !playerHand.isFull()){
                playerHand.addCard(drawPile.popCard());//sai da loja para a mao
            }
        }
        while (playerTurn && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){
            App.pause(1000);
            printIntroduction();
            System.out.println("Fôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            System.out.println("Golpes possíveis:");
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
            ArrayList<Card> unused_cards = playerHand.discardAll();//descarte de cartas que sobrou
            for(Card i : unused_cards){
                discardPile.addCard(i);
            }
    }

    public void printIntroduction() {
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
    }
}


