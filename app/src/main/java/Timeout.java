/**
 * Efeito de recuperação de vida aplicado no início do turno do dono.
 * <p>
 * Criado pela {@link timeoutCard}, este efeito restaura pontos de vida
 * ao dono no início de cada um de seus turnos. A intensidade representa
 * a quantidade de vida recuperada por ativação e é decrementada a cada uso.
 * Quando chega a zero, o efeito é removido.
 * </p>
 *
 * @see Effect
 * @see timeoutCard
 */
public class Timeout extends Effect{

    /**
     * Constrói um efeito de recuperação de vida.
     *
     * @param name      nome do efeito (geralmente "Recuperação")
     * @param owner     entidade que receberá a cura
     * @param intensity quantidade de vida recuperada por ativação
     */
    public Timeout(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito no formato "Nome(intensidade)".
     *
     * @return string descritiva da recuperação
     */
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual é o início do turno do dono do efeito
     * e chama {@link #useEffect} quando aplicável.
     * <p>
     * Age em {@link Turns.Events#HEROSTART} para o herói e
     * {@link Turns.Events#ENEMYSTART} para inimigos.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos com o evento atual
     */
    public void beNotified(Turns turn) {
        if (turn.currentEvent == Turns.Events.HEROSTART && getOwner() instanceof Hero ||
            turn.currentEvent == Turns.Events.ENEMYSTART && getOwner() instanceof Enemy) {
            useEffect(turn);
        }
    }

    /**
     * Recupera vida ao dono do efeito e reduz a intensidade em 1.
     * <p>
     * Quando a intensidade chega a zero, o efeito é encerrado e removido.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos (usado para remover o efeito ao fim)
     */
    protected void useEffect(Turns turn) {
        getOwner().gainHealth(getIntensity());
        System.out.println("[Recuperação] " + getOwner() + " recuperou " + getIntensity() + "de vida");
        addIntensity(-1);
        if (getIntensity() == 0)
            effectFinish(turn);
    }  
}