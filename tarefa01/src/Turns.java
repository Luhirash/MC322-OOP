import java.util.Random;

public class Turns {
    
    private Hero hero;
    private Enemy enemy;
    private DamageCard[] attackCards;
    private ShieldCard shieldCard;

    public Turns(Hero hero, Enemy enemy, DamageCard[] attackCards, ShieldCard shieldCard) {
        this.hero = hero;
        this.enemy = enemy;
        this.attackCards = attackCards;
        this.shieldCard = shieldCard;
    }

    public DamageCard chooseCard() {
        
        Random number = new Random();
        int action = number.nextInt(4);

        return this.attackCards[action];
    }

    public void enemyTurn(){
        DamageCard attackCard = chooseCard();

        while(enemy.getStamina() > attackCard.getCost() && hero.isAlive()){

            App.pause(1000);
            System.out.println(enemy.getName() + " golpeou com um " + attackCard.getName());
            hero.receiveDamage(attackCard);
            enemy.spendStamina(attackCard.getCost());
            attackCard = chooseCard();
        }
        if (enemy.getStamina() >= shieldCard.getCost() && hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " aumentou seus reflexos\n");
            enemy.gainShield(shieldCard.getDamageBlocked());
        }
        if (hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " encerrou seu turno");
        }
    }

    public void printHeroTurn(DamageCard firstAttack, DamageCard secondAttack) {
        System.out.println("Fôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
        if (!firstAttack.getWasUsed()) {
            System.out.print("1 - ");
            firstAttack.printCardStats();
        }
        else {
            System.out.println("1 - Golpe já utilizado");
        }

        if (!secondAttack.getWasUsed()) {
            System.out.print("2 - ");
            secondAttack.printCardStats();
        }
        else {
            System.out.println("2 - Golpe já utilizado");
        }

        if (!shieldCard.getWasUsed()) {
            System.out.print("3 - ");
            shieldCard.printCardStats();
        }
        else {
            System.out.println("3 - Golpe já utilizado");
        }

        System.out.println("4 - Encerrar turno");
        System.out.println("");
    }

    public boolean allCardsUsed(DamageCard firstAttack, DamageCard secondAttack, ShieldCard shieldCard) {
        return (firstAttack.getWasUsed() && secondAttack.getWasUsed() && shieldCard.getWasUsed());
    }

    public void printIntroduction() {
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
    }
}


