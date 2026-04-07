public abstract class Card {

    private String name;
    private String description;
    private int staminaCost;
    private boolean wasUsed;

    public Card (String name, int staminaCost, String description) {
        this.name = name;
        this.staminaCost = staminaCost;
        this.wasUsed = false;
        this.description = description;
    }

    protected abstract void useCard(Entity attacker, Entity receiver, Turns turns);
    public abstract void printCardStats();
    public abstract int getMainStat();

    public boolean tryCard(Hero hero, Enemy enemy, Turns turns){
        if(hero.getStamina() >= this.getStaminaCost()) {
            this.useCard(hero, enemy, turns);
            this.setWasUsed(true);
            return true;//realizou o golpe
        }
        else{
            System.out.println("-> Fôlego insuficiente para usar " + this.getName() + "!");
            return false;//não deu
        }
    }    

    public String getName(){
        return this.name;
    }

    public int getStaminaCost(){
        return this.staminaCost;
    }

    public boolean getWasUsed(){
        return this.wasUsed;
    }

    public void setWasUsed(boolean use) {
        this.wasUsed = use;
    }

    public String get_description(){
        return this.description;
    }

}
