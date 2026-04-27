package Relics;

import Entities.Hero;


public class EnhancedGlovesStrategy implements RelicStrategy {

    private static final int SHIELD_AMOUNT = 3;

    private static final String NAME = "Luvas Aprimoradas";
    private static final String DESCRIPTION =
            "Couro de microfibra estufado com crina de cavalo. Concede " + SHIELD_AMOUNT +
            " de escudo no início de cada turno, aumentando a absorção dos impactos recebidos.";
    private static final int COST = 10;

    /**
     * Aplica escudo ao herói ao início de cada batalha.
     *
     * @param hero o herói que recebe o escudo
     */
    @Override
    public void onBattleStart(Hero hero) {
        hero.gainShield(SHIELD_AMOUNT);
        System.out.println("🥊 [" + NAME + "] Anderson Silva aperta suas luvas e recebe — +" +
                SHIELD_AMOUNT + " de escudo antes do gongo!");
    }


    public void onTurnStart(Hero hero) {
        hero.gainHealth(SHIELD_AMOUNT);
        System.out.println("🩹 [" + NAME + "] As luvas se deformam a cada golpe recebido — +" +
                SHIELD_AMOUNT + " de bloqueio para os próximos ataques recebidos!");
    }
    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public int getCost() {
        return COST;
    }
}