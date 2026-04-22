package Core;

import java.util.ArrayList;

import Effects.Effect;
import Entities.Entity; // Import adicionado para o Javadoc reconhecer as referências à Entidade

/**
 * Gerenciador central de eventos e efeitos de status durante o combate.
 *
 * <p>Implementa o papel de <em>Publisher</em> no padrão Observer: mantém uma lista
 * de {@link Effect efeitos} inscritos e os notifica quando o evento de turno muda.
 * Cada efeito decide, em {@link Effect#beNotified(GameManager)}, se deve agir
 * com base no valor de {@link #currentEvent}.</p>
 *
 * <p>O ciclo de vida de um efeito dentro do {@code GameManager} é:</p>
 * <ol>
 *   <li>Criado por uma carta e entregue a {@link #subscribe(Effect)}.</li>
 *   <li>Notificado a cada mudança de evento via {@link #notifyEvent()}.</li>
 *   <li>Removido automaticamente por {@link #notifyEvent()} quando
 *       {@link Effect#isExpired()} retorna {@code true}.</li>
 *   <li>Todos os inscritos são removidos ao fim de cada batalha via
 *       {@link #unsubscribeAll()}.</li>
 * </ol>
 *
 * @see Effect
 * @see Publisher
 * @see Turns
 */
public class GameManager implements Publisher{

    /**
     * Enumeração dos momentos do combate que disparam ações de efeitos de status.
     *
     * <ul>
     * <li>{@code HEROSTART}  – início do turno do herói.</li>
     * <li>{@code HEROFINISH} – fim do turno do herói.</li>
     * <li>{@code ENEMYSTART}  – início do turno do inimigo.</li>
     * <li>{@code ENEMYFINISH} – fim do turno do inimigo.</li>
     * </ul>
     */
    public enum Events {
        HEROSTART, HEROFINISH, ENEMYSTART, ENEMYFINISH
    }

    /**
     * Evento de turno atualmente em execução.
     * <p>Definido por {@link Turns} antes de cada chamada a {@link #notifyEvent()}.
     * Consultado por cada {@link Effect#beNotified(GameManager)} para decidir se deve agir.</p>
     */
    public Events currentEvent = Events.HEROSTART;

    /**
     * Lista de efeitos de status inscritos para receber notificações de eventos.
     * <p>Gerenciada por {@link #subscribe(Effect)}, {@link #unsubscribe(Effect)}
     * e {@link #unsubscribeAll()}.</p>
     */
    private ArrayList<Effect> subscriberList = new ArrayList<Effect>();

    /**
     * Inscreve um efeito para receber notificações de eventos e o aplica ao seu dono.
     *
     * <p>Se o efeito ainda não estiver ativo na entidade (verificado por
     * {@link Effect#getIndex(ArrayList)}), ele é adicionado à lista de inscritos.
     * Em seguida, {@link Entities.Entity#applyEffect(Effect)} é sempre chamado,
     * acumulando intensidade caso o efeito já exista no dono.</p>
     *
     * @param effect efeito a ser inscrito e aplicado
     * @see Entities.Entity#applyEffect(Effect)
     */
    public void subscribe(Effect effect) {
        if (effect.getIndex(effect.getOwner().getEffects()) == -1)
            subscriberList.add(effect); // Se o efeito ainda não existe (não está aplicado no dono), ele é adicionado
        effect.getOwner().applyEffect(effect); // De qualquer maneira, é aplicado no dono
    }

    /**
     * Remove um efeito da lista de inscritos, cancelando suas futuras notificações.
     *
     * <p>Chamado automaticamente por {@link #notifyEvent()} quando a intensidade
     * do efeito chega a zero, ou manualmente quando necessário.</p>
     *
     * @param effect efeito a ser removido da lista de inscritos
     */
    public void unsubscribe(Effect effect) {
        int idx = effect.getIndex(subscriberList);
        if (idx != -1)
            subscriberList.remove(idx); // remove o efeito da lista (se tiver o mesmo nome e dono)
    }

    /**
     * Remove todos os efeitos da lista de inscritos de uma só vez.
     *
     * <p>Chamado ao final de cada {@link Battle} para evitar que efeitos residuais
     * de uma luta se propaguem para a próxima.</p>
     */
    public void unsubscribeAll() {
        subscriberList.clear();
    }

    /**
     * Notifica todos os efeitos inscritos sobre o evento de turno atual e remove
     * os que expiraram.
     *
     * <p>A notificação ocorre sobre uma cópia da lista para evitar
     * {@link java.util.ConcurrentModificationException} caso algum efeito se
     * desinscreva durante a iteração (ex: sangramento que expira no último dano).</p>
     *
     * <p>Após notificar, percorre a lista original de trás para frente e remove
     * os efeitos cuja intensidade chegou a zero ({@link Effect#isExpired()}),
     * limpando-os também da lista de efeitos do dono.</p>
     */
    public void notifyEvent() {
        // Itera sobre cópia para evitar ConcurrentModificationException
        // caso um efeito se desinscreva durante a notificação
        ArrayList<Effect> copy = new ArrayList<Effect>(subscriberList);
        for (Effect effect : copy)
            effect.beNotified(this);

        // Varredura reversa para remover com segurança os efeitos expirados
        for (int i = subscriberList.size() - 1; i >= 0; i--) {
            Effect effect = subscriberList.get(i);
            if (effect.isExpired()) {
                ArrayList<Effect> ownerEffects = effect.getOwner().getEffects();
                ownerEffects.remove(effect.getIndex(ownerEffects));
                this.unsubscribe(effect);
            }
        }
    }
}