package Core;

import java.util.ArrayList;

import Effects.Effect;

public class GameManager implements Publisher{

        /**
     * Enumeração dos momentos do combate que disparam ações de efeitos de status.
     *
     * <ul>
     *   <li>{@code HEROSTART}  – início do turno do herói.</li>
     *   <li>{@code HEROFINISH} – fim do turno do herói.</li>
     *   <li>{@code ENEMYSTART}  – início do turno do inimigo.</li>
     *   <li>{@code ENEMYFINISH} – fim do turno do inimigo.</li>
     * </ul>
     */
    public enum Events {
        HEROSTART, HEROFINISH, ENEMYSTART, ENEMYFINISH
    }

    /**
     * Evento de turno atualmente em execução.
     * <p>Consultado por cada {@link Effect#beNotified(Turns)} para decidir se deve agir.</p>
     */
    public Events currentEvent = Events.HEROSTART;

    /**
     * Lista de efeitos de status inscritos para receber notificações de eventos.
     * <p>Gerenciada por {@link #subscribe(Effect)} e {@link #unsubscribe(Effect)}.</p>
     */
    private ArrayList<Effect> subscriberList = new ArrayList<Effect>();

    /**
     * Inscreve um efeito para receber notificações de eventos de turno e o aplica ao dono.
     *
     * <p>Se o efeito ainda não estiver ativo na entidade (verificado por
     * {@link Effect#getIndex(ArrayList)}), ele é adicionado à lista de inscritos.
     * Independentemente disso, {@link Entity#applyEffect(Effect)} é sempre chamado,
     * o que acumula a intensidade caso o efeito já exista.</p>
     *
     * @param effect efeito a ser inscrito e aplicado
     * @see Entity#applyEffect(Effect)
     */

    public void subscribe(Effect effect) {
        if (effect.getIndex(effect.getOwner().getEffects()) == -1)
            subscriberList.add(effect); //Se o efeito ainda não existe (não está aplicado no dono), ele é adicionado
        effect.getOwner().applyEffect(effect); //De qualquer maneira, é aplicado no dono
    }

    /**
     * Remove um efeito da lista de inscritos, cancelando suas futuras notificações.
     * <p>Chamado automaticamente por {@link GameManager#notifyEvent()} quando
     * a intensidade do efeito chega a zero.</p>
     *
     * @param effect efeito a ser removido da lista de inscritos
     */
    public void unsubscribe(Effect effect) {
        int idx = effect.getIndex(subscriberList);
        if (idx != -1)
            subscriberList.remove(idx); //remove o efeito da lista (se tiver o mesmo nome e dono)
    }
    
    /**
     * Notifica todos os efeitos inscritos sobre a mudança no evento de turno atual.
     *
     * <p>Itera sobre uma cópia da lista de inscritos para evitar
     * {@link java.util.ConcurrentModificationException} caso algum efeito se desinscreva
     * durante a notificação (ex: sangramento que expira após o último dano).</p>
     * 
     * <p>Remove os efeitos que chegam à intensidade 0.</p>
     */
    public void notifyEvent() {
        // Itera sobre cópia para evitar ConcurrentModificationException
        // caso um efeito se desinscreva durante a notificação
        ArrayList<Effect> copy = new ArrayList<Effect>(subscriberList);
        for (Effect effect : copy)
            effect.beNotified(this);

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
