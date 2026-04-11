package Cards;
import Core.GameManager;
import Effects.Effect;
import Effects.Healing;
import Entities.Entity;

/**
 * Carta de combate que aplica o efeito de {@link Healing recuperação} de vida ao usuário.
 *
 * <p>Diferente de {@link HealthCard} (que recupera vida instantaneamente), esta carta
 * cria um efeito de {@link Healing} que restaura pontos de vida no <b>início de cada
 * turno</b> do usuário, por uma quantidade de rodadas igual à intensidade definida.
 * O efeito age sobre o próprio atacante (quem jogou a carta), não sobre o receptor.</p>
 *
 * <p>Exemplos do deck do herói: "pedir tempo técnico" (intensidade 2),
 * "beber suco secreto" (intensidade 3).</p>
 *
 * @see Card
 * @see Healing
 * @see Turns#subscribe(Effect)
 */
public class HealingCard extends Card {

    /**
     * Intensidade do efeito de recuperação criado ao jogar esta carta.
     * <p>Representa tanto a quantidade de vida recuperada por turno quanto
     * a duração do efeito em turnos (a intensidade é decrementada em 1 por ativação).</p>
     */
    private int healingIntensity; 

    /**
     * Constrói uma carta de recuperação de vida.
     *
     * @param name             nome da carta exibido nas mensagens de combate
     * @param staminaCost      custo em fôlego para usá-la
     * @param healingIntensity intensidade da recuperação (vida recuperada por turno e duração em turnos)
     * @param description      descrição textual da ação de cura
     */
    public HealingCard(String name, int staminaCost, int healingIntensity, String description) {
        super(name, staminaCost, description);
        this.healingIntensity = healingIntensity;
    }

    /**
     * Executa a carta: gasta o fôlego do atacante, cria um efeito de {@link Healing}
     * sobre ele e o inscreve no gerenciador de turnos.
     *
     * <p>O efeito de recuperação age no <b>início dos próximos turnos</b> do usuário
     * ({@link Turns.Events#HEROSTART} para o herói), restaurando vida a cada rodada
     * até a intensidade se esgotar.</p>
     *
     * @param attacker entidade que usa a carta e receberá a recuperação de vida
     * @param receiver entidade-alvo (não utilizada — a recuperação é sempre para o atacante)
     * @param turns    gerenciador de turnos onde o efeito de recuperação será inscrito
     */
    @Override
    public void useCard(Entity attacker, Entity receiver, GameManager gameManager) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e ganhou recuperação de intensidade " + healingIntensity + "!");
        Healing healing = new Healing("Recuperação", attacker, healingIntensity);
        gameManager.subscribe(healing);
        attacker.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>NomeDaCarta (Recuperação: X | Custo: Y)</pre>
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Recuperação: " + healingIntensity + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a intensidade da recuperação de vida.
     *
     * @return intensidade do efeito de recuperação (vida recuperada por turno)
     */
    @Override
    public int getMainStat() {
        return healingIntensity;
    }
}