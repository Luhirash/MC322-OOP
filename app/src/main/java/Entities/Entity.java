package Entities;
import java.util.ArrayList;

import Core.GameManager;
import Core.Turns;
import Cards.Card;
import Cards.DamageCard;
import Effects.Effect;
import Effects.Strength;

/**
 * Classe base abstrata que representa qualquer participante de um combate.
 *
 * <p>Define os atributos e comportamentos compartilhados pelo {@link Hero} e por
 * todos os {@link Enemy inimigos}: vida, escudo, fôlego e efeitos de status ativos.
 * A lógica específica de cada combatente (como o conjunto de cartas disponíveis)
 * é delegada às subclasses.</p>
 *
 * <h2>Mecânicas principais</h2>
 * <ul>
 *   <li><b>Vida ({@code health}):</b> reduzida ao receber dano. Quando chega a zero,
 *       a entidade é considerada derrotada ({@link #isAlive()} retorna {@code false}).</li>
 *   <li><b>Escudo ({@code shield}):</b> absorve dano antes que a vida seja afetada.
 *       É zerado no início de cada novo turno ({@link #newTurn()}).</li>
 *   <li><b>Fôlego ({@code stamina}):</b> recurso gasto para usar cartas. Restaurado
 *       ao máximo no início de cada turno.</li>
 *   <li><b>Efeitos ({@code effects}):</b> lista de {@link Effect efeitos de status} ativos
 *       (ex: sangramento, força, recuperação). São aplicados e removidos dinamicamente
 *       pelo sistema de eventos de {@link Turns}.</li>
 * </ul>
 *
 * @see Hero
 * @see Enemy
 * @see Effect
 */
public abstract class Entity{

    /** Nome da entidade exibido nas mensagens de combate. */
    private String name;

    /** Quantidade atual de pontos de vida. */
    private int health;

    /** Quantidade máxima de pontos de vida. */
    private int maxHealth;

    /**
     * Pontos de escudo (bloqueio) atuais.
     * <p>O escudo absorve dano antes da vida e é zerado no início de cada turno.</p>
     */
    private int shield;

    /**
     * Fôlego atual disponível para usar cartas.
     * <p>Cada carta possui um custo em fôlego ({@link Card#getStaminaCost()}).
     * Quando o fôlego acaba, o turno do herói é encerrado automaticamente.</p>
     */
    private int stamina;

    /**
     * Fôlego máximo, restaurado integralmente a cada novo turno via {@link #newTurn()}.
     */
    private int maxStamina;

    /**
     * Lista de efeitos de status ativos sobre esta entidade.
     * <p>Gerenciada pelo sistema Observer de {@link Turns}: efeitos são inscritos via
     * <code>GameManager.subscribe(Effect)</code> e removidos via <code>GameManager.unsubscribe(Effect)</code>.</p>
     */
    private ArrayList<Effect> effects;

    /**
     * Constrói uma entidade com vida e fôlego completos e sem escudo ou efeitos ativos.
     *
     * @param name       nome da entidade
     * @param maxHealth  vida máxima (também usada como vida inicial)
     * @param maxStamina fôlego máximo (também usado como fôlego inicial)
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
     *
     * <p>Regras de aplicação:</p>
     * <ul>
     *   <li>Se o dano for maior ou igual à soma de escudo + vida, ambos são zerados
     *       (a entidade é derrotada sem ficar com valores negativos).</li>
     *   <li>Se o dano for menor ou igual ao escudo, apenas o escudo é reduzido.</li>
     *   <li>Caso contrário, o escudo é zerado e o dano restante é subtraído da vida.</li>
     * </ul>
     *
     * @param damageInflicted quantidade de dano a receber (deve ser positivo)
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
     * <p>Cada subclasse define seu próprio deck com cartas e custos específicos.</p>
     *
     * @return array de {@link Card} com os golpes e habilidades disponíveis
     */
    public abstract ArrayList<Card> getHits();

    /**
     * Adiciona uma carta às possibilidades de uso da entidade durante a batalha.
     * @param card carta a ser adicionada
     */
    public void addCard(Card card) {
        getHits().add(card);
    }

    /**
     * Adiciona pontos de escudo à entidade (acumulando ao escudo existente).
     *
     * @param shieldPoints quantidade de pontos de escudo a ganhar (deve ser positivo)
     */
    public void gainShield(int shieldPoints){
        int newShield = this.getShield() + shieldPoints;
        this.setShield(newShield);
    }

