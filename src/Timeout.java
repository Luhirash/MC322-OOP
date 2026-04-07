public class Timeout extends Effect{

    public Timeout(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    public void beNotified(Turns turn) {
        //Recuperação por pedido de tempo é no início do turno daquele que usou a carta
        if (turn.currentEvent == Turns.Events.HEROSTART && getOwner() instanceof Hero ||
            turn.currentEvent == Turns.Events.ENEMYSTART && getOwner() instanceof Enemy) {
            useEffect(turn);
        }
    }

    protected void useEffect(Turns turn) {
        getOwner().gainHealth(getIntensity());
        System.out.println("[Recuperação] " + getOwner() + " recuperou " + getIntensity() + "de vida");
        addIntensity(-1);
        if (getIntensity() == 0)
            effectFinish(turn);
    }  
}