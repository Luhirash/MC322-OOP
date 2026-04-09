import java.util.ArrayList;

/**
 * Classe abstrata que representa um efeito de status aplicado a uma {@link Entity}.
 * <p>
 * Efeitos são gerenciados pelo sistema de publicação/assinatura de {@link Turns}.
 * Cada efeito possui um nome, um dono (a entidade afetada) e uma intensidade
 * que determina a magnitude ou a duração do efeito.
 * </p>
 *
 * @see Bleeding
 * @see Strength
 * @see Timeout
 */
public abstract class Effect{
    
    /** Nome identificador do efeito (ex: "Sangramento", "Força", "Recuperação"). */
    private String name;

    /** Entidade que sofre ou possui este efeito. */
    private Entity owner;

    /** Intensidade atual do efeito (dano por turno, bônus de dano, vida recuperada etc.). */
    private int intensity;

    /**
     * Constrói um efeito com seus atributos iniciais.
     *
     * @param name      nome do efeito
     * @param owner     entidade dona/afetada pelo efeito
     * @param intensity intensidade inicial do efeito
     */
    public Effect (String name, Entity owner, int intensity) {
        this.name = name;
        this.owner = owner;
        this.intensity = intensity;
    }

    /**
     * Retorna uma representação textual do efeito para exibição nos status da entidade.
     *
     * @return string no formato "Nome(intensidade)"
     */
    public abstract String getString();

    /**
     * Executa a lógica principal do efeito sobre o dono.
     * Chamado internamente por {@link #beNotified} quando o evento correto ocorre.
     *
     * @param turn referência ao gerenciador de turnos
     */
    protected abstract void useEffect(Turns turn);

    /**
     * Recebe uma notificação de evento do gerenciador de turnos.
     * <p>
     * Cada subclasse decide em qual evento ({@link Turns.Events}) deve agir
     * e chama {@link #useEffect} quando apropriado.
     * </p>
     *
     * @param turn referência ao gerenciador de turnos com o evento atual
     */
    public abstract void beNotified(Turns turn);

    /**
     * Retorna o nome do efeito.
     *
     * @return nome do efeito
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a intensidade atual do efeito.
     *
     * @return intensidade atual
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Define a intensidade do efeito.
     *
     * @param newIntensity novo valor de intensidade
     */
    private void setIntensity(int newIntensity) {
        intensity = newIntensity;
    }

    /**
     * Adiciona (ou subtrai, se negativo) um valor à intensidade atual do efeito.
     *
     * @param value valor a somar à intensidade (pode ser negativo para reduzir)
     */
    public void addIntensity(int value) {
        setIntensity(getIntensity() + value);
    }

    /**
     * Retorna a entidade dona/afetada por este efeito.
     *
     * @return entidade dona do efeito
     */
    public Entity getOwner() {
        return owner;
    }

    /**
     * Busca o índice deste efeito dentro de uma lista de efeitos,
     * comparando por nome e por referência de dono.
     *
     * @param effects lista de efeitos onde buscar
     * @return índice do efeito na lista, ou {@code -1} se não encontrado
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
     * Finaliza o efeito: remove-o da lista de efeitos do dono e cancela sua assinatura no gerenciador de turnos.
     *
     * @param turn referência ao gerenciador de turnos para cancelar a assinatura
     */
    protected void effectFinish(Turns turn) {
        ArrayList<Effect> ownerEffects = getOwner().getEffects();
        ownerEffects.remove(getIndex(ownerEffects));
        turn.unsubscribe(this);
    }
}