package Core;

/**
 * Interface do padrão Observer para efeitos de status que reagem a eventos de turno.
 *
 * <p>Implementada por {@link Effects.Effect} e suas subclasses. Cada implementação
 * define em {@link #beNotified(GameManager)} qual evento de turno a dispara e
 * qual ação executar (ex: aplicar dano de sangramento, recuperar vida, etc.).</p>
 *
 * <p>O {@link GameManager} atua como <em>Publisher</em>: chama {@link #beNotified}
 * em todos os inscritos sempre que o evento de turno muda.</p>
 *
 * @see GameManager
 * @see Publisher
 * @see Effects.Effect
 */
public interface Observer {

    /**
     * Chamado pelo {@link GameManager} quando um evento de turno ocorre.
     *
     * <p>A implementação deve consultar {@link GameManager#currentEvent} para
     * decidir se deve agir e, em caso positivo, executar o efeito correspondente
     * (dano, cura, bônus de força, etc.).</p>
     *
     * @param gameManager o gerenciador que disparou a notificação, fornecendo
     *                    acesso ao evento atual via {@link GameManager#currentEvent}
     */
    public void beNotified(GameManager gameManager);
}