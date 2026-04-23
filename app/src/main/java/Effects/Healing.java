package Effects;
import Cards.HealingCard;
import Core.GameManager;
import Entities.*;

/**
 * Efeito de status de recuperação que restaura pontos de vida ao dono no início de cada turno.
 *
 * <p>Criado pela {@link HealingCard}, este efeito é aplicado sobre o <b>próprio usuário</b>
 * da carta. A cada início de turno do dono, recupera pontos de vida iguais à
 * {@link Effect#getIntensity() intensidade atual} e decrementa a intensidade em 1.
 * Quando a intensidade chega a zero, o efeito é removido automaticamente.</p>
 *
 * <h2>Momento de ativação</h2>
 * <ul>
 *   <li>Se o dono for o {@link Hero herói}: age em {@link GameManager.Events#HEROSTART}
 *       (início do turno do herói).</li>
 *   <li>Se o dono for um {@link Enemy inimigo}: age em {@link GameManager.Events#ENEMYSTART}
 *       (início do turno do inimigo).</li>
 * </ul>
 *
 * @see Effect
 * @see HealingCard
 * @see GameManager.Events
 */
public class Healing extends Effect{

    /**
     * Constrói um efeito de recuperação de vida.
     *
     * @param name      nome do efeito (convencionalmente "Recuperação")
     * @param owner     entidade que receberá a cura a cada turno
     * @param intensity quantidade de vida recuperada por ativação e duração em turnos
     */
    public Healing(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito para exibição no status da entidade.
     *
     * @return string no formato {@code "Recuperação(X)"}, onde X é a intensidade atual
     */
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual é o início do turno do dono e chama
     * {@link #useEffect()} quando aplicável.
     *
     * <p>Regra de ativação:</p>
     * <ul>
     *   <li><code>HEROSTART</code> → ativa se o dono for o {@link Hero herói}.</li>
     *   <li><code>ENEMYSTART</code> → ativa se o dono for um {@link Enemy inimigo}.</li>
     * </ul>
     *
     * @param gameManager referenciador de turnos com o {@link GameManager#currentEvent evento atual}
     */
    public void beNotified(GameManager gameManager) {
        if (gameManager.currentEvent == GameManager.Events.HEROSTART && getOwner() instanceof Hero ||
            gameManager.currentEvent == GameManager.Events.ENEMYSTART && getOwner() instanceof Enemy) {
            useEffect();
        }
    }

    /**
     * Recupera vida ao dono do efeito e decrementa a intensidade em 1.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Adiciona vida ao dono igual à intensidade atual, sem ultrapassar a vida máxima, via {@link Entity#gainHealth(int)}.</li>
     *   <li>Exibe mensagem informando a quantidade de vida recuperada.</li>
     *   <li>Reduz a intensidade em 1 via {@link Effect#addIntensity(int)}.</li>
     * </ol>
     *
     */
    protected void useEffect() {
        int trueHealing = 0;
        if (getOwner().getMaxHealth() - getOwner().getHealth() > getIntensity())
            trueHealing = getIntensity();
        else
            trueHealing = getOwner().getMaxHealth() - getOwner().getHealth();
        getOwner().gainHealth(trueHealing);
        System.out.println("[Recuperação] " + getOwner().getName() + " recuperou " + trueHealing + " de vida");
        addIntensity(-1);
    }  
}