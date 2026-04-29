package Cards;
import Effects.Strength;
import Effects.Effect;
import Entities.Entity;

/**
 * Carta de combate que aplica o efeito de {@link Strength força} ao usuário.
 *
 * <p>Ao ser jogada, cria um efeito de {@link Strength} sobre o <b>próprio atacante</b>
 * e o inscreve no gerenciador de turnos. Enquanto ativo, o efeito de força é consultado
 * passivamente por todas as {@link DamageCard}s do usuário através de
 * {@link Entity#getStrengthBonus()}, somando o bônus ao dano de cada golpe.</p>
 *
 * <p>O efeito decai em 1 ponto de intensidade ao fim de cada turno do dono.
 * Se a força já estiver ativa, a nova intensidade é acumulada à existente.</p>
 *
 * @see Card
 * @see Strength
 * @see Entity#getStrengthBonus()
 * @see DamageCard
 */
public class StrengthCard extends EffectCard{

    /**
     * Intensidade do bônus de força concedido ao usuário.
     * <p>Representa o bônus adicional de dano somado a cada {@link DamageCard} usada
     * enquanto o efeito estiver ativo. Decrementado em 1 ao fim de cada turno do dono.</p>
     */
    private int strengthIntensity; 
    
    /**
     * Constrói uma carta de força.
     *
     * @param name              nome da carta exibido nas mensagens de combate
     * @param staminaCost       custo em fôlego para usá-la
     * @param strengthIntensity intensidade do bônus de força (bônus de dano por turno ativo)
     * @param description       descrição textual da ação
     */
    public StrengthCard(String name, int staminaCost, int strengthIntensity, String description) {
        super(name, staminaCost, description);
        this.strengthIntensity = strengthIntensity;
    }

    /**
     * Executa a carta: gasta o fôlego do atacante, cria um efeito de {@link Strength}
     * sobre ele e o inscreve no gerenciador de turnos.
     *
     * <p>O efeito é aplicado sobre o <b>atacante</b> (quem jogou a carta).
     * Ao contrário de {@link BleedingCard}, a força beneficia o usuário, não o receptor.
     * O status exibido é o do receptor (inimigo), pois a força não altera os status
     * visíveis do herói imediatamente.</p>
     *
     * @param attacker entidade que usa a carta e receberá o bônus de força
     * @param receiver entidade-alvo (não afetada diretamente — seus status são exibidos para contexto)
     */
    public Effect useCard(Entity attacker, Entity receiver) {
        attacker.spendStamina(super.getStaminaCost());
        System.out.println(attacker.getName() + " usou " + getName() + " aumentando sua força em " + getMainStat());
        return new Strength("Força", attacker, strengthIntensity);
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>NomeDaCarta (Força: X | Custo: Y)</pre>
     */
    @Override
    public void printCardStats() {
        System.out.println(getName() + " (Força: " + getMainStat() + " | Custo: " + getStaminaCost() + ")");
    }

    /**
     * Retorna o atributo principal da carta, que é a intensidade do bônus de força.
     *
     * @return intensidade do bônus de força concedido ao usuário
     */
    @Override
    public int getMainStat() {
        return strengthIntensity;
    }

    public void upgrade() {
        this.strengthIntensity += 2; // Aumenta a intensidade do bônus de força
        this.setUpgrade(); 
    }

}