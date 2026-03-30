public class bleedingCard extends Card {

    private int bleedingStacks; 
    private Turns turns;      

    public bleedingCard(String name, int staminaCost, int bleedingStacks, String description, Turns turns) {
        super(name, staminaCost, description);
        this.bleedingStacks = bleedingStacks;
        this.turns = turns;
    }

    protected void useCard(Entity attacker, Entity receiver) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e cortou " + receiver.getName() + " causando " + bleedingStacks + " acúmulo(s) de sangramento!");
        Bleeding bleeding = new Bleeding("Sangramento", receiver, bleedingStacks);
        turns.subscribe(bleeding);
        receiver.printStats();
    }

    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Sangramento: " + bleedingStacks + " | Custo: " + getStaminaCost() + ")");
    }

    @Override
    public int getMainStat() {
        return bleedingStacks;
    }

    public Turns getTurns() {
        return turns;
    }
}