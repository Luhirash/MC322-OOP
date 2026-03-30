public class Bleeding extends Effect {

    public Bleeding(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }
    @Override//override permite editar metodo herdado para se adequar a classe filha
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    @Override
    public void beNotified(Turns turn) {
        // Sangramento age no fim do turno do herói
        if (turn.currentEvent == Turns.Events.HEROFINISH) {
            useEffect(turn);
        }
    }

    @Override
    protected void useEffect(Turns turn) {
        System.out.println("[Sangramento] " + getOwner().getName() + " sofre " + getIntensity() + " de dano pelo sangramento!");
        getOwner().receiveDamage(getIntensity());
        getOwner().printStats();
        addIntensity(-1);
        if (getIntensity() == 0) {
            System.out.println("[Sangramento] O sangramento em " + getOwner().getName() + " foi estancado.");
            effectFinish(turn);
        }
    }
}