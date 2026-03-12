public class ShieldCard {
    private String name;
    private int staminaCost;
    private int damageBlocked;
    private boolean wasUsed;

    public ShieldCard(String name, int staminaCost, int damageBlocked){
        this.name = name;
        this.staminaCost = staminaCost;
        this.damageBlocked = damageBlocked;
        this.wasUsed = false;
    }

    public void useShieldCard(Hero hero){
        if(hero.getStamina() >= this.staminaCost){
            System.out.println("Você usou " + this.name + "!");
            hero.spendStamina(this.staminaCost);
            hero.gainShield(this.damageBlocked);
        }
        else{
            System.out.println("Energia insuficiente para usar " + this.name + ".");
        }

    }

    public void tryCard(Hero hero, Enemy enemy) {
        if (! this.getWasUsed())
            if (hero.getStamina() < this.getCost()) {
                System.out.println("Estamina insuficiente! Tente outra jogada");
            }
            else {
                this.useShieldCard(hero);
                this.setWasUsed(true);
            }
        else {
            System.out.println("Você selecionou um golpe já utilizado! Tente novamente");
        }
    }

    public void printCardStats() {
        System.out.println(this.getName() + " no próximo movimento do adversário (Bloqueio: " + this.getDamageBlocked() + " | Custo: " + this.getCost() + ")");
    }

    public String getName(){
        return this.name;
    }

    public int getCost(){
        return this.staminaCost;
    }

    public int getDamageBlocked(){
        return this.damageBlocked;
    }

    public boolean getWasUsed(){
        return this.wasUsed;
    }

    public void setWasUsed(boolean use) {
        this.wasUsed = use;
    }

}
