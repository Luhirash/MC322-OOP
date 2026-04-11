package Core;

import java.util.ArrayList;

import Effects.Effect;
import Entities.Entity; // Import adicionado para o Javadoc reconhecer as referências à Entidade

/**
 * Gerencia a inscrição e notificação de efeitos de status no jogo.
 * * <p>Implementa o padrão Observer (atuando como Publisher) para centralizar o controle
 * do fluxo de turnos. Garante que todos os efeitos ativos sejam notificados nos momentos 
 * corretos e gerencia a "coleta de lixo" (garbage collection), removendo automaticamente 
 * os efeitos que já expiraram.</p>
 */
public class GameManager implements Publisher {

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
     * <p>Consultado por cada {@link Effect#beNotified(GameManager)} para decidir se o efeito deve agir.</p>
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
     * o que acumula a intensidade caso o efeito já exista no dono.</p>
     *
     * @param effect efeito a ser inscrito e aplicado
     * @see Entity#applyEffect(Effect)
     */
    public void subscribe(Effect effect) {
        if (effect.getIndex(effect.getOwner().getEffects()) == -1)
            subscriberList.add(effect); // Se o efeito ainda não existe (não está aplicado no dono), ele é adicionado
        effect.getOwner().applyEffect(effect); // De qualquer maneira, é aplicado no dono
    }

    /**
     * Remove um efeito da lista de inscritos, cancelando suas futuras notificações.
     * <p>Chamado automaticamente por {@link GameManager#notifyEvent()} quando
     * a intensidade do efeito chega a zero (expira).</p>
     *
     * @param effect efeito a ser removido da lista de inscritos
     */
    public void unsubscribe(Effect effect) {
        int idx = effect.getIndex(subscriberList);
        if (idx != -1)
            subscriberList.remove(idx); // remove o efeito da lista (se tiver o mesmo nome e dono)
    }
    
    /**
     * Notifica todos os efeitos inscritos sobre a mudança no evento de turno atual.
     *
     * <p>Itera sobre uma cópia da lista de inscritos para evitar a exceção
     * {@link java.util.ConcurrentModificationException} caso ocorram alterações na lista original
     * durante a notificação.</p>
     * * <p>Após notificar os efeitos, realiza uma varredura reversa na lista. 
     * Os efeitos que chegaram à intensidade 0 (expirados) são removidos definitivamente 
     * tanto da lista de inscritos do GameManager quanto da lista pessoal do dono.</p>
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