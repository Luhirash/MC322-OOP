/**
 * Carta de combate que aumenta a vida de uma entidade.
 * <p>
 * A vida total acrescida é calculada pelo máximo de vida que pode ser adicionada, 
 * sem ultrapassar a vida máxima da entidade
 * </p>
 *
 * @see Card
 * @see Entity
 */
public class HealthCard extends Card{

    /** Vida adicionada à entidade */
    private int healthAdded;

    /**
     * Constrói uma carta de vida.
     *
     * @param name             nome da carta
     * @param staminaCost      custo em fôlego para usá-la
     * @param healthAdded  vida base adicionada à entidade alvo
     * @param description      descrição textual do golpe
     */
    public HealthCard(String name, int staminaCost, int healthAdded, String description){
        super(name, staminaCost, description);
        this.healthAdded = healthAdded;
    }
    
    /**
     * Executa o golpe: gasta fôlego da entidade, aumentando sua vida
     *
     * @param attacker entidade que usa a carta e recebe a vida
     * @param receiver (não utilizado diretamente aqui)
     * @param turns    referência ao gerenciador de turnos (não utilizado diretamente aqui)
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
     * Imprime no console as estatísticas da carta (nome, vida adicionada e custo de fôlego).
     */
    public void printCardStats() {
        System.out.println("Usar " + this.getName() + " (Vida adicionada: " + this.getHealthAdded() + " | Custo: " + this.getStaminaCost() + ")");
    }

    /**
     * Retorna a vida adicionada por essa carta.
     *
     * @return retorna a vida adicionada pela carta
     */
    public int getHealthAdded(){
        return this.healthAdded;
    }

    /**
     * Retorna o atributo principal da carta, que é a vida adicionada
     *
     * @return vida adicionada
     */
    public int getMainStat() {
        return getHealthAdded();
    }
}