package Cards;
import Core.GameManager;
import Effects.Bleeding;
import Effects.Effect;
import Entities.Entity;

/**
 * Carta de combate que aplica o efeito de {@link Bleeding sangramento} ao alvo.
 *
 * <p>Ao ser jogada, cria um efeito de {@link Bleeding} sobre o receptor e o inscreve
 * no gerenciador de turnos ((<code>GameManager.subscribe()</code>). A partir desse momento,
 * o alvo sofrerá dano periódico a cada fim de turno do atacante:
 * se o herói jogar esta carta, o inimigo sangrará ao fim do turno do herói
 * (<code>HEROFINISH</code>); e vice-versa.</p>
 *
 * <p>Se o alvo já estiver sob efeito de sangramento, a nova intensidade é somada
 * à existente (acumulação de efeitos).</p>
 *
 * <p>Exemplos do deck do herói: "golpe lascerante" (3 de intensidade),
 * "cotovelada cortante" (4 de intensidade).</p>
 *
 * @see Card
 * @see Bleeding
 * @see GameManager#subscribe(Effect)
 */

public class BleedingCard extends EffectCard {

    /**
     * Intensidade inicial do sangramento aplicado ao alvo.
     * <p>Representa o dano por turno causado pelo efeito. A cada ativação,
     * a intensidade é decrementada em 1 até chegar a zero e o efeito ser removido.</p>
     */
    private int bleedingIntensity; 
    
    /**
     * Constrói uma carta de sangramento.
     *
     * @param name              nome da carta exibido nas mensagens de combate
     * @param staminaCost       custo em fôlego para usá-la
     * @param bleedingIntensity intensidade do sangramento (dano por turno aplicado ao alvo)
     * @param description       descrição textual do golpe
     */
    public BleedingCard(String name, int staminaCost, int bleedingIntensity, String description) {
        super(name, staminaCost, description);
        this.bleedingIntensity = bleedingIntensity;
    }

    /**
     * Executa o golpe: gasta o fôlego do atacante, cria um efeito de {@link Bleeding}
     * sobre o receptor e o inscreve no gerenciador de turnos.
     *
     * <p>O efeito age sobre o <b>receptor</b> (quem recebeu o corte), não sobre o atacante.
     * Se o receptor já estiver sangrando, a intensidade é acumulada via
     * <code>Entity.applyEffect()</code>.</p>
     *
     * @param attacker entidade que usa a carta (terá seu fôlego reduzido)
     * @param receiver entidade que receberá o efeito de sangramento
     */
    public Effect useCard(Entity attacker, Entity receiver) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " e cortou " + receiver.getName() + " causando um sangramento de intensidade " + getMainStat() + "!");
        return new Bleeding("Sangramento", receiver, bleedingIntensity);
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>NomeDaCarta (Sangramento: X | Custo: Y)</pre>
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Sangramento: " + bleedingIntensity + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a intensidade do sangramento aplicado.
     *
     * @return intensidade do sangramento (dano por turno causado ao alvo)
     */
    @Override
    public int getMainStat() {
        return bleedingIntensity;
    }

    @Override
    public void upgrade() {
        this.bleedingIntensity += 2; // Aumenta a intensidade do sangramento
        this.setUpgrade(); // Marca a carta como aprimorada
    }

}