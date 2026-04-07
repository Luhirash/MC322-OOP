public class bleedingCard extends Card {

    private int bleedingIntensity; 
     
    public bleedingCard(String name, int staminaCost, int bleedingIntensity, String description) {
        super(name, staminaCost, description);
        this.bleedingIntensity = bleedingIntensity;
    }

    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e cortou " + receiver.getName() + " causando um sangramento de intensidade " + getMainStat() + "!");
        Bleeding bleeding = new Bleeding("Sangramento", receiver, bleedingIntensity);
        turns.subscribe(bleeding);
        receiver.printStats();
    }

    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Sangramento: " + bleedingIntensity + " | Custo: " + getStaminaCost() + ")");
    }

    @Override
    public int getMainStat() {
        return bleedingIntensity;
    }

}