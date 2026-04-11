package Cards;
import Core.GameManager;
import Core.Turns;
import Entities.*;
import Piles.DiscardPile;
import Piles.PlayerHand;

/**
 * Classe abstrata que representa uma carta de combate do jogo.
 *
 * <p>Toda carta possui um nome, um custo em fôlego para ser jogada, uma descrição
 * textual do golpe/ação, e um estado de uso ({@link #wasUsed}) para controle de turno.
 * As subclasses concretas definem o efeito específico de cada carta implementando
 * {@link #useCard}, {@link #printCardStats} e {@link #getMainStat}.</p>
 *
 * <h2>Ciclo de vida de uma carta</h2>
 * <ol>
 *   <li>O jogador seleciona a carta pela mão ({@link PlayerHand}).</li>
 *   <li>{@link #tryCard} verifica se o herói possui fôlego suficiente.</li>
 *   <li>Se sim, {@link #useCard} é chamado e a carta vai para o descarte ({@link DiscardPile}).</li>
 *   <li>Se não, uma mensagem é exibida e a carta permanece na mão.</li>
 * </ol>
 *
 * @see DamageCard
 * @see ShieldCard
 * @see bleedingCard
 * @see HealingCard
 * @see StrengthCard
 * @see HealthCard
 */
public abstract class Card {

    /** Nome da carta exibido nas mensagens de combate e na mão do jogador. */
    private String name;

    /** Descrição textual da ação realizada ao jogar esta carta. */
    private String description;

    /**
     * Custo em fôlego para jogar esta carta.
     * <p>O fôlego do herói deve ser maior ou igual a este valor para que a carta
     * possa ser usada ({@link #tryCard}).</p>
     */
    private int staminaCost;

    /**
     * Indica se a carta já foi jogada no turno atual.
     * <p>Após o uso, a carta é movida para o descarte; este campo serve como
     * salvaguarda adicional de controle de estado.</p>
     */
    private boolean wasUsed;

    /**
     * Constrói uma carta com seus atributos básicos.
     * <p>Toda carta começa com {@link #wasUsed} igual a {@code false}.</p>
     *
     * @param name        nome da carta
     * @param staminaCost custo em fôlego para utilizá-la (deve ser positivo)
     * @param description descrição textual da ação realizada
     */
    public Card (String name, int staminaCost, String description) {
        this.name = name;
        this.staminaCost = staminaCost;
        this.wasUsed = false;
        this.description = description;
    }

    /**
     * Executa o efeito concreto desta carta no combate.
     *
     * <p>Cada subclasse implementa sua própria lógica: causar dano, ganhar escudo,
     * aplicar sangramento, curar etc. O fôlego do atacante <b>deve ser gasto dentro
     * deste método</b> via {@link Entity#spendStamina(int)}.</p>
     *
     * @param attacker entidade que está jogando a carta
     * @param receiver entidade que recebe o efeito da carta (pode ser ignorada em algumas cartas)
     * @param turns    referência ao gerenciador de turnos, necessário para inscrever efeitos de status
     */
    protected abstract void useCard(Entity attacker, Entity receiver, GameManager gameManager);

    /**
     * Imprime no console as estatísticas desta carta (nome, atributo principal e custo de fôlego).
     * <p>Exibido na mão do jogador antes de ele escolher sua ação.</p>
     */
    public abstract void printCardStats();

    /**
     * Retorna o atributo principal desta carta.
     *
     * <p>O significado varia conforme o tipo:</p>
     * <ul>
     *   <li>{@link DamageCard}: dano base infligido.</li>
     *   <li>{@link ShieldCard}: pontos de escudo concedidos.</li>
     *   <li>{@link bleedingCard}: intensidade do sangramento.</li>
     *   <li>{@link HealingCard}: vida recuperada por turno.</li>
     *   <li>{@link StrengthCard}: bônus de força concedido.</li>
     *   <li>{@link HealthCard}: vida recuperada instantaneamente.</li>
     * </ul>
     *
     * @return valor do atributo principal da carta
     */
    public abstract int getMainStat();


    public void enemyUseCard(Entity attacker, Entity receiver, GameManager gameManager) {
        useCard(attacker, receiver,gameManager);
    }

    /**
     * Tenta jogar a carta verificando se o herói possui fôlego suficiente.
     *
     * <p>Se o herói tiver fôlego maior ou igual ao custo ({@link #staminaCost}),
     * o método chama {@link #useCard} e marca a carta como usada ({@link #wasUsed} = {@code true}).
     * Caso contrário, exibe uma mensagem de fôlego insuficiente e retorna {@code false}
     * sem alterar o estado do jogo.</p>
     *
     * @param hero  o herói que tenta usar a carta
     * @param enemy o inimigo-alvo da carta
     * @param turns referência ao gerenciador de turnos
     * @return {@code true} se a carta foi usada com sucesso; {@code false} se fôlego insuficiente
     */
    public boolean tryCard(Hero hero, Enemy enemy, GameManager gameManager){
        if(hero.getStamina() >= this.getStaminaCost()) {
            this.useCard(hero, enemy, gameManager);
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
     * Retorna o custo em fôlego necessário para jogar esta carta.
     *
     * @return custo em fôlego
     */
    public int getStaminaCost(){
        return this.staminaCost;
    }

    /**
     * Indica se esta carta já foi jogada no turno atual.
     *
     * @return {@code true} se a carta foi usada; {@code false} caso contrário
     */
    public boolean getWasUsed(){
        return this.wasUsed;
    }

    /**
     * Define o estado de uso da carta.
     * <p>Chamado automaticamente por {@link #tryCard} após uso bem-sucedido.
     * Pode ser chamado com {@code false} para reiniciar o estado da carta.</p>
     *
     * @param use {@code true} para marcar como usada; {@code false} para redefinir
     */
    public void setWasUsed(boolean use) {
        this.wasUsed = use;
    }

    /**
     * Retorna a descrição textual da ação realizada ao jogar esta carta.
     *
     * @return descrição da carta
     */
    public String get_description(){
        return this.description;
    }

}