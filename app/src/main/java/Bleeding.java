/**
 * Efeito de sangramento que causa dano periódico à entidade afetada.
 * <p>
 * O sangramento age no <strong>fim do turno do atacante</strong>:
 * ao fim do turno do herói se o dono for um inimigo, e ao fim do turno do inimigo
 * se o dono for o herói. A cada ativação, causa dano igual à intensidade atual
 * e reduz a intensidade em 1. Quando a intensidade chega a zero, o efeito é removido.
 * </p>
 *
 * @see Effect
 * @see bleedingCard
 */
public class Bleeding extends Effect {

    /**
     * Constrói um efeito de sangramento.
     *
     * @param name      nome do efeito (geralmente "Sangramento")
     * @param owner     entidade que sofrerá o dano de sangramento
     * @param intensity intensidade inicial (dano causado por ativação)
     */
    public Bleeding(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito no formato "Nome(intensidade)".
     *
     * @return string descritiva do sangramento
     */
    @Override
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual é o momento correto para o sangramento agir
     * e chama {@link #useEffect} quando aplicável.
     * <p>
     * O sangramento age no fim do turno do atacante:
     * {@link Turns.Events#HEROFINISH} para inimigos e
     * {@link Turns.Events#ENEMYFINISH} para o herói.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos com o evento atual
     */
    @Override
    public void beNotified(Turns turn) {
        if (turn.currentEvent == Turns.Events.HEROFINISH && getOwner() instanceof Enemy ||
            turn.currentEvent == Turns.Events.ENEMYFINISH && getOwner() instanceof Hero) 
            useEffect(turn);
    }

    /**
     * Aplica o dano de sangramento ao dono do efeito e reduz a intensidade em 1.
     * <p>
     * Se a intensidade chegar a zero após a redução, o efeito é removido.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos (usado para remover o efeito ao fim)
     */
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