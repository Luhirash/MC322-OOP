public class timeoutCard extends Card {

    private int healStacks; 
    //private Turns turns;    // referência ao publisher para inscrever o efeito

    public timeoutCard(String name, int staminaCost, int healStacks, String description) {
        super(name, staminaCost, description);
        this.healStacks = healStacks;
        // this.turns = turns;
    }

    @Override//ela age em quem utilizou mesmo, por isso override
    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e ganhou recuperação de intensidade " + healStacks + "!");
        Timeout timeout = new Timeout("Recuperação", attacker, healStacks);
        turns.subscribe(timeout);
        attacker.printStats();
    }

    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Recuperação: " + healStacks + " | Custo: " + getStaminaCost() + ")");
    }

    @Override
    public int getMainStat() {
        return healStacks;
    }

    // public Turns getTurns() {
    //     return turns;
    // }
}