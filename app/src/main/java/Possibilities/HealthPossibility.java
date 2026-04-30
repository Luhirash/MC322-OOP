package Possibilities;

/**
 * Resultado de escolha que afeta os pontos de vida do herói.
 *
 * <p>Pode representar um ganho de vida (descanso, item curativo) ou uma
 * perda de vida (dano sofrido em evento narrativo). A alteração mecânica
 * é processada por {@link Entities.Hero#gainHealth(int)}.</p>
 *
 * @see Possibility
 * @see Events.Choice
 */
public class HealthPossibility extends Possibility {
    
    /** Quantidade de vida a ser recuperada (positivo) ou perdida (negativo). */
    private int healthChange;
    
    /**
     * Constrói uma possibilidade envolvendo alteração de vida.
     *
     * @param description  narrativa da ação (ex: "Se entregar à dor")
     * @param healthChange valor da alteração de vida (positivo para cura, negativo para dano)
     */
    public HealthPossibility(String description, int healthChange) {
        super(description);
        this.healthChange = healthChange;
    }

    /**
     * Retorna a variação de pontos de vida desta possibilidade.
     *
     * @return pontos de vida afetados (positivo ou negativo)
     */
    public int getHealthChange() {
        return this.healthChange;
    }
}