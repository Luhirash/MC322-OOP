/**
 * Efeito de força que concede bônus de dano à entidade afetada.
 * <p>
 * O bônus de dano é consultado diretamente em {@link Entity#getStrengthBonus()} durante o cálculo
 * de dano das {@link DamageCard}s. A cada fim de turno do dono do efeito,
 * a intensidade é reduzida em 1. Quando a intensidade chega a zero, o efeito é removido.
 * </p>
 *
 * @see Effect
 * @see StrengthCard
 */
public class Strength extends Effect {

    /**
     * Constrói um efeito de força.
     *
     * @param name      nome do efeito (geralmente "Força")
     * @param owner     entidade que receberá o bônus de dano
     * @param intensity intensidade inicial (quantidade de bônus de dano por uso)
     */
    public Strength(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito no formato "Nome(intensidade)".
     *
     * @return string descritiva da força
     */
    @Override
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual corresponde ao fim do turno do dono do efeito
     * e chama {@link #useEffect} quando aplicável.
     * <p>
     * Age em {@link Turns.Events#ENEMYFINISH} para inimigos e
     * {@link Turns.Events#HEROFINISH} para o herói.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos com o evento atual
     */
    @Override
    public void beNotified(Turns turn) {
        if (turn.currentEvent == Turns.Events.ENEMYFINISH && super.getOwner() instanceof Enemy || 
            turn.currentEvent == Turns.Events.HEROFINISH && super.getOwner() instanceof Hero) 
            useEffect(turn);
    }

    /**
     * Reduz a intensidade do efeito em 1 ao fim do turno do dono.
     * <p>
     * O bônus de dano em si é lido passivamente por {@link Entity#getStrengthBonus()};
     * este método apenas contabiliza o decaimento do efeito.
     * Quando a intensidade chega a zero, o efeito é removido.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos (usado para remover o efeito ao fim)
     */
    @Override
    protected void useEffect(Turns turn) {
        this.addIntensity(-1);
        if (this.getIntensity() == 0) {
            System.out.println("[Força] A força extra de " + getOwner().getName() + " acabou");
            this.effectFinish(turn);
            }
    }
}