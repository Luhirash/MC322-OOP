package Events;

import Entities.Hero;

/**
 * Classe base abstrata para todos os eventos da jornada do herói.
 *
 * <p>Um evento representa uma parada ou nó no caminho do jogador (como no estilo
 * clássico de jogos Roguelike Deckbuilders). As subclasses definem o que ocorre
 * nessa etapa específica do mapa.</p>
 *
 * <p>Tipos principais de eventos implementados:</p>
 * <ul>
 * <li>{@link Battle}: Combate contra um inimigo específico.</li>
 * <li>{@link Shop}: Loja para compra de relíquias e melhorias passivas.</li>
 * <li>{@link TrainingCamp}: Área de descanso e upgrade de cartas.</li>
 * <li>{@link Choice}: Evento narrativo com decisões que geram consequências mecânicas.</li>
 * </ul>
 *
 * @see EventNode
 * @see EventTree
 */
public abstract class Event {
    
    /**
     * Inicia a execução do evento, interagindo com o jogador e o herói.
     *
     * <p>Cada subclasse implementa sua própria lógica de interface, loop de combate,
     * compras ou interações textuais de escolhas dentro deste método.</p>
     *
     * @param hero o herói do jogador que participará do evento
     */
    public abstract void startEvent(Hero hero);

    /**
     * Retorna o nome identificador do evento.
     *
     * @return nome do evento (ex: "Batalha", "Loja do Octógono")
     */
    public abstract String getName();

    /**
     * Retorna a descrição detalhada ou contexto do evento atual.
     *
     * @return texto descritivo do evento (ex: o nome do inimigo a ser enfrentado)
     */
    public abstract String getDescription();
}