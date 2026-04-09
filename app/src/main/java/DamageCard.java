/**
 * Carta de combate que causa dano direto a uma entidade.
 * <p>
 * O dano total é calculado somando o dano base da carta ao bônus de força
 * do atacante ({@link Entity#getStrengthBonus()}). Caso haja bônus de força,
 * a mensagem exibida inclui o valor adicional.
 * </p>
 *
 * @see Card
 * @see Entity#getStrengthBonus()
 */
public class DamageCard extends Card{

    /** Dano base infligido ao alvo ao usar esta carta. */
    private int damageInflicted;

    /**
     * Constrói uma carta de dano.
     *
     * @param name             nome da carta
     * @param staminaCost      custo em fôlego para usá-la
     * @param damageInflicted  dano base causado ao alvo
     * @param description      descrição textual do golpe
     */
    public DamageCard(String name, int staminaCost, int damageInflicted, String description){
        super(name, staminaCost, description);
        this.damageInflicted = damageInflicted;
    }
    
    /**
     * Executa o golpe: gasta fôlego do atacante, aplica dano ao receptor
     * e exibe a mensagem correspondente no console.
     * <p>
     * Se o atacante possuir bônus de força, o dano adicional é incluído na mensagem.
     * </p>
     *
     * @param attacker entidade que usa a carta
     * @param receiver entidade que recebe o dano
     * @param turns    referência ao gerenciador de turnos (não utilizado diretamente aqui)
     */
    public void useCard (Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        int totalDamage = damageInflicted + attacker.getStrengthBonus();
        receiver.receiveDamage(totalDamage);
        if (attacker.getStrengthBonus() > 0)
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano! (+" + attacker.getStrengthBonus() + " de Força)");
        else
            System.out.println(attacker.getName() + " usou " + this.getName() + " e causou " + totalDamage + " de dano!");
        receiver.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta (nome, dano base e custo de fôlego).
     */
    public void printCardStats() {
        System.out.println("Golpear com " + this.getName() + " (Dano: " + this.getDamageInflicted() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna o dano base desta carta.
     *
     * @return dano base infligido
     */
    public int getDamageInflicted(){
        return this.damageInflicted;
    }

    /**
     * Retorna o atributo principal da carta, que é o dano base.
     *
     * @return dano base infligido
     */
    public int getMainStat() {
        return getDamageInflicted();
    }
}