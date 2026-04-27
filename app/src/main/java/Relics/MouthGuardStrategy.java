package Relics;

import Entities.Hero;

/**
 * Relíquia: Protetor Bucal de Anderson Silva.
 *
 * <p>Estratégia concreta que implementa {@link RelicStrategy}. Ao iniciar
 * cada batalha, concede um escudo inicial ao herói, absorvendo parte
 * do dano do primeiro ataque recebido.</p>
 *
 * <p>Temática: Todo lutador de MMA usa protetor bucal — este em especial
 * foi moldado com borracha importada e protege como nenhum outro.</p>
 *
 * <ul>
 *   <li><b>onBattleStart</b>: Aplica {@value #SHIELD_AMOUNT} pontos de escudo.</li>
 *   <li><b>onTurnStart</b>: Sem efeito nesta relíquia.</li>
 * </ul>
 *
 * @see RelicStrategy
 */
public class MouthGuardStrategy implements RelicStrategy {

    private static final int SHIELD_AMOUNT = 5;

    private static final String NAME = "Protetor Bucal de Campeão";
    private static final String DESCRIPTION =
            "Forjado em borracha de alta densidade. Concede " + SHIELD_AMOUNT +
            " de escudo no início de cada batalha, absorvendo o primeiro golpe.";
    private static final int COST = 4;

    /**
     * Aplica escudo ao herói ao início de cada batalha.
     *
     * @param hero o herói que recebe o escudo
     */
    @Override
    public void onBattleStart(Hero hero) {
        hero.gainShield(SHIELD_AMOUNT);
        System.out.println("🥊 [" + NAME + "] Anderson Silva ajusta o protetor bucal — +" +
                SHIELD_AMOUNT + " de escudo antes do gongo!");
    }


    public void onTurnStart(Hero hero) {//essa reliquia nao tem efeito por turno
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