public class DamageCard extends Card{

    private int damageInflicted;

    public DamageCard(String name, int staminaCost, int damageInflicted, String description){
        super(name, staminaCost, description);
        this.damageInflicted = damageInflicted;
    }
    
    public void useCard (Hero hero, Enemy enemy) {
        hero.spendStamina(super.getStaminaCost());
        enemy.receiveDamage((damageInflicted));
        System.out.println("-> Voce usou " + this.getName() + " e causou " + this.damageInflicted + " de dano!");
    }

    public void printCardStats() {
        System.out.println("Golpear com " + this.getName() + " (Dano: " + this.getDamageInflicted() + " | Custo: " + this.getStaminaCost() + ")");
    }

    public int getDamageInflicted(){
        return this.damageInflicted;
    }

    public int getMainStat() {
        return getDamageInflicted();
    }
}
