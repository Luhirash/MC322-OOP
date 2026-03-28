public class EyePunch extends Effect {
    
    public EyePunch(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    public void getString() {
        System.out.println("Dá um soco no olho do inimigo, resultando em uma cicatriz com intensidade inicial de dano de " + getIntensity());
    }

    public void beNotified(Turns turn) {
        if (turn.currentEvent == Turns.Events.ENEMYFINISH) {
            useEffect(turn);
        }
    }

    protected void useEffect(Turns turn) {
        getOwner().receiveDamage(getIntensity());
        addIntensity(-1);
        if (getIntensity() == 0)
            effectFinish(turn);
    }  
} 