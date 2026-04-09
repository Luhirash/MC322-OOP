/**
 * Carta de combate que aplica o efeito de {@link Bleeding sangramento} ao alvo.
 * <p>
 * Ao ser jogada, cria e registra um efeito de sangramento no gerenciador de turnos,
 * que passará a causar dano periódico ao receptor a cada fim de turno do atacante.
 * Se o alvo já estiver sangrando, a intensidade é acumulada.
 * </p>
 *
 * @see Card
 * @see Bleeding
 */
public class bleedingCard extends Card {

    /** Intensidade do sangramento aplicado ao alvo. */
    private int bleedingIntensity; 
    
    /**
     * Constrói uma carta de sangramento.
     *
     * @param name               nome da carta
     * @param staminaCost        custo em fôlego para usá-la
     * @param bleedingIntensity  intensidade do sangramento aplicado (dano por turno)
     * @param description        descrição textual do golpe
     */
    public bleedingCard(String name, int staminaCost, int bleedingIntensity, String description) {
        super(name, staminaCost, description);
        this.bleedingIntensity = bleedingIntensity;
    }

    /**
     * Executa o golpe: gasta fôlego do atacante, cria um efeito de sangramento
     * sobre o receptor e o registra no gerenciador de turnos.
     *
     * @param attacker entidade que usa a carta
     * @param receiver entidade que receberá o efeito de sangramento
     * @param turns    gerenciador de turnos onde o efeito será inscrito
     */
    protected void useCard(Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e cortou " + receiver.getName() + " causando um sangramento de intensidade " + getMainStat() + "!");
        Bleeding bleeding = new Bleeding("Sangramento", receiver, bleedingIntensity);
        turns.subscribe(bleeding);
        receiver.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta (nome, intensidade do sangramento e custo de fôlego).
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Sangramento: " + bleedingIntensity + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a intensidade do sangramento.
     *
     * @return intensidade do sangramento aplicado
     */
    @Override
    public int getMainStat() {
        return bleedingIntensity;
    }

}