package Events;
import Entities.*;
import Possibilities.CardPossibility;
import Possibilities.CoinPossibility;
import Possibilities.HealthPossibility;

import javax.swing.tree.DefaultMutableTreeNode;

import Cards.*;

import Events.Shop;

import java.util.Scanner;

public class EventTree extends DefaultMutableTreeNode {

    Scanner scanner;
    private Event[] events;
    public EventTree(Scanner scanner) {
        this.scanner = scanner;
        this.events = new Event[] {
            new Battle(new KennethAllen("Kenneth Allen", 22 , 9, 5), scanner),
            new Choice("Bater o dedo na quina do ringue e cair no chão", 
                        new CardPossibility("Perceber a utilidade de se agachar", new ShieldCard("agachar", 2, 4, "busca se esconder do inimigo rapidamente")), 
                        new HealthPossibility("Se entregar à dor", -1),
                        new CoinPossibility("Observar atentamente o seu entorno", 3), scanner),
            new Shop(scanner),
            new Battle(new FrancisNgannou("Francis Ngannou", 40, 8, 8), scanner),
            new Battle(new MaxHolloway("Max Holloway", 28, 11, 7), scanner),
            new Choice("Ir para a balada", 
                        new CardPossibility("Encontra um barman estranho, que te oferece a receita de um suco secreto.", new HealingCard("beber suco secreto", 2, 3, "bebe seu super suco que confere 3 de intensidade de cura")), 
                        new HealthPossibility("Ficar acordado até de madrugada", -2),
                        new CoinPossibility("Pagar uma rodada de drinks para a galera", -4), scanner),
            new Shop(scanner),
            new Battle(new KhabibNurmagomedov("Khabib Nurmagomedov", 35, 10, 11), scanner),
            new Battle(new IsraelAdesanya("Israel Adesanya", 24, 12, 9), scanner),
            new Choice("Treinar até o sol raiar", 
                        new CardPossibility("O cansaço te faz adquirir movimentos imperfeitos", new DamageCard("Chute nas costas", 1, 6, "um movimento fraco e desagastante")), 
                        new HealthPossibility("Dormir o dia seguinte inteiro", 3),
                        new CoinPossibility("Gravar um vídeo do treino e postar no tiktok", 6), scanner),
            new Battle(new JonJones( "Jon Jones", 42, 11, 13), scanner),
            new Battle(new ConnnorMcGregor("Connor McGregor", 30, 14, 11), scanner)
        };
    }

    private EventNode[] transformEvents() {
        EventNode[] nodes = new EventNode[events.length];
        for (int i = 0; i < events.length; i++)
            nodes[i] = new EventNode(events[i]);
        return nodes;
    }

    public EventNode createTree() {
        EventNode[] nodes = transformEvents();
        nodes[0].add(nodes[1]); //Kenneth -> bater o dedo
        nodes[0].add(nodes[3]); //Kenneth -> MaxHolloway
        nodes[1].add(nodes[2]); //bater o dedo -> FrancisNgannou
        nodes[1].add(nodes[5]); //Bater o dedo -> KhabibNurgmagomedov
        nodes[3].add(nodes[4]); //MaxHolloway -> Balada
        nodes[2].add(nodes[6]); //FrancisNgannou -> IsraelAdesanya
        nodes[4].add(nodes[8]); //Balada -> JonJones
        nodes[5].add(nodes[7]); //KhabibNurgmagomedov -> Treinar até o sol raiar
        nodes[7].add(nodes[9]); //Treinar até o Sol raiar -> ConnorMcGregor

        return nodes[0];
    }

}