    /**
     * Recupera pontos de vida da entidade (acumulando à vida existente).
     * @param healthPoints quantidade de pontos de vida a recuperar (deve ser positivo)
     */
    public int gainHealth(int healthPoints){
        int realGain = healthPoints;
        if (getHealth() + healthPoints > getMaxHealth())
            realGain = getMaxHealth() - getHealth();
        else if (getHealth() + healthPoints < 0)
            realGain = -getHealth();
        setHealth(getHealth() + realGain);
        return realGain;
    }

    /**
     * Imprime no console os status atuais da entidade: vida, escudo e efeitos ativos.
     *
     * <p>Formato exibido:</p>
     * <pre>
     * NomeDaEntidade (Vida: X/MaxVida) (Bloqueio: Y) [Efeitos: Sangramento(2), Força(1)]
     * </pre>
     * <p>A seção de efeitos só aparece se houver efeitos ativos.
     * Se a entidade estiver morta, a vida é exibida como {@code 0}.</p>
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
     * Verifica se a entidade ainda está viva (vida maior que zero).
     *
     * @return {@code true} se {@code health > 0}; {@code false} se foi derrotada
     */
    public boolean isAlive(){
        return this.health > 0;
    }

    /**
     * Gasta uma quantidade de fôlego da entidade.
     * <p>Chamado automaticamente pelas cartas em {@link Card#useCard} ao serem utilizadas.</p>
     *
     * @param consumedStamina quantidade de fôlego a consumir
     */
    public void spendStamina(int consumedStamina){
        int newStamina = this.getStamina() - consumedStamina;
        this.setStamina(newStamina);
    }

    /**
     * Prepara a entidade para um novo turno: restaura o fôlego ao máximo e zera o escudo.
     * <p>Chamado no início de cada turno pelo gerenciador {@link Turns}.</p>
     */
    public void newTurn(){
        this.setStamina(this.getMaxStamina());;
        this.setShield(0);
    }

    /**
     * Aplica um efeito de status à entidade.
     *
     * <p>Se um efeito com o mesmo nome e dono já estiver ativo, a intensidade do efeito
     * existente é incrementada pelo valor do novo (acumulação). Caso contrário, o efeito
     * é adicionado à lista de efeitos ativos.</p>
     *
     * @param effect o efeito a ser aplicado ou acumulado
     * @see Effect#getIndex(ArrayList)
     * @see Effect#addIntensity(int)
     */
    public void applyEffect(Effect effect) {
        int effectIndex = effect.getIndex(effects);
        if (effectIndex == -1)
            effects.add(effect);
        else
            effects.get(effectIndex).addIntensity(effect.getIntensity());
    }

    public void clearEffects() {
        getEffects().clear();
    }

    /**
     * Retorna a vida atual da entidade.
     *
     * @return pontos de vida atuais
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Define diretamente a vida atual da entidade.
     * <p>Use {@link #gainHealth(int)} para recuperação e {@link #receiveDamage(int)}
     * para dano, pois ambos respeitam as regras do jogo.</p>
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
     * Retorna os pontos de escudo atuais da entidade.
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
     * Retorna o fôlego atual disponível para usar cartas.
     *
     * @return fôlego atual
     */
    public int getStamina() {
        return this.stamina;
    }

    /**
     * Define o fôlego atual da entidade.
     *
     * @param stamina novo valor de fôlego
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    } 

    /**
     * Retorna o fôlego máximo da entidade.
     *
     * @return fôlego máximo
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
     * Retorna o bônus de dano proveniente do efeito de {@link Strength Força} ativo, se houver.
     *
     * <p>Este bônus é somado ao dano base de todas as {@link DamageCard}s usadas pela entidade
     * enquanto o efeito estiver ativo. Retorna {@code 0} se nenhum efeito de Força estiver aplicado.</p>
     *
     * @return intensidade do efeito "Força" ativo, ou {@code 0} caso não exista
     * Visto em DamageCard.
     */
    public int getStrengthBonus() {
        for (Effect e : effects) {
            if (e.getName().equals("Força"))
                return e.getIntensity();
        }
        return 0;
    }

    /**
     * Retorna a lista de efeitos de status ativos sobre esta entidade.
     *
     * @return lista de {@link Effect} ativos (pode estar vazia, nunca {@code null})
     */
    public ArrayList<Effect> getEffects() {
        return effects;
    }
}