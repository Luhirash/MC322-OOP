package Events;
import java.util.Scanner;

import Possibilities.*;
import Entities.Hero;
import Cards.Card;

/**
 * Evento narrativo onde o jogador se depara com uma encruzilhada de decisões.
 *
 * <p>Extende {@link Event} para apresentar uma situação inusitada (descrição) e
 * oferecer 3 opções pré-definidas. Cada escolha leva a uma {@link Possibility consequência}
 * mecânica diferente, como ganhar/perder vida, obter uma nova carta ou alterar o saldo
 * de moedas.</p>
 *
 * <h2>Fluxo do Evento</h2>
 * <ol>
 * <li>Apresenta o contexto narrativo e as 3 opções geradas de forma dinâmica.</li>
 * <li>Lê e valida a escolha do jogador (1, 2 ou 3) via {@link Scanner}.</li>
 * <li>Aplica a {@link Possibility} correspondente ao {@link Hero}.</li>
 * </ol>
 *
 * @see Event
 * @see Possibility
 */
public class Choice extends Event {
    
    private String description;
    private CardPossibility cardPossibility;
    private HealthPossibility healthPossibility;
    private CoinPossibility coinPossibility;
    private Scanner scanner;
    private String name;

    /**
     * Constrói um evento de escolha narrativa com as 3 opções disponíveis.
     *
     * @param description       o cenário narrativo ou situação inusitada
     * @param cardPossibility   a opção que gera o ganho de uma carta
     * @param healthPossibility a opção que altera a vida (positiva ou negativamente)
     * @param coinPossibility   a opção que altera as moedas (positiva ou negativamente)
     * @param scanner           scanner para leitura da entrada do jogador
     */
    public Choice(String description, CardPossibility cardPossibility, HealthPossibility healthPossibility, CoinPossibility coinPossibility, Scanner scanner) {
        this.description = description;
        this.healthPossibility = healthPossibility;
        this.cardPossibility = cardPossibility;
        this.coinPossibility = coinPossibility;
        this.scanner = scanner;
        this.name = "Escolha";
    }

    /**
     * Inicia o evento de múltipla escolha. Apresenta o cenário, recolhe a escolha e aplica o resultado.
     *
     * @param hero o herói do jogador
     */
    @Override
    public void startEvent(Hero hero) {
        introduceChoice();
        int choice = getChoice();
        usePossibility(hero, choice);
    }

    /**
     * Apresenta o contexto do evento e as opções no terminal, indicando 
     * explicitamente qual será o custo/benefício mecânico de cada uma.
     */
    private void introduceChoice() {
        System.out.println(description);
        System.out.println("    Qual você escolherá?");
        System.out.println("1 - " + cardPossibility.getDescription() + ". Assim, adicionar " + cardPossibility.getCard().getName() + " ao seu baralho");
        if (healthPossibility.getHealthChange() > 0) System.out.println("2 - " + healthPossibility.getDescription() + " Assim, recuperar " + healthPossibility.getHealthChange() + " de vida.");
        else System.out.println("2 - " + healthPossibility.getDescription() + ". Assim, perder " + -healthPossibility.getHealthChange() + " de vida.");
        if (coinPossibility.getCoins() > 0) System.out.println("3 - " + coinPossibility.getDescription() + ". Assim, ganhar " + coinPossibility.getCoins() + " moedas");
        else System.out.println("3 - " + coinPossibility.getDescription() + ". Assim, perder " + -coinPossibility.getCoins() + " moedas");
    }

    /**
     * Lê a entrada do jogador de forma segura, garantindo a seleção de uma opção válida.
     *
     * @return um inteiro de 1 a 3 representando a escolha do jogador
     */
    private int getChoice() {
        int choice = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Escolha sua ação: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt(); 
                scanner.nextLine(); 
                
                if (choice > 0 && choice <= 3) {
                    entradaValida = true; 
                } else {
                    System.out.println("Erro: Digite um número entre 1 e 3");
                }
            } else {
                System.out.println("Erro: Isso não é um número inteiro!");
                scanner.next(); 
            }
        }
        return choice;
    }

    /**
     * Processa a consequência mecânica da opção escolhida, atualizando o herói.
     *
     * @param hero   o herói que será afetado
     * @param choice o índice da opção escolhida (1, 2 ou 3)
     */
    private void usePossibility(Hero hero, int choice) {
        if (choice == 1) {
            Card newCard = cardPossibility.getCard(); 
            System.out.println(hero.getName() + " agora pode usar " + newCard.getName() + " durante suas lutas.");
            hero.addCard(newCard);
        }
        else if (choice == 2) {
            int trueHealing = hero.gainHealth(healthPossibility.getHealthChange());
            if (trueHealing < 0) System.out.println("Você perdeu " + trueHealing + " de vida.");
            else System.out.println("Você ganhou " + trueHealing + " de vida.");
        }
        else if (choice == 3) {
            int coins = coinPossibility.getCoins();
            hero.addCoins(coins);
            if (coins > 0) System.out.println("Você ganhou " + coins + " moedas.");
            else System.out.println("Você perdeu " + coins + " moedas.");
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }
}