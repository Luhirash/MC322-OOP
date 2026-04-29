package Events;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Scanner;

public class EventNode extends DefaultMutableTreeNode{
    
    private Event event;

    public EventNode(Event event) {
        this.event = event;
    }

    public Event[] nextEvents() {
        Event[] nextEvents = new Event[getChildCount()];
        for (int i = 0; i < getChildCount(); i++)
            nextEvents[i] = ((EventNode) getChildAt(i)).getEvent();
        return nextEvents;
    }

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

    public Event getEvent() {
        return this.event;
    }
}
