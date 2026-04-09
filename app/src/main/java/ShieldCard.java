/**
 * Carta de combate que concede pontos de escudo (bloqueio) ao usuário.
 * <p>
 * Ao ser jogada, adiciona pontos de escudo ao <strong>atacante</strong> (quem a usou),
 * protegendo-o contra dano recebido até o fim do turno.
 * </p>
 *
 * @see Card
 * @see Entity#gainShield(int)
 */
public class ShieldCard extends Card {

    /** Quantidade de dano bloqueado (pontos de escudo concedidos). */
    private int damageBlocked;

    /**
     * Constrói uma carta de escudo.
     *
     * @param name          nome da carta
     * @param staminaCost   custo em fôlego para usá-la
     * @param damageBlocked pontos de escudo concedidos ao usuário
     * @param description   descrição textual da ação de defesa
     */
    public ShieldCard(String name, int staminaCost, int damageBlocked, String description){
        super(name, staminaCost, description);
        this.damageBlocked = damageBlocked;
    }

    /**
     * Executa a defesa: gasta fôlego e concede pontos de escudo ao atacante.
     * <p>
     * Note que {@code receiver} é ignorado — o escudo é sempre aplicado ao usuário da carta.
     * </p>
     *
     * @param attacker entidade que usa a carta e recebe o escudo
     * @param receiver entidade-alvo (não utilizada nesta carta)
     * @param turns    referência ao gerenciador de turnos (não utilizado aqui)
     */
    public void useCard(Entity attacker, Entity receiver, Turns turns){
        System.out.println(attacker.getName() + " usou " + super.getName() + "!");
        attacker.spendStamina(super.getStaminaCost());
        attacker.gainShield(this.damageBlocked);
        attacker.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta (nome, bloqueio e custo de fôlego).
     */
    public void printCardStats() {
        System.out.println(this.getName() + " (Bloqueio: " + this.getDamageBlocked() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna a quantidade de dano bloqueado por esta carta.
     *
     * @return pontos de escudo concedidos
     */
    public int getDamageBlocked(){
        return this.damageBlocked;
    }

    /**
     * Retorna o atributo principal da carta, que é a quantidade de bloqueio.
     *
     * @return pontos de escudo concedidos
     */
    public int getMainStat() {
        return getDamageBlocked();
    }
}