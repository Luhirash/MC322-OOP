public class Strength extends Effect {

    public Strength(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    @Override
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    @Override
    public void beNotified(Turns turn) {
        //nada acontece, o bonus e aplicado no momento de ataque
    }

    @Override
    protected void useEffect(Turns turn) {
        //nada acontece, o bonus e aplicado no momento de ataque
    }
}