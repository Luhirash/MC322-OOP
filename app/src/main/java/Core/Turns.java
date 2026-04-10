package Core;

import java.util.Scanner;

import Cards.Card;
import Effects.Bleeding;
import Effects.Effect;
import Effects.Healing;
import Effects.Strength;
import Entities.Enemy;
import Entities.Entity;
import Entities.Hero;
import Piles.DiscardPile;
import Piles.PlayerHand;

import java.util.ArrayList;

/**
 * Gerencia o fluxo de combate e coordena o sistema de efeitos de status.
 *
 * <p>Esta classe cumpre dois papéis principais:</p>
 * <ol>
 *   <li><b>Controlador de turnos:</b> orquestra a sequência de ações em cada rodada,
 *       executando o turno do herói ({@link #HeroTurn}) e o turno do inimigo ({@link #enemyTurn}),
 *       além de auxiliar na escolha do inimigo ({@link #chooseEnemy}).</li>
 *   <li><b>Publisher (padrão Observer):</b> mantém uma lista de {@link Effect efeitos de status}
 *       inscritos e os notifica a cada mudança de evento de turno ({@link #notifyEvent}).
 *       Efeitos se inscrevem via {@link #subscribe(Effect)} e se removem via
 *       {@link #unsubscribe(Effect)} quando expiram.</li>
 * </ol>
 *
 * <h2>Sequência de eventos por rodada</h2>
 * <pre>
 * HEROSTART  → efeitos do herói ativados no início (ex: Recuperação)
 *   [herói joga suas cartas]
 * HEROFINISH → efeitos do inimigo ativados no fim do turno do herói (ex: Sangramento no inimigo)
 *
 * ENEMYSTART → efeitos do inimigo ativados no início (ex: Recuperação no inimigo)
 *   [inimigo executa suas cartas]
 * ENEMYFINISH → efeitos do herói ativados no fim do turno do inimigo (ex: Sangramento no herói)
 * </pre>
 *
 * @see Effect
 * @see Bleeding
 * @see Healing
 * @see Strength
 */
