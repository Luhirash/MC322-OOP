package Core;

import Effects.Effect;

/**
 * Interface do padrão Observer para o papel de <em>Publisher</em> (ou <em>Subject</em>).
 *
 * <p>Implementada por {@link GameManager}, que mantém a lista de {@link Effect efeitos}
 * inscritos e os notifica a cada mudança de evento de turno.</p>
 *
 * <p>O contrato desta interface define três operações fundamentais:</p>
 * <ul>
 *   <li>{@link #subscribe(Effect)} – inscreve um efeito e o aplica ao seu dono.</li>
 *   <li>{@link #unsubscribe(Effect)} – remove um efeito expirado ou cancelado.</li>
 *   <li>{@link #notifyEvent()} – propaga o evento atual para todos os inscritos.</li>
 * </ul>
 *
 * @see Observer
 * @see GameManager
 * @see Effect
 */
public interface Publisher {

    /**
     * Inscreve um efeito de status para receber notificações de eventos de turno
     * e o aplica imediatamente ao seu dono.
     *
     * @param effect efeito a ser inscrito; não deve ser {@code null}
     */
    public void subscribe(Effect effect);

    /**
     * Remove um efeito de status da lista de inscritos, impedindo que receba
     * futuras notificações.
     *
     * <p>Geralmente chamado quando o efeito expira (intensidade chega a zero)
     * ou quando a batalha encerra.</p>
     *
     * @param effect efeito a ser removido; não produz erro se já estiver ausente
     */
    public void unsubscribe(Effect effect);

    /**
     * Notifica todos os efeitos inscritos sobre o evento de turno atual.
     *
     * <p>Cada {@link Observer} inscrito recebe a chamada e decide se deve agir
     * com base em {@link GameManager#currentEvent}. Após a notificação, efeitos
     * expirados devem ser removidos da lista.</p>
     */
    public void notifyEvent();
}