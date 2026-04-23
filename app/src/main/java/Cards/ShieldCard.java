package Cards;
import Entities.Entity;
import Effects.Effect;

/**
 * Carta de combate que concede pontos de escudo (bloqueio) ao usuário.
 *
 * <p>Ao ser jogada, adiciona pontos de escudo ao <b>próprio atacante</b> (quem a jogou),
 * protegendo-o contra o dano recebido durante o turno do inimigo. O escudo absorve
 * dano antes da vida ({@link Entity#receiveDamage(int)}) e é zerado automaticamente
 * no início de cada novo turno ({@link Entity#newTurn()}).</p>
 *
 * <p>Exemplos do deck do herói: "desviar" (8 de bloqueio), "focar" (5),
 * "agachar" (4), "andar para trás" (2).</p>
 *
 * @see Card
 * @see Entity#gainShield(int)
 * @see Entity#receiveDamage(int)
 */
public class ShieldCard extends Card {

    /**
     * Quantidade de pontos de escudo concedidos ao usuário ao jogar esta carta.
     * <p>Este valor é somado ao escudo atual da entidade via {@link Entity#gainShield(int)}.</p>
     */
    private int damageBlocked;

    /**
     * Constrói uma carta de escudo.
     *
     * @param name          nome da carta exibido nas mensagens de combate
     * @param staminaCost   custo em fôlego para usá-la
     * @param damageBlocked pontos de escudo concedidos ao usuário
     * @param description   descrição textual da ação de defesa
     */
    public ShieldCard(String name, int staminaCost, int damageBlocked, String description){
        super(name, staminaCost, description);
        this.damageBlocked = damageBlocked;
    }

    /**
     * Executa a defesa: gasta o fôlego e concede pontos de escudo ao atacante.
     *
     * <p>O escudo é sempre aplicado ao <b>usuário da carta</b> (o parâmetro {@code attacker}),
     * independentemente de quem é o receptor. Isso reflete a natureza defensiva da carta:
     * o lutador se protege para o próximo ataque do adversário.</p>
     *
     * @param attacker entidade que usa a carta e recebe o escudo (terá fôlego e escudo atualizados)
     * @param receiver entidade-alvo (ignorada nesta carta — o escudo é sempre para o atacante)
     */
    public Effect useCard(Entity attacker, Entity receiver){
        System.out.println(attacker.getName() + " usou " + super.getName() + "!");
        attacker.spendStamina(super.getStaminaCost());
        attacker.gainShield(this.damageBlocked);
        attacker.printStats();
        return null;
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>NomeDaCarta (Bloqueio: X | Custo: Y)</pre>
     */
    public void printCardStats() {
        System.out.println(this.getName() + " (Bloqueio: " + this.getDamageBlocked() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna a quantidade de pontos de escudo concedidos por esta carta.
     *
     * @return pontos de escudo (bloqueio)
     */
    public int getDamageBlocked(){
        return this.damageBlocked;
    }

    /**
     * Retorna o atributo principal da carta, que é a quantidade de bloqueio.
     *
     * @return pontos de escudo (equivalente a {@link #getDamageBlocked()})
     */
    public int getMainStat() {
        return getDamageBlocked();
    }
}