public class Turns {

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
     * <p>Chamado automaticamente por {@link Effect#effectFinish(Turns)} quando
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
     */
    public void notifyEvent() {
        // Itera sobre cópia para evitar ConcurrentModificationException
        // caso um efeito se desinscreva durante a notificação
        ArrayList<Effect> copy = new ArrayList<Effect>(subscriberList);
        for (Effect effect : copy)
            effect.beNotified(this);
    }

    /**
     * Executa a sequência de ações planejadas pelo inimigo no seu turno.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Dispara {@link Events#ENEMYSTART} e notifica efeitos.</li>
     *   <li>O inimigo executa cada carta da lista {@code chosenCards} contra o herói.</li>
     *   <li>Se o herói morrer durante o ataque, a sequência é interrompida.</li>
     *   <li>Ao final, dispara {@link Events#ENEMYFINISH} e notifica efeitos.</li>
     *   <li>Exibe o placar atualizado ({@link #printIntroduction}).</li>
     * </ol>
     *
     * @param chosenCards cartas escolhidas pelo inimigo no início da rodada
     *                    (via {@link Enemy#chooseCards(Card[])})
     * @param hero        herói que será o alvo dos ataques
     * @param enemy       inimigo que está executando o turno
     */
    public void enemyTurn(ArrayList<Card> chosenCards, Hero hero, Enemy enemy){
        currentEvent = Events.ENEMYSTART;
        notifyEvent();

        for (int i = 0; i < chosenCards.size(); i++) {
            if (hero.isAlive()) {
                chosenCards.get(i).enemyUseCard(enemy, hero, this);
                System.out.println();
            }
            else{
                System.out.println(hero.getName() + " foi derrotado!");
                break;
            }
        }      
        if (hero.isAlive()) {
            App.pause(1000);
            System.out.println(enemy.getName() + " encerrou seu turno\n");

            currentEvent = Events.ENEMYFINISH;
            notifyEvent();
            
            printIntroduction(hero, enemy);
        }

    }

    /**
     * Gerencia a interação com o jogador durante o turno do herói.
     *
     * <p>Sequência de execução:</p>
     * <ol>
     *   <li>Restaura o fôlego e o escudo do herói ({@link Entity#newTurn()}).</li>
     *   <li>Dispara {@link Events#HEROSTART} e notifica efeitos (ex: Recuperação).</li>
     *   <li>Exibe as cartas disponíveis na mão ({@link PlayerHand#printHand()}).</li>
     *   <li>Aguarda a escolha do jogador: número da carta ou opção de encerrar o turno.</li>
     *   <li>Tenta jogar a carta escolhida ({@link Card#tryCard}). Se bem-sucedida, a carta
     *       é removida da mão e enviada ao descarte ({@link DiscardPile}).</li>
     *   <li>O loop continua até que o jogador encerre o turno, o fôlego acabe,
     *       a mão fique vazia, ou um dos combatentes seja derrotado.</li>
     *   <li>Ao final, dispara {@link Events#HEROFINISH} e notifica efeitos (ex: Sangramento no inimigo).</li>
     * </ol>
     *
     * @param scanner     entrada do usuário via terminal
     * @param hero        herói controlado pelo jogador
     * @param enemy       inimigo-alvo das ações do herói
     * @param playerHand  mão atual do jogador com as cartas disponíveis
     * @param discardPile pilha de descarte que recebe as cartas jogadas
     */
    public void HeroTurn(Scanner scanner, Hero hero, Enemy enemy, PlayerHand playerHand, DiscardPile discardPile) {
        hero.newTurn();
        currentEvent = Events.HEROSTART;
        notifyEvent();

        while (currentEvent == Events.HEROSTART && hero.isAlive() && enemy.isAlive() && !playerHand.isEmpty()){

            App.pause(1000);
            //hero.printStats();

            System.out.println("\nFôlego: " + hero.getStamina() + "/" + hero.getMaxStamina());
            System.out.println("Suas cartas disponíveis:");
            playerHand.printHand();
            
            int numCards = playerHand.getHandSize();
            int exitChoice = numCards + 1;
            System.out.println(exitChoice + " - Encerrar turno");
            System.out.print("\n Escolha sua ação: ");
            int choice = scanner.nextInt();
            if(choice >= 1 && choice <= numCards){
                Card chosenCard = playerHand.getCard(choice - 1);
                if(chosenCard.tryCard(hero, enemy, this)){//se for possivel usar a carta
                    playerHand.useCard(choice - 1);
                    discardPile.addCard(chosenCard);//depois do uso a carta vai para descarte
                }
            } 
            else if(choice == exitChoice){
                System.out.println("Turno encerrado pelo jogador.");
                break;
            }
            else{
                System.out.println("Escolha inválida!");
            }

            if(hero.getStamina() <= 0){
                    App.pause(2000);
                    System.out.println("\nAcabou seu fôlego! Vez do inimigo");
                    break;
                }
            }
        if (enemy.isAlive()) {
            currentEvent = Events.HEROFINISH;
            notifyEvent();
        }
    }

    /**
     * Exibe no terminal o placar atual da luta com os status de ambos os combatentes.
     *
     * <p>Formato exibido:</p>
     * <pre>
     * ----------------------------------
     * Hero (Vida: X/MaxVida) (Bloqueio: Y)
     * VS
     * Enemy (Vida: X/MaxVida) (Bloqueio: Y) [Efeitos: ...]
     * ----------------------------------
     * </pre>
     *
     * @param hero  herói cujos status serão exibidos
     * @param enemy inimigo cujos status serão exibidos
     */
    public void printIntroduction(Hero hero, Enemy enemy) {
        App.pause(300);
        System.out.println("\n----------------------------------");
        hero.printStats();
        System.out.println("VS");
        enemy.printStats();
        System.out.println("----------------------------------\n");
        App.pause(300);
    }

    /**
     * Apresenta a lista de inimigos disponíveis e aguarda a escolha do jogador via terminal.
     *
     * <p>Exibe nome, vida máxima e fôlego máximo de cada inimigo. Repete a solicitação
     * enquanto a escolha for inválida (fora do intervalo [1, número de inimigos]).</p>
     *
     * @param enemies array com os inimigos disponíveis para escolha
     * @param scanner entrada do usuário via terminal
     * @return o inimigo escolhido pelo jogador
     */
    public Enemy chooseEnemy(Enemy[] enemies, Scanner scanner) {
        System.out.println("Escolha seu inimigo:");
        for (int i = 0; i < enemies.length; i++) {
            System.out.print(i + 1 + " - " );
            System.out.println(enemies[i].getName() + " (Vida: " + enemies[i].getMaxHealth() + ") (Fôlego: " + enemies[i].getMaxStamina() + ")");
        } 
        int choice = scanner.nextInt();
        while (choice < 1 || choice > enemies.length) {
            System.out.println("Escolha inválida! Tente novamente:");
            choice = scanner.nextInt();
        }
        return enemies[choice -1];
    }

}