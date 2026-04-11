package Cards;
import Effects.Effect;
import Effects.Strength;
import Entities.Entity;

/**
 * Carta de combate que causa dano direto ao alvo.
 *
 * <p>É a carta mais comum do jogo. Ao ser jogada, inflige um dano calculado pela
 * soma do {@link #damageInflicted dano base} com o {@link Entity#getStrengthBonus()
 * bônus de força} ativo no atacante. Isso permite que o efeito {@link Strength Força}
 * amplifique o dano das cartas de ataque.</p>
 *
 * <p>Exemplos do deck do herói: jab (5 de dano), direto (8), uppercut (11),
 * chute na cabeça (12).</p>
 *
 * @see Card
 * @see Entity#getStrengthBonus()
 * @see Strength
 */
public class DamageCard extends Card{

    /**
     * Dano base infligido ao alvo ao usar esta carta, antes de qualquer bônus.
     * <p>O dano final é {@code damageInflicted + attacker.getStrengthBonus()}.</p>
     */
    private int damageInflicted;

    /**
     * Constrói uma carta de dano.
     *
     * @param name            nome da carta exibido nas mensagens de combate
     * @param staminaCost     custo em fôlego para usá-la
     * @param damageInflicted dano base causado ao alvo (sem bônus de força)
     * @param description     descrição textual do golpe
     */
    public DamageCard(String name, int staminaCost, int damageInflicted, String description){
        super(name, staminaCost, description);
        this.damageInflicted = damageInflicted;
    }
    
    /**
     * Executa o golpe: gasta o fôlego do atacante, calcula o dano total e o aplica ao receptor.
     *
     * <p>O dano total é {@code damageInflicted + attacker.getStrengthBonus()}.
     * Se houver bônus de força ativo, a mensagem exibida inclui o valor adicional
     * entre parênteses para informar o jogador.</p>
     *
     * @param attacker entidade que usa a carta (terá seu fôlego reduzido)
     * @param receiver entidade que recebe o dano (terá escudo/vida reduzidos)
     * @param turns    referência ao gerenciador de turnos (não utilizado diretamente aqui)
     */
    public Effect useCard (Entity attacker, Entity receiver) {
        attacker.spendStamina(super.getStaminaCost());
        int totalDamage = damageInflicted + attacker.getStrengthBonus();
        receiver.receiveDamage(totalDamage);
        if (attacker.getStrengthBonus() > 0)
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano! (+" + attacker.getStrengthBonus() + " de Força)");
        else
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano!");
        receiver.printStats();
        return null;
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>Golpear com NomeDaCarta (Dano: X | Custo: Y)</pre>
     */
    public void printCardStats() {
        System.out.println("Golpear com " + this.getName() + " (Dano: " + this.getDamageInflicted() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna o dano base desta carta (sem bônus de força).
     *
     * @return dano base infligido ao alvo
     */
    public int getDamageInflicted(){
        return this.damageInflicted;
    }

    /**
     * Retorna o atributo principal da carta, que é o dano base infligido.
     *
     * @return dano base (equivalente a {@link #getDamageInflicted()})
     */
    public int getMainStat() {
        return getDamageInflicted();
    }
}