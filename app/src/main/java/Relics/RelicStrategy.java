package Relics;

import Entities.Hero;

/**
 * Interface Strategy para as relíquias (itens passivos) do lutador.
 *
 * <p>Cada implementação concreta representa um equipamento de MMA que confere
 * bônus passivos ao herói em momentos específicos do jogo. Seguindo o padrão
 * <em>Strategy</em>, cada relíquia encapsula seu próprio comportamento,
 * permitindo adicionar novos itens sem modificar classes existentes.</p>
 *
 * <p>Os métodos "gancho" (hooks) são chamados automaticamente pelo {@link Hero}
 * nos momentos adequados do fluxo de jogo:</p>
 * <ul>
 *   <li>{@link #onBattleStart(Hero)} — invocado uma vez ao iniciar cada batalha.</li>
 *   <li>{@link #onTurnStart(Hero)} — invocado no início de cada turno do herói.</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * RelicStrategy bucal = new MouthGuardStrategy();
 * hero.addRelic(bucal);
 * // Em algum ponto do loop de batalha:
 * hero.triggerBattleStartRelics();
 * }</pre>
 *
 * @see MouthGuardStrategy
 * @see HandWrapStrategy
 * @see Entities.Hero
 */
public interface RelicStrategy {

    /**
     * Hook chamado uma única vez quando uma nova batalha se inicia,
     * antes do primeiro turno do herói.
     *
     * @param hero o herói que possui esta relíquia
     */
    void onBattleStart(Hero hero);

    /**
     * Hook chamado no início de cada turno do herói durante uma batalha.
     *
     * @param hero o herói que possui esta relíquia
     */
    void onTurnStart(Hero hero);

    /**
     * Retorna o nome da relíquia exibido na loja e no inventário.
     *
     * @return nome da relíquia
     */
    String getName();

    /**
     * Retorna a descrição do efeito passivo da relíquia.
     *
     * @return descrição da relíquia
     */
    String getDescription();

    /**
     * Retorna o custo em moedas para adquirir esta relíquia na loja.
     *
     * @return preço em moedas
     */
    int getCost();
}