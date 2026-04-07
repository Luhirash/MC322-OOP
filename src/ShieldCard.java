public class ShieldCard extends Card {

    private int damageBlocked;

    public ShieldCard(String name, int staminaCost, int damageBlocked, String description){
        super(name, staminaCost, description);
        this.damageBlocked = damageBlocked;
    }

    public void useCard(Entity attacker, Entity receiver, Turns turns){
        System.out.println(attacker.getName() + " usou " + super.getName() + "!");
        attacker.spendStamina(super.getStaminaCost());
        attacker.gainShield(this.damageBlocked);
        attacker.printStats();
    }

    public void printCardStats() {
        System.out.println(this.getName() + " (Bloqueio: " + this.getDamageBlocked() + " | Custo: " + this.getStaminaCost() + ")");
    }

    public int getDamageBlocked(){
        return this.damageBlocked;
    }

    public int getMainStat() {
        return getDamageBlocked();
    }
}
