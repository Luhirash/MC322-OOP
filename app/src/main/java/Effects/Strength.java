package Effects;
import Cards.DamageCard;
import Cards.StrengthCard;
import Core.GameManager;
import Entities.*;

/**
 * Efeito de status de força que concede bônus de dano ao dono em suas {@link DamageCard}s.
 *
 * <p>Criado pela {@link StrengthCard}, este efeito age de forma <b>passiva</b>:
 * o bônus de dano é lido diretamente por {@link Entity#getStrengthBonus()} a cada vez
 * que o dono usa uma {@link DamageCard}, sem precisar de ativação explícita.</p>
 *
 * <p>O decaimento do efeito ocorre ao <b>fim do turno do dono</b>: a intensidade é
 * decrementada em 1 por turno. Quando chega a zero, o efeito é removido e o bônus
 * de dano deixa de existir.</p>
 *
 * <h2>Momento de decaimento</h2>
 * <ul>
 *   <li>Se o dono for o {@link Hero herói}: decai em <code>HEROFINISH</code>
 *       (fim do turno do herói).</li>
 *   <li>Se o dono for um {@link Enemy inimigo}: decai em <code>HEROFINISH</code>
 *       (fim do turno do inimigo).</li>
 * </ul>
 *
 * @see Effect
 * @see StrengthCard
 * @see Entity#getStrengthBonus()
 * @see DamageCard
 */
public class Strength extends Effect {

    /**
     * Constrói um efeito de força.
     *
     * @param name      nome do efeito (convencionalmente "Força")
     * @param owner     entidade que receberá o bônus de dano
     * @param intensity intensidade inicial: valor do bônus de dano e duração em turnos
     */
    public Strength(String name, Entity owner, int intensity) {
        super(name, owner, intensity);
    }

    /**
     * Retorna a representação textual do efeito para exibição no status da entidade.
     *
     * @return string no formato {@code "Força(X)"}, onde X é a intensidade atual
     */
    @Override
    public String getString() {
        return getName() + "(" + getIntensity() + ")";
    }

    /**
     * Verifica se o evento atual é o fim do turno do dono e chama
     * {@link #useEffect()} quando aplicável.
     *
     * <p>Regra de decaimento:</p>
     * <ul>
     *   <li><code>HEROFINISH</code> → ativa se o dono for o {@link Hero herói}.</li>
     *   <li><code>ENEMYFINISH</code> → ativa se o dono for um {@link Enemy inimigo}.</li>
     * </ul>
     *
     * @param gameManager referenciador de turnos com o {@link GameManager#currentEvent evento atual}
     */
    @Override
    public void beNotified(GameManager gameManager) {
        if (gameManager.currentEvent == GameManager.Events.ENEMYFINISH && super.getOwner() instanceof Enemy || 
            gameManager.currentEvent == GameManager.Events.HEROFINISH && super.getOwner() instanceof Hero) 
            useEffect();
    }

    /**
     * Decrementa a intensidade do efeito em 1 ao fim do turno do dono.
     *
     * <p>O bônus de dano em si é lido passivamente por {@link Entity#getStrengthBonus()};
     * este método apenas contabiliza o desgaste do efeito ao longo dos turnos.
     * Quando a intensidade chega a zero, exibe uma mensagem.</p>
     *
     */
    @Override
    protected void useEffect() {
        this.addIntensity(-1);
        if (this.getIntensity() == 0) {
            System.out.println("[Força] A força extra de " + getOwner().getName() + " acabou");
            }
    }
}