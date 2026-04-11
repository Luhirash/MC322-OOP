package Effects;
import Cards.BleedingCard;
import Core.GameManager;
import Core.Turns;
import Entities.*;

/**
 * Efeito de status de sangramento que causa dano periódico à entidade afetada.
 *
 * <p>Criado pela {@link BleedingCard}, este efeito é aplicado sobre o <b>alvo</b> do golpe
 * (geralmente o inimigo, quando o herói usa a carta). O sangramento age automaticamente
 * ao fim do turno do atacante — ou seja, ao fim do turno de quem o aplicou:</p>
 * <ul>
 *   <li>Se o dono for um {@link Enemy inimigo}, age em {@link Turns.Events#HEROFINISH}
 *       (fim do turno do herói, que foi o atacante).</li>
 *   <li>Se o dono for o {@link Hero herói}, age em {@link Turns.Events#ENEMYFINISH}
 *       (fim do turno do inimigo, que foi o atacante).</li>
 * </ul>
 *
 * <h2>Decaimento do efeito</h2>
 * <p>A cada ativação, causa dano igual à {@link Effect#getIntensity() intensidade atual}
 * e a reduz em 1. Quando a intensidade chega a zero, o efeito é removido automaticamente
 * via {@link Effect#effectFinish(Turns)}.</p>
 *
 * <h2>Acumulação</h2>
 * <p>Se um novo sangramento for aplicado enquanto o efeito já estiver ativo, a intensidade
 * é somada ({@link Entity#applyEffect(Effect)}), prolongando e intensificando o dano.</p>
 *
 * @see Effect
 * @see bleedingCard
 * @see Turns.Events
 */
public class Bleeding extends Effect {

    /**
     * Constrói um efeito de sangramento.
     *
     * @param name      nome do efeito (convencionalmente "Sangramento")
     * @param owner     entidade que sofrerá o dano periódico de sangramento
     * @param intensity intensidade inicial: dano causado por ativação (decrementado a cada uso)
     */
    public Bleeding(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito para exibição no status da entidade.
     *
     * @return string no formato {@code "Sangramento(X)"}, onde X é a intensidade atual
     */
    @Override
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual é o momento correto para o sangramento agir
     * e chama {@link #useEffect(Turns)} quando aplicável.
     *
     * <p>Regra de ativação (age no fim do turno de quem causou o corte):</p>
     * <ul>
     *   <li>{@link Turns.Events#HEROFINISH} → ativa se o dono for um {@link Enemy} (inimigo sangrando).</li>
     *   <li>{@link Turns.Events#ENEMYFINISH} → ativa se o dono for o {@link Hero} (herói sangrando).</li>
     * </ul>
     *
     * @param turn referenciador de turnos com o {@link Turns#currentEvent evento atual}
     */
    @Override
    public void beNotified(GameManager gameManager) {
        if (gameManager.currentEvent == GameManager.Events.HEROFINISH && getOwner() instanceof Enemy ||
            gameManager.currentEvent == GameManager.Events.ENEMYFINISH && getOwner() instanceof Hero) 
            useEffect(gameManager);
    }

    /**
     * Aplica o dano de sangramento ao dono do efeito e decrementa a intensidade em 1.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Exibe mensagem informando o dano de sangramento.</li>
     *   <li>Aplica o dano igual à intensidade atual via {@link Entity#receiveDamage(int)}.</li>
     *   <li>Exibe os status atualizados do dono.</li>
     *   <li>Reduz a intensidade em 1 via {@link Effect#addIntensity(int)}.</li>
     *   <li>Se a intensidade chegar a zero, exibe mensagem e remove o efeito via
     *       {@link Effect#effectFinish(Turns)}.</li>
     * </ol>
     *
     * @param turn referência ao gerenciador de turnos (necessário para remover o efeito ao esgotar)
     */
    @Override
    protected void useEffect(GameManager gameManager) {
        System.out.println("[Sangramento] " + getOwner().getName() + " sofre " + getIntensity() + " de dano pelo sangramento!");
        getOwner().receiveDamage(getIntensity());
        getOwner().printStats();
        addIntensity(-1);
        if (getIntensity() == 0) {
            System.out.println("[Sangramento] O sangramento em " + getOwner().getName() + " foi estancado.");
            effectFinish(gameManager);
        }
    }
}