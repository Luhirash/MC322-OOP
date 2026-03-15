public class ShieldCard extends Card {

    private int damageBlocked;

    public ShieldCard(String name, int staminaCost, int damageBlocked){
        super(name, staminaCost);
        this.damageBlocked = damageBlocked;
    }

    public void useCard(Hero hero, Enemy enemy){
        System.out.println("Você usou " + super.getName() + "!");
        hero.spendStamina(super.getStaminaCost());
        hero.gainShield(this.damageBlocked);
    }

    public void printCardStats() {
        System.out.println(this.getName() + " no próximo movimento do adversário (Bloqueio: " + this.getDamageBlocked() + " | Custo: " + this.getStaminaCost() + ")");
    }

    public int getDamageBlocked(){
        return this.damageBlocked;
    }

    public int getMainStat() {
        return getDamageBlocked();
    }
}
