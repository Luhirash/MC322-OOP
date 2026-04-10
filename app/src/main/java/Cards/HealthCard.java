package Cards;
import Core.Turns;
import Entities.Entity;

/**
 * Carta de combate que restaura pontos de vida ao usuário instantaneamente.
 *
 * <p>Diferente de {@link HealingCard} (que aplica um efeito de recuperação ao longo
 * de vários turnos), esta carta recupera vida imediatamente ao ser jogada.
 * A vida recuperada é limitada para não ultrapassar a {@link Entity#getMaxHealth() vida máxima}
 * da entidade.</p>
 *
 * <p>Exemplos do deck do herói: "curativo" (+4 de vida), "anestésico" (+6),
 * "massagem do senhor Miyagi" (+5).</p>
 *
 * @see Card
 * @see Entity#gainHealth(int)
 * @see HealingCard
 */
public class HealthCard extends Card{

    /**
     * Quantidade máxima de vida a ser adicionada ao usuário ao jogar esta carta.
     * <p>O valor real recuperado pode ser menor se o usuário estiver próximo da vida máxima.</p>
     */
    private int healthAdded;

    /**
     * Constrói uma carta de vida.
     *
     * @param name         nome da carta exibido nas mensagens de combate
     * @param staminaCost  custo em fôlego para usá-la
     * @param healthAdded  quantidade máxima de vida a ser recuperada
     * @param description  descrição textual da ação de cura
     */
    public HealthCard(String name, int staminaCost, int healthAdded, String description){
        super(name, staminaCost, description);
        this.healthAdded = healthAdded;
    }
    
    /**
     * Executa a cura: gasta o fôlego do atacante e recupera vida imediatamente.
     *
     * <p>A lógica de cálculo da vida recuperada é:</p>
     * <ul>
     *   <li>Se a diferença entre vida máxima e vida atual for negativa (situação inesperada),
     *       recupera essa diferença (resultado seria zero ou negativo — comportamento defensivo).</li>
     *   <li>Caso contrário, recupera exatamente o valor de {@link #healthAdded}.</li>
     * </ul>
     * <p><b>Nota:</b> A verificação de limite máximo (para não ultrapassar {@code maxHealth})
     * é responsabilidade de quem consome este método. A lógica atual pode precisar de
     * ajuste para garantir que a vida não ultrapasse o máximo em todos os cenários.</p>
     *
     * @param attacker entidade que usa a carta e recebe a cura
     * @param receiver entidade-alvo (não utilizada — a cura é sempre para o atacante)
     * @param turns    referência ao gerenciador de turnos (não utilizado aqui)
     */
    public void useCard (Entity attacker, Entity receiver, Turns turns) {
        attacker.spendStamina(super.getStaminaCost());
        int totalHealthAdded = 0;
        if (attacker.getMaxHealth() - attacker.getHealth() < 0)
            totalHealthAdded = attacker.getMaxHealth() - attacker.getHealth();
        else
            totalHealthAdded = getHealthAdded();
        attacker.gainHealth(totalHealthAdded);
        System.out.println(attacker.getName() + " usou " + this.getName() + " e recebeu " + totalHealthAdded+ " de vida a mais!");
        attacker.printStats();
    }

    /**
     * Imprime no console as estatísticas da carta no formato:
     * <pre>Usar NomeDaCarta (Vida adicionada: X | Custo: Y)</pre>
     */
    public void printCardStats() {
        System.out.println("Usar " + this.getName() + " (Vida adicionada: " + this.getHealthAdded() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna a quantidade de vida adicionada por esta carta.
     *
     * @return vida máxima a ser recuperada
     */
    public int getHealthAdded(){
        return this.healthAdded;
    }

    /**
     * Retorna o atributo principal da carta, que é a quantidade de vida adicionada.
     *
     * @return vida máxima a ser recuperada (equivalente a {@link #getHealthAdded()})
     */
    public int getMainStat() {
        return getHealthAdded();
    }
}