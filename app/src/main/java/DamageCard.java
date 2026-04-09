public class DamageCard extends Card{

    private int damageInflicted;

    public DamageCard(String name, int staminaCost, int damageInflicted, String description){
        super(name, staminaCost, description);
        this.damageInflicted = damageInflicted;
    }
    
    public void useCard (Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        int totalDamage = damageInflicted + attacker.getStrengthBonus();
        receiver.receiveDamage(totalDamage);
        if (attacker.getStrengthBonus() > 0)
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano! (+" + attacker.getStrengthBonus() + " de Força)");
        else
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano!");
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