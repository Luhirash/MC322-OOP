public class DamageCard extends Card{

    private int damageInflicted;

    public DamageCard(String name, int staminaCost, int damageInflicted, String description){
        super(name, staminaCost, description);
        this.damageInflicted = damageInflicted;
    }
    
    public void useCard (Entity attacker, Entity receiver) {
        attacker.spendStamina(super.getStaminaCost());
        receiver.receiveDamage((damageInflicted));
        System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + this.damageInflicted + " de dano!");
        receiver.printStats();
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
