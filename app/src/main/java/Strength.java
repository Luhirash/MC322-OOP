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
        if (turn.currentEvent == Turns.Events.ENEMYFINISH && super.getOwner() instanceof Enemy || 
            turn.currentEvent == Turns.Events.HEROFINISH && super.getOwner() instanceof Hero) 
            useEffect(turn);
    }

    @Override
    protected void useEffect(Turns turn) {
        //Único acontecimento é diminuição da intensidade ao ser notificado. Efeito está funcionando o tempo todo.
        this.addIntensity(-1);
        if (this.getIntensity() == 0) {
            System.out.println("[Força] A força extra de " + getOwner().getName() + " acabou");
            this.effectFinish(turn); // Remove o efeito se chegar a 0
            }
    }
}