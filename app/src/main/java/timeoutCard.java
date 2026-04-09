/**
 * Carta de combate que aplica o efeito de {@link Timeout recuperação} ao usuário.
 * <p>
 * Ao ser jogada, cria e registra um efeito de recuperação no gerenciador de turnos,
 * restaurando pontos de vida ao atacante no início de seus próximos turnos.
 * </p>
 *
 * @see Card
 * @see Timeout
 */
public class timeoutCard extends Card {

    /** Quantidade de vida recuperada por turno (intensidade do efeito de recuperação). */
    private int healStacks; 

    /**
     * Constrói uma carta de recuperação de vida.
     *
     * @param name        nome da carta
     * @param staminaCost custo em fôlego para usá-la
     * @param healStacks  quantidade de vida recuperada por turno
     * @param description descrição textual da ação
     */
    public timeoutCard(String name, int staminaCost, int healStacks, String description) {
        super(name, staminaCost, description);
        this.healStacks = healStacks;
    }

    /**
     * Executa a carta: gasta fôlego do atacante, cria um efeito de recuperação sobre ele
     * e o registra no gerenciador de turnos.
     * <p>
     * O efeito age sobre o próprio usuário da carta, e não sobre o receptor.
     * </p>
     *
     * @param attacker entidade que usa a carta e receberá a recuperação de vida
     * @param receiver entidade-alvo (não utilizada nesta carta)
     * @param turns    gerenciador de turnos onde o efeito será inscrito
     */
    @Override
    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e ganhou recuperação de intensidade " + healStacks + "!");
        Timeout timeout = new Timeout("Recuperação", attacker, healStacks);
        turns.subscribe(timeout);
        attacker.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta (nome, quantidade de recuperação e custo de fôlego).
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Recuperação: " + healStacks + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a quantidade de vida recuperada por turno.
     *
     * @return quantidade de vida recuperada por turno
     */
    @Override
    public int getMainStat() {
        return healStacks;
    }
}