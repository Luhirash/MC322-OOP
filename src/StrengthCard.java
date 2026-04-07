public class StrengthCard extends Card{

    private int strengthIntensity; 
     
    public StrengthCard(String name, int staminaCost, int strengthIntensity, String description) {
        super(name, staminaCost, description);
        this.strengthIntensity = strengthIntensity;
    }

    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " aumentando sua força em " + getMainStat());
        Strength strength = new Strength("Força", attacker, strengthIntensity);
        turns.subscribe(strength);
        receiver.printStats();
    }

    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Força: " + getMainStat() + " | Custo: " + getStaminaCost() + ")");
    }

    @Override
    public int getMainStat() {
        return strengthIntensity;
    }

}
