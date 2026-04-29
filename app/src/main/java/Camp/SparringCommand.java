package Camp;

import java.util.ArrayList;
import java.util.Scanner;

import Cards.Card;
import Entities.Hero;

/**
 * Comando Concreto: Treino de Sparring com Manoplas.
 *
 * <p>Implementação de {@link CampActionCommand} que representa uma sessão de treino
 * técnico com o preparador físico. O herói aprimora a execução de um golpe ou
 * movimento defensivo — mecanicamente, isso realiza o <em>upgrade</em> de uma carta
 * do seu baralho.</p>
 *
 * <p>No padrão <em>Command</em>, esta classe é o <b>Concrete Command</b>:</p>
 * <ul>
 *   <li>Sabe <em>o que</em> fazer (escolher e melhorar uma carta).</li>
 *   <li>Sabe <em>como</em> interagir com o usuário via {@link Scanner}
 *       para obter a escolha.</li>
 *   <li>Não sabe <em>quando</em> será executado — isso é responsabilidade do
 *       Invoker ({@link Events.TrainingCamp}).</li>
 * </ul>
 *
 * <p>Cartas já aprimoradas são exibidas com a marcação {@code [APRIMORADA]} e
 * não podem ser selecionadas novamente, evitando desperdício da ação.</p>
 *
 * @see CampActionCommand
 * @see Events.TrainingCamp
 * @see Cards.Card
 */
public class SparringCommand implements CampActionCommand {

    private static final String NAME = "Treino de Sparring";
    private static final String DESCRIPTION =
            "Anderson Silva vai para as manoplas com o preparador. " +
            "Escolha uma técnica do seu arsenal para aprimorar — ela ficará mais poderosa para sempre.";

    /** Scanner para leitura da escolha do jogador via terminal. */
    private final Scanner scanner;

    /**
     * Constrói o comando de sparring com o scanner de entrada do usuário.
     *
     * @param scanner entrada do usuário via terminal
     */
    public SparringCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Executa o processo de upgrade de uma carta do baralho do herói.
     *
     * <p>Fluxo de execução:</p>
     * <ol>
     *   <li>Obtém o baralho completo do herói.</li>
     *   <li>Verifica se há cartas passíveis de melhoria.</li>
     *   <li>Exibe a lista de cartas com seu status de upgrade.</li>
     *   <li>Lê e valida a escolha do jogador.</li>
     *   <li>Chama {@link Card#upgrade()} na carta selecionada.</li>
     * </ol>
     *
     * @param hero o herói cujo baralho será consultado e modificado
     */
    @Override
    public void execute(Hero hero) {
        ArrayList<Card> deck = hero.getHits();

        System.out.println("\n🥊 Anderson Silva entra na sala de treinos da Chute Boxe...");
        System.out.println("   O preparador aguarda com as manoplas em punho.");
        System.out.println("   \"Qual técnica você quer aperfeiçoar hoje, Spider?\"\n");

        if (!hasUpgradableCards(deck)) {
            System.out.println("⚠️  Preparador: \"Cara, você já aprimorou tudo que tinha! Que monstro.\"");
            System.out.println("    Não há cartas disponíveis para melhoria.\n");
            return;
        }

        printDeck(deck);

        int choice = getValidCardChoice(deck);

        Card chosen = deck.get(choice - 1);
        String oldName = chosen.getName();
        chosen.upgrade();

        System.out.println("\n✅ Técnica aprimorada!");
        System.out.printf("   [%s] evoluiu para [%s]!%n", oldName, chosen.getName());
        System.out.println("   Preparador: \"Isso! Agora sim tá no nível do campeão.\"\n");
    }

    /**
     * Verifica se existe ao menos uma carta no baralho que ainda pode ser aprimorada.
     *
     * @param deck o baralho do herói
     * @return {@code true} se houver ao menos uma carta não aprimorada
     */
    private boolean hasUpgradableCards(ArrayList<Card> deck) {
        return deck.stream().anyMatch(card -> !card.IsUpgraded());
    }

    /**
     * Exibe no terminal a lista numerada de cartas do baralho,
     * sinalizando quais já foram aprimoradas e quais estão disponíveis.
     *
     * @param deck o baralho a ser exibido
     */
    private void printDeck(ArrayList<Card> deck) {
        System.out.println("--- Seu Arsenal de Técnicas ---");
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            String status = card.IsUpgraded() ? " [APRIMORADA ✨]" : "";
            System.out.printf("  %d) %s%s%n", i + 1, card.getName(), status);
        }
        System.out.println("--------------------------------");
        System.out.println("  (Cartas marcadas como [APRIMORADA] não podem ser melhoradas novamente)");
        System.out.println("--------------------------------\n");
    }

    /**
     * Lê e valida a escolha do jogador, garantindo que:
     * <ul>
     *   <li>O input é um número inteiro.</li>
     *   <li>O número corresponde a um índice válido do baralho.</li>
     *   <li>A carta escolhida ainda não foi aprimorada.</li>
     * </ul>
     *
     * @param deck o baralho do herói (para validação de índice e status)
     * @return índice escolhido (base 1) correspondente a uma carta válida e não aprimorada
     */
    private int getValidCardChoice(ArrayList<Card> deck) {
        int choice = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Escolha a técnica a aprimorar (número): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > deck.size()) {
                    System.out.printf("❌ Número inválido. Digite entre 1 e %d.%n", deck.size());
                } else if (deck.get(choice - 1).IsUpgraded()) {
                    System.out.println("❌ Preparador: \"Essa técnica já tá no máximo, Spider! Escolhe outra.\"");
                } else {
                    entradaValida = true;
                }
            } else {
                System.out.println("❌ Isso não é um número! O preparador perdeu a paciência por um segundo...");
                scanner.next(); // Descarta entrada de texto inválida
            }
        }
        return choice;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}