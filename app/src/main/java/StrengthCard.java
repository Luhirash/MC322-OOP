/**
 * Carta de combate que aplica o efeito de {@link Strength força} ao usuário.
 * <p>
 * Ao ser jogada, cria e registra um efeito de força no gerenciador de turnos,
 * concedendo bônus de dano nas {@link DamageCard}s usadas pelo atacante
 * durante os próximos turnos. Se a força já estiver ativa, a intensidade é acumulada.
 * </p>
 *
 * @see Card
 * @see Strength
 */
public class StrengthCard extends Card{

    /** Intensidade do bônus de força concedido ao usuário. */
    private int strengthIntensity; 
    
    /**
     * Constrói uma carta de força.
     *
     * @param name               nome da carta
     * @param staminaCost        custo em fôlego para usá-la
     * @param strengthIntensity  intensidade do bônus de força (bônus de dano por turno)
     * @param description        descrição textual da ação
     */
    public StrengthCard(String name, int staminaCost, int strengthIntensity, String description) {
        super(name, staminaCost, description);
        this.strengthIntensity = strengthIntensity;
    }

    /**
     * Executa a carta: gasta fôlego do atacante, cria um efeito de força sobre ele
     * e o registra no gerenciador de turnos.
     *
     * @param attacker entidade que usa a carta e receberá o bônus de força
     * @param receiver entidade-alvo (não é diretamente afetada por esta carta)
     * @param turns    gerenciador de turnos onde o efeito será inscrito
     */
    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " aumentando sua força em " + getMainStat());
        Strength strength = new Strength("Força", attacker, strengthIntensity);
        turns.subscribe(strength);
        receiver.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta (nome, intensidade da força e custo de fôlego).
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Força: " + getMainStat() + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a intensidade do bônus de força.
     *
     * @return intensidade do bônus de força
     */
    @Override
    public int getMainStat() {
        return strengthIntensity;
    }

}