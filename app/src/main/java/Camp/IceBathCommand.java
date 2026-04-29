package Camp;

import Entities.Hero;

/**
 * Comando Concreto: Banheira de Gelo.
 *
 * <p>Implementação de {@link CampActionCommand} que representa a recuperação do herói
 * por imersão em água gelada — técnica amplamente utilizada por atletas de alto rendimento
 * para acelerar a recuperação muscular entre treinos e lutas.</p>
 *
 * <p>No padrão <em>Command</em>, esta classe é o <b>Concrete Command</b>:</p>
 * <ul>
 *   <li>Sabe <em>o que</em> fazer (curar o herói).</li>
 *   <li>Sabe <em>como</em> fazer (calcular 30% da vida máxima).</li>
 *   <li>Não sabe <em>quando</em> será executado — isso é responsabilidade do
 *       Invoker ({@link Events.TrainingCamp}).</li>
 * </ul>
 *
 * <p><b>Efeito:</b> Recupera {@value #HEAL_PERCENTAGE}% da vida máxima do herói,
 * sem ultrapassar o limite máximo de vida.</p>
 *
 * @see CampActionCommand
 * @see Events.TrainingCamp
 */
public class IceBathCommand implements CampActionCommand {

    /** Percentual da vida máxima recuperado ao usar a banheira de gelo. */
    private static final double HEAL_PERCENTAGE = 0.30;

    private static final String NAME = "Banheira de Gelo";
    private static final String DESCRIPTION =
            "Anderson Silva mergulha na banheira de gelo da academia. " +
            "Recupera 30% da vida máxima — o frio dói, mas o Spider não reclama.";

    /**
     * Constrói o comando de banheira de gelo.
     * Não requer parâmetros pois toda a lógica é determinística.
     */
    public IceBathCommand() {
        // Sem dependências externas — a lógica é puramente sobre o Hero.
    }

    /**
     * Executa a recuperação de vida do herói.
     *
     * <p>Calcula 30% da vida máxima e aplica a cura via {@link Hero#gainHealth(int)},
     * que internamente já garante que a vida não ultrapasse o máximo permitido.</p>
     *
     * @param hero o herói que se recupera na banheira de gelo
     */
    @Override
    public void execute(Hero hero) {
        int healAmount = (int) Math.floor(hero.getMaxHealth() * HEAL_PERCENTAGE);

        System.out.println("\n🧊 Anderson Silva entra na banheira de gelo...");
        System.out.println("   Os músculos contraem, a respiração para por um segundo.");
        System.out.println("   Mas o Spider conhece a dor. Ele fica quieto. Ele descansa.");

        int actualHeal = hero.gainHealth(healAmount);

        System.out.println("\n✅ Recuperação concluída!");
        System.out.printf("   +%d de vida restaurada (30%% de %d de vida máxima).%n",
                actualHeal, hero.getMaxHealth());
        System.out.println("   Anderson Silva sai da banheira mais leve. Pronto pra guerra.\n");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}