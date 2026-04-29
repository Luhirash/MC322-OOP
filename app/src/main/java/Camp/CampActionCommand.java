package Camp;

import Entities.Hero;

/**
 * Interface Command para as ações disponíveis no Camp de Treinamento.
 *
 * <p>Seguindo o padrão de projeto <em>Command</em> (Refactoring Guru), esta interface
 * encapsula uma ação como um objeto autônomo, separando completamente:</p>
 * <ul>
 *   <li>O <b>Invoker</b> ({@link Events.TrainingCamp}) — que solicita a execução.</li>
 *   <li>O <b>Receiver</b> ({@link Entities.Hero}) — que sofre o efeito da ação.</li>
 *   <li>O <b>Command concreto</b> — que implementa esta interface e sabe exatamente
 *       como operar sobre o receiver.</li>
 * </ul>
 *
 * <p>Para adicionar uma nova ação ao Camp, basta criar uma nova classe que implemente
 * {@code CampActionCommand} e adicioná-la à lista do {@link Events.TrainingCamp},
 * sem modificar nenhuma classe existente (princípio Aberto/Fechado do SOLID).</p>
 *
 * <p>Implementações concretas disponíveis:</p>
 * <ul>
 *   <li>{@link IceBathCommand} — Recupera 30% da vida máxima do herói.</li>
 *   <li>{@link SparringCommand} — Melhora (upgrade) uma carta do baralho do herói.</li>
 * </ul>
 *
 * @see IceBathCommand
 * @see SparringCommand
 * @see Events.TrainingCamp
 */
public interface CampActionCommand {

    /**
     * Retorna o nome curto da ação exibido no menu do Camp.
     *
     * @return nome da ação
     */
    String getName();

    /**
     * Retorna a descrição detalhada do efeito da ação.
     *
     * @return descrição da ação
     */
    String getDescription();

    /**
     * Executa a ação sobre o herói (o <em>Receiver</em> do padrão Command).
     *
     * <p>Cada implementação concreta define aqui o comportamento específico
     * da sua ação — cura, upgrade de carta, etc.</p>
     *
     * @param hero o herói que será afetado pela ação
     */
    void execute(Hero hero);
}