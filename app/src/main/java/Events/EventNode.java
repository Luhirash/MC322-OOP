package Events;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Scanner;

/**
 * Representa um nó na árvore de progressão do mapa do jogo.
 *
 * <p>A progressão do herói é modelada como um grafo/árvore direcionada. Cada nó
 * ({@code EventNode}) contém o {@link Event} atual que está sendo jogado, além
 * de ramificações (filhos) que determinam os próximos caminhos que o jogador
 * poderá escolher após concluir este evento.</p>
 *
 * <p>Herdando as facilidades da classe <code>DefaultMutableTreeNode</code> nativa
 * do Java, esta classe gerencia a relação "pai e filhos" das rotas do jogo.</p>
 *
 * @see EventTree
 * @see Event
 */
public class EventNode extends DefaultMutableTreeNode {
    
    /** O evento encapsulado e executado neste momento da jornada. */
    private Event event;

    /**
     * Constrói um nó da árvore de progressão.
     *
     * @param event o evento correspondente a este ponto do mapa
     */
    public EventNode(Event event) {
        this.event = event;
    }

    /**
     * Retorna a lista dos eventos contidos nos nós filhos.
     *
     * <p>Utilizado para apresentar as rotas futuras ao jogador antes de pedir
     * a sua decisão de movimentação no mapa.</p>
     *
     * @return um array contendo os eventos adjacentes disponíveis para o próximo passo
     */
    public Event[] nextEvents() {
        Event[] nextEvents = new Event[getChildCount()];
        for (int i = 0; i < getChildCount(); i++)
            nextEvents[i] = ((EventNode) getChildAt(i)).getEvent();
        return nextEvents;
    }

    /**
     * Exibe os caminhos futuros e processa a decisão de avanço no mapa do jogador.
     *
     * <p>Lê as conexões cadastradas nos nós filhos, imprime-as com identificação
     * clara (se for Batalha, mostra o nome do inimigo), aguarda o input validado
     * via terminal, e retorna o nó subsequente selecionado.</p>
     *
     * @param scanner ferramenta de leitura da resposta do usuário
     * @return o {@link EventNode} filho correspondente ao caminho escolhido
     */
    public EventNode chooseNextEvent(Scanner scanner) {
        Event[] nextEvents = nextEvents();
        System.out.println("Escolha seu próximo evento:");
        for (int i = 0; i < nextEvents.length; i++) {
            System.out.print(i + 1 + " - ");
            if (nextEvents[i] instanceof Battle)
                System.out.println(nextEvents[i].getName() + " contra " + ((Battle)nextEvents[i]).getEnemyName());
            else
                System.out.println(nextEvents[i].getDescription());
        }

        int choice = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Escolha sua ação: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt(); // Lê apenas UMA vez
                scanner.nextLine(); 
                
                if (choice > 0 && choice <= nextEvents.length) {
                    entradaValida = true; 
                } else {
                    System.out.println("Erro: Digite um número entre 1 e " + nextEvents.length);
                }
            } else {
                System.out.println("Erro: Isso não é um número inteiro!");
                scanner.next(); // Descarta a entrada de texto inválida
            }
        }

        return (EventNode) getChildAt(choice - 1);
    }

    /**
     * Retorna o evento hospedado por este nó.
     *
     * @return a instância do evento (Batalha, Loja, etc.)
     */
    public Event getEvent() {
        return this.event;
    }
}