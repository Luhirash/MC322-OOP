import java.util.ArrayList;

/**
 * Classe base abstrata que representa uma entidade combatente no jogo.
 * <p>
 * Contém os atributos fundamentais de qualquer participante da luta:
 * vida, escudo, fôlego e efeitos ativos. Tanto {@link Hero} quanto {@link Enemy}
 * estendem esta classe.
 * </p>
 */
public abstract class Entity{

    /** Nome da entidade. */
    private String name;

    /** Vida atual da entidade. */
    private int health;

    /** Vida máxima da entidade. */
    private int maxHealth;

    /** Pontos de escudo (bloqueio) atuais. */
    private int shield;

    /** fôlego atual disponível para usar cartas. */
    private int stamina;

    /** fôlego máxima, restaurada a cada novo turno. */
    private int maxStamina;

    /** Lista de efeitos ativos sobre esta entidade. */
    private ArrayList<Effect> effects;

    /**
     * Constrói uma entidade com os atributos iniciais.
     *
     * @param name       nome da entidade
     * @param maxHealth  vida máxima (também define a vida inicial)
     * @param maxStamina fôlego máxima (também define a fôlego inicial)
     */
    public Entity(String name, int maxHealth, int maxStamina){
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.shield = 0;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.effects = new ArrayList<Effect>();
    }

    /**
     * Aplica dano à entidade, consumindo primeiro o escudo e depois a vida.
     * <p>
     * Caso o dano ultrapasse escudo + vida, a vida é zerada (sem ficar negativa).
     * </p>
     *
     * @param damageInflicted quantidade de dano a receber
     */
    public void receiveDamage(int damageInflicted){
        if (this.shield + this.health <= damageInflicted) {
            this.setShield(0);
            this.setHealth(0);
        }
        else if (this.shield >= damageInflicted){
            this.shield -= damageInflicted;
        }
        else{
            int realDamage = damageInflicted - this.shield;
            this.shield = 0;
            this.health -= realDamage;
        }
    }

    /**
     * Retorna o conjunto de cartas disponíveis para esta entidade usar em combate.
     *
     * @return array de {@link Card} com os golpes disponíveis
     */
    public abstract Card[] getHits();

    /**
     * Adiciona pontos de escudo à entidade.
     *
     * @param shieldPoints quantidade de escudo a ganhar
     */
    public void gainShield(int shieldPoints){
        int newShield = this.getShield() + shieldPoints;
        this.setShield(newShield);
    }

    /**
     * Recupera pontos de vida da entidade.
     *
     * @param healthPoints quantidade de vida a recuperar
     */
    public void gainHealth(int healthPoints){
        setHealth(getHealth() + healthPoints);
    }

    /**
     * Imprime no console os status atuais da entidade (vida, escudo e efeitos ativos).
     */
    public void printStats() {
        if (isAlive())
            System.out.print(this.getName() + " (Vida: " + this.getHealth() + "/" + this.getMaxHealth() + ") (Bloqueio: " + this.getShield() + ")");
        else
            System.out.print(this.getName() + " (Vida: 0/" + this.getMaxHealth() + ") (Bloqueio: " + this.getShield() + ")");
        if (!effects.isEmpty()) {
            System.out.print(" [Efeitos: ");
            for (int i = 0; i < effects.size(); i++) {
                System.out.print(effects.get(i).getString());
                if (i < effects.size() - 1) System.out.print(", ");
            }
            System.out.print("]");
        }
        System.out.println();
    }

    /**
     * Verifica se a entidade ainda está viva.
     *
     * @return {@code true} se a vida for maior que zero; {@code false} caso contrário
     */
    public boolean isAlive(){
        return this.health > 0;
    }

    /**
     * Gasta uma quantidade de fôlego da entidade.
     *
     * @param consumedStamina quantidade de fôlego a consumir
     */
    public void spendStamina(int consumedStamina){
        int newStamina = this.getStamina() - consumedStamina;
        this.setStamina(newStamina);
    }

    /**
     * Restaura a fôlego ao máximo e zera o escudo no início de um novo turno.
     */
    public void newTurn(){
        this.setStamina(this.getMaxStamina());;
        this.setShield(0);
    }

    /**
     * Aplica um efeito à entidade.
     * <p>
     * Se o efeito já estiver ativo, sua intensidade é incrementada.
     * Caso contrário, o efeito é adicionado à lista de efeitos ativos.
     * </p>
     *
     * @param effect o efeito a ser aplicado
     */
    public void applyEffect(Effect effect) {
        int effectIndex = effect.getIndex(effects);
        if (effectIndex == -1)
            effects.add(effect);
        else
            effects.get(effectIndex).addIntensity(effect.getIntensity());
    }

    /**
     * Retorna a vida atual da entidade.
     *
     * @return vida atual
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Define a vida atual da entidade.
     *
     * @param health novo valor de vida
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retorna a vida máxima da entidade.
     *
     * @return vida máxima
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Retorna os pontos de escudo atuais.
     *
     * @return escudo atual
     */
    public int getShield() {
        return this.shield;
    }

    /**
     * Define os pontos de escudo da entidade.
     *
     * @param shield novo valor de escudo
     */
    public void setShield(int shield) {
        this.shield = shield;
    }

    /**
     * Retorna a fôlego atual da entidade.
     *
     * @return fôlego atual
     */
    public int getStamina() {
        return this.stamina;
    }

    /**
     * Define a fôlego atual da entidade.
     *
     * @param stamina novo valor de fôlego
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    } 

    /**
     * Retorna a fôlego máxima da entidade.
     *
     * @return fôlego máxima
     */
    public int getMaxStamina() {
        return this.maxStamina;
    }

    /**
     * Retorna o nome da entidade.
     *
     * @return nome da entidade
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retorna o bônus de dano proveniente do efeito de Força ativo, se houver.
     *
     * @return intensidade do efeito "Força", ou {@code 0} se não estiver ativo
     */
    public int getStrengthBonus() {
        for (Effect e : effects) {
            if (e.getName().equals("Força"))
                return e.getIntensity();
        }
        return 0;
    }

    /**
     * Retorna a lista de efeitos ativos sobre a entidade.
     *
     * @return lista de {@link Effect} ativos
     */
    public ArrayList<Effect> getEffects() {
        return effects;
    }
}