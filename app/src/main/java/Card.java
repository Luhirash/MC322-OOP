/**
 * Classe abstrata que representa uma carta de combate.
 * <p>
 * Toda carta possui nome, custo de fôlego, descrição e um estado de uso.
 * As subclasses devem implementar o efeito concreto da carta ({@link #useCard}),
 * a exibição de seus atributos ({@link #printCardStats}) e seu atributo principal ({@link #getMainStat}).
 * </p>
 *
 * @see DamageCard
 * @see ShieldCard
 * @see bleedingCard
 * @see timeoutCard
 * @see StrengthCard
 */
public abstract class Card {

    /** Nome da carta. */
    private String name;

    /** Descrição textual do efeito da carta. */
    private String description;

    /** Custo em fôlego para jogar esta carta. */
    private int staminaCost;

    /** Indica se a carta já foi usada neste turno. */
    private boolean wasUsed;

    /**
     * Constrói uma carta com seus atributos básicos.
     *
     * @param name        nome da carta
     * @param staminaCost custo em fôlego para utilizá-la
     * @param description descrição textual do efeito
     */
    public Card (String name, int staminaCost, String description) {
        this.name = name;
        this.staminaCost = staminaCost;
        this.wasUsed = false;
        this.description = description;
    }

    /**
     * Executa o efeito da carta no combate.
     *
     * @param attacker entidade que está usando a carta
     * @param receiver entidade que recebe o efeito da carta
     * @param turns    referência ao gerenciador de turnos (para inscrição de efeitos)
     */
    protected abstract void useCard(Entity attacker, Entity receiver, Turns turns);

    /**
     * Imprime no console as estatísticas da carta (nome, custo, atributo principal).
     */
    public abstract void printCardStats();

    /**
     * Retorna o atributo principal da carta (dano, bloqueio, intensidade do efeito etc.).
     *
     * @return valor do atributo principal
     */
    public abstract int getMainStat();

    /**
     * Tenta usar a carta caso o herói possua fôlego suficiente.
     * <p>
     * Se bem-sucedido, marca a carta como usada ({@link #wasUsed}) e retorna {@code true}.
     * Caso contrário, exibe uma mensagem de fôlego insuficiente e retorna {@code false}.
     * </p>
     *
     * @param hero  o herói que tenta usar a carta
     * @param enemy o inimigo-alvo da carta
     * @param turns referência ao gerenciador de turnos
     * @return {@code true} se a carta foi usada com sucesso; {@code false} caso contrário
     */
    public boolean tryCard(Hero hero, Enemy enemy, Turns turns){
        if(hero.getStamina() >= this.getStaminaCost()) {
            this.useCard(hero, enemy, turns);
            this.setWasUsed(true);
            return true;
        }
        else{
            System.out.println("-> Fôlego insuficiente para usar " + this.getName() + "!");
            return false;
        }
    }    

    /**
     * Retorna o nome da carta.
     *
     * @return nome da carta
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retorna o custo em fôlego da carta.
     *
     * @return custo em fôlego
     */
    public int getStaminaCost(){
        return this.staminaCost;
    }

    /**
     * Retorna se a carta já foi usada neste turno.
     *
     * @return {@code true} se a carta foi usada; {@code false} caso contrário
     */
    public boolean getWasUsed(){
        return this.wasUsed;
    }

    /**
     * Define o estado de uso da carta.
     *
     * @param use {@code true} para marcar como usada; {@code false} para redefinir
     */
    public void setWasUsed(boolean use) {
        this.wasUsed = use;
    }

    /**
     * Retorna a descrição textual da carta.
     *
     * @return descrição da carta
     */
    public String get_description(){
        return this.description;
    }

}