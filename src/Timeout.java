public class Timeout extends Effect{

    public Timeout(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    public void beNotified(Turns turn) {
        if (turn.currentEvent == Turns.Events.HEROSTART) {
            useEffect(turn);
        }
    }

    protected void useEffect(Turns turn) {
        getOwner().gainHealth(getIntensity());
        addIntensity(-1);
        if (getIntensity() == 0)
            effectFinish(turn);
    }  
}