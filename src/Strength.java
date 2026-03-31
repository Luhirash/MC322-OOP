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
        if (turn.currentEvent == Turns.Events.ENEMYFINISH) {
            this.addIntensity(-1);
            if (this.getIntensity() <= 0) {
                this.effectFinish(turn); // Remove o efeito se chegar a 0
            }
        }
    }

    @Override
    protected void useEffect(Turns turn) {
        //nada acontece, o bonus e aplicado no momento de ataque
    }
}