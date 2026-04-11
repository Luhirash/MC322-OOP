package Effects;
import java.util.ArrayList;
import Core.*;
import Cards.*;
import Entities.Entity;

/**
 * Classe abstrata que representa um efeito de status aplicado a uma {@link Entity}.
 *
 * <p>Efeitos de status são aplicados durante o combate por cartas especiais
 * (ex: {@link bleedingCard}, {@link HealingCard}, {@link StrengthCard}) e persistem
 * entre turnos, agindo automaticamente em momentos específicos da rodada.</p>
 *
 * <h2>Integração com o sistema de eventos (Observer)</h2>
 * <p>Cada efeito funciona como um <b>Observer</b> no padrão Publisher/Subscriber
 * implementado por {@link Turns}. Ao ser inscrito via {@link Turns#subscribe(Effect)},
 * o efeito passa a receber notificações de todos os eventos de turno
 * ({@link Turns.Events}). O método {@link #beNotified(Turns)} decide em qual
 * evento o efeito deve agir e chama {@link #useEffect(Turns)} quando apropriado.</p>
 *
 * <h2>Acumulação e remoção</h2>
 * <p>Se o mesmo efeito for aplicado duas vezes à mesma entidade, a intensidade
 * é acumulada ({@link Entity#applyEffect(Effect)})</p>
 *
 * @see Bleeding
 * @see Strength
 * @see Healing
 * @see Turns
 */
public abstract class Effect{
    
    /** Nome identificador do efeito (ex: "Sangramento", "Força", "Recuperação"). */
    private String name;

    /** Entidade que sofre ou se beneficia deste efeito. */
    private Entity owner;

    /**
     * Intensidade atual do efeito.
     * <p>Representa a magnitude do efeito (ex: dano por turno no sangramento,
     * bônus de dano na força, vida recuperada na recuperação).
     * Decrementada a cada ativação; ao chegar a zero, o efeito é removido.</p>
     */
    private int intensity;

    /**
     * Constrói um efeito com seus atributos iniciais.
     *
     * @param name      nome do efeito
     * @param owner     entidade que receberá os efeitos (pode ser benefício ou malefício)
     * @param intensity intensidade inicial do efeito (deve ser positivo)
     */
    public Effect (String name, Entity owner, int intensity) {
        this.name = name;
        this.owner = owner;
        this.intensity = intensity;
    }

    /**
     * Retorna uma representação textual do efeito para exibição na linha de status da entidade.
     * <p>Tipicamente no formato {@code "Nome(intensidade)"}, ex: {@code "Sangramento(3)"}.</p>
     *
     * @return string descritiva do efeito com sua intensidade atual
     */
    public abstract String getString();

    /**
     * Executa a lógica principal do efeito sobre o {@link #owner dono}.
     * <p>Chamado internamente por {@link #beNotified(Turns)} quando o evento correto
     * é detectado. Cada subclasse define o que acontece (causar dano, curar, decrementar etc.)
     * </p>
     *
     * @param turn referência ao gerenciador de turnos (necessário para remover o efeito ao fim)
     */
    protected abstract void useEffect();

    /**
     * Recebe a notificação de um evento de turno do gerenciador {@link Turns}.
     * <p>Cada subclasse verifica se o {@link Turns#currentEvent evento atual} é o
     * momento correto para agir e chama {@link #useEffect(Turns)} quando for o caso.
     * Eventos disponíveis: {@link Turns.Events#HEROSTART}, {@link Turns.Events#HEROFINISH},
     * {@link Turns.Events#ENEMYSTART}, {@link Turns.Events#ENEMYFINISH}.</p>
     *
     * @param turn referência ao gerenciador de turnos com o evento atual definido em
     *             {@link Turns#currentEvent}
     */
    public abstract void beNotified(GameManager gameManager);

    /**
     * Retorna o nome do efeito.
     *
     * @return nome do efeito (ex: "Sangramento", "Força", "Recuperação")
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a intensidade atual do efeito.
     *
     * @return intensidade atual (sempre positiva enquanto o efeito estiver ativo)
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Define diretamente a intensidade do efeito.
     * <p>Uso interno: prefira {@link #addIntensity(int)} para ajustes graduais.</p>
     *
     * @param newIntensity novo valor de intensidade
     */
    private void setIntensity(int newIntensity) {
        intensity = newIntensity;
    }

    /**
     * Soma um valor à intensidade atual do efeito.
     * <p>Use valores negativos para decrementar (ex: {@code addIntensity(-1)} a cada turno).
     *</p>
     *
     * @param value valor a somar (positivo para aumentar, negativo para reduzir)
     */
    public void addIntensity(int value) {
        setIntensity(getIntensity() + value);
    }

    /**
     * Retorna a entidade dona/afetada por este efeito.
     *
     * @return entidade que sofre ou se beneficia do efeito
     */
    public Entity getOwner() {
        return owner;
    }

    /**
     * Localiza este efeito dentro de uma lista de efeitos pelo nome e pela referência do dono.
     *
     * <p>Usado para verificar se o efeito já está ativo ({@link Entity#applyEffect(Effect)})
     * </p>
     *
     * @param effects lista de efeitos onde buscar
     * @return índice deste efeito na lista, ou {@code -1} se não encontrado
     */
    public int getIndex(ArrayList<Effect> effects) {
        int effectIndex = -1;
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i).getName().equals(getName()) && effects.get(i).getOwner() == getOwner())
                effectIndex = i;
        }
        return effectIndex;
    }

    /**
     * Encerra o efeito: remove-o da lista de efeitos do {@link #owner dono} e
     * cancela sua inscrição no gerenciador de turnos.
     *
     * <p>Deve ser chamado pelas subclasses em {@link #useEffect(Turns)} quando
     * {@link #getIntensity()} chegar a zero.</p>
     *
     * @param turn referência ao gerenciador de turnos para cancelar a inscrição via
     *             {@link Turns#unsubscribe(Effect)}
     */

    public boolean isExpired() {
        return this.getIntensity() <= 0;
    }
}