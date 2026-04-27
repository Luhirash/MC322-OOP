package Relics;

import Entities.Hero;

/**
 * Relíquia: Bandagens de Luta do Spider.
 *
 * <p>Estratégia concreta que implementa {@link RelicStrategy}. Estas bandagens
 * especiais aumentam a resistência do herói: a cada início de turno durante
 * uma batalha, o herói recupera um ponto de vida, refletindo o condicionamento
 * físico superior de Anderson Silva.</p>
 *
 * <p>Temática: As bandagens de luta protegem os punhos e pulsos, mas as lendárias
 * bandagens do Spider vão além — elas parecem canalizar a energia do lutador,
 * devolvendo-lhe fôlego a cada round.</p>
 *
 * <ul>
 *   <li><b>onBattleStart</b>: Exibe mensagem motivacional, sem efeito numérico.</li>
 *   <li><b>onTurnStart</b>: Recupera {@value #HEAL_PER_TURN} ponto(s) de vida.</li>
 * </ul>
 *
 * @see RelicStrategy
 */
public class HandWrapStrategy implements RelicStrategy {

    /** Quantidade de vida restaurada no início de cada turno. */
    private static final int HEAL_PER_TURN = 2;

    private static final String NAME = "Bandagens do Spider";
    private static final String DESCRIPTION =
            "As bandagens lendárias do Spider. Recupera " + HEAL_PER_TURN +
            " ponto(s) de vida no início de cada turno — resiste até o último round.";
    private static final int COST = 6;


    public void onBattleStart(Hero hero) {
        System.out.println("🩹 [" + NAME + "] Anderson Silva enrola as bandagens com calma " +
                "— nada vai quebrá-las hoje.");
    }

    /**
     * Recupera {@value #HEAL_PER_TURN} ponto(s) de vida no início do turno do herói.
     *
     * @param hero o herói que recupera vida
     */
    public void onTurnStart(Hero hero) {
        hero.gainHealth(HEAL_PER_TURN);
        System.out.println("🩹 [" + NAME + "] As bandagens pulsam — +" +
                HEAL_PER_TURN + " de vida recuperado!");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getCost() {
        return COST;
    }
}