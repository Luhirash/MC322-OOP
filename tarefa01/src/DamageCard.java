public class DamageCard {

    private String name;
    private int staminaCost;
    private int damageInflicted;
    private boolean wasUsed;

    public DamageCard(String name, int staminaCost, int damageInflicted){
        this.name = name;
        this.staminaCost = staminaCost;
        this.damageInflicted = damageInflicted;
        this.wasUsed = false;
    }
    

    public boolean strike(Hero hero, Enemy enemy){
        if(hero.getStamina() >= this.staminaCost){
            hero.spendStamina(staminaCost);
            enemy.receiveDamage((damageInflicted));
            System.out.println("-> Voce usou " + this.name + " e causou " + this.damageInflicted + " de dano!");
            return true;//realizou o golpe
        }
        else{
            System.out.println("-> Energia insuficiente para usar " + this.name + "!");
            return false;//não deu
        }
    }

    public void printCardStats() {
        System.out.println("Golpear com " + this.getName() + " (Dano: " + this.getDamageInflicted() + " | Custo: " + this.getCost() + ")");
    }

    public void tryCard(Hero hero, Enemy enemy) {
        if (! this.getWasUsed())
            if (hero.getStamina() < this.getCost()) {
                System.out.println("Fôlego insuficiente! Tente outra jogada");
            }
            else {
                this.strike(hero, enemy);
                this.setWasUsed(true);
            }
        else {
            System.out.println("Você selecionou um golpe já utilizado! Tente novamente");
        }
    }

    public String getName(){
        return this.name;
    }

    public int getCost(){
        return this.staminaCost;
    }

    public int getDamageInflicted(){
        return this.damageInflicted;
    }

    public boolean getWasUsed(){
        return this.wasUsed;
    }

    public void setWasUsed(boolean use) {
        this.wasUsed = use;
    }

}
