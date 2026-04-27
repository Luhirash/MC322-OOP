package Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entities.Hero;
import Relics.EnhancedGlovesStrategy;
import Relics.HandWrapStrategy;
import Relics.MouthGuardStrategy;
import Relics.RelicStrategy;

/**
 * Evento de Loja de Equipamentos de MMA.
 *
 * <p>Implementa o evento {@link Event} que representa uma loja onde o herói
 * pode gastar as moedas ganhas nas batalhas para adquirir {@link RelicStrategy relíquias}
 * — itens passivos que conferem bônus permanentes durante o restante da jornada.</p>
 *
 * <p>O padrão <em>Strategy</em> é aplicado aqui de duas formas:</p>
 * <ol>
 *   <li>Cada item à venda é uma implementação concreta de {@link RelicStrategy},
 *       encapsulando seu próprio comportamento passivo.</li>
 *   <li>A loja não precisa saber <em>como</em> cada relíquia funciona — apenas
 *       exibe nome, descrição e custo via interface.</li>
 * </ol>
 *
 * <p>Fluxo de {@link #startEvent}:</p>
 * <ol>
 *   <li>Exibe saudação temática do lojista.</li>
 *   <li>Lista os itens disponíveis com preços.</li>
 *   <li>Exibe o saldo do herói.</li>
 *   <li>Aguarda a escolha do jogador via {@link Scanner}.</li>
 *   <li>Processa a compra (debita moedas e adiciona a relíquia ao herói).</li>
 * </ol>
 *
 * @see RelicStrategy
 * @see MouthGuardStrategy
 * @see HandWrapStrategy
 * @see Hero
 */
public class Shop extends Event {

    private static final String NAME = "Loja do Octógono";
    private static final String DESCRIPTION = "Uma loja de equipamentos de MMA — compre relíquias para a sua jornada.";

    private final Scanner scanner;

    /** Catálogo de relíquias disponíveis para compra nesta instância da loja. */
    private final List<RelicStrategy> availableRelics;

    /**
     * Constrói a loja populando o catálogo com as relíquias disponíveis.
     *
     * @param scanner entrada do usuário via terminal
     */
    public Shop(Scanner scanner) {
        this.scanner = scanner;
        this.availableRelics = new ArrayList<>();
        populateCatalog();
    }

    private void populateCatalog() {
        availableRelics.add(new MouthGuardStrategy());
        availableRelics.add(new HandWrapStrategy());
        availableRelics.add(new EnhancedGlovesStrategy());
    }


    public void startEvent(Hero hero) {
        printGreeting(hero);

        boolean shopping = true;
        while (shopping) {
            printCatalog(hero);
            int choice = getPlayerChoice(availableRelics.size());

            if (choice == 0) {
                // Jogador escolheu sair
                shopping = false;
                printFarewell();
            } else {
                RelicStrategy chosen = availableRelics.get(choice - 1);
                shopping = processPurchase(hero, chosen);
            }
        }
    }

    /**
     * Exibe a saudação temática do lojista.
     *
     * @param hero o herói que chega à loja
     */
    private void printGreeting(Hero hero) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       🥊  LOJA DO OCTÓGONO  🥊       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("\nLojista: \"E aí, " + hero.getName() + "! Chegou no lugar certo.");
        System.out.println("         Aqui a gente equipa campeão, não faz milagre.");
        System.out.println("         Dá uma olhada no que tenho pra você...\"\n");
    }


    private void printCatalog(Hero hero) {
        System.out.println("--- Equipamentos Disponíveis ---");
        for (int i = 0; i < availableRelics.size(); i++) {
            RelicStrategy relic = availableRelics.get(i);
            boolean alreadyOwned = hero.hasRelic(relic.getName());
            String status = "";
            if (alreadyOwned) {
                status = " [JÁ ADQUIRIDO]";
            }

            System.out.printf("  %d) %-30s | %3d moedas%s%n",
                    i + 1,
                    relic.getName(),
                    relic.getCost(),
                    status);
            System.out.println("     └─ " + relic.getDescription());
        }
        System.out.println("  0) Sair da loja");
        System.out.println("--------------------------------");
        System.out.println("  💰 Suas moedas: " + hero.getCoins());
        System.out.println("--------------------------------");
    }

    private int getPlayerChoice(int maxOption) {
        int choice = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Sua escolha: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 0 && choice <= maxOption) {
                    entradaValida = true;
                } else {
                    System.out.println("❌ Opção inválida. Digite um número entre 0 e " + maxOption + ".");
                }
            } else {
                System.out.println("❌ Isso não é um número! Tente de novo.");
                scanner.next();
            }
        }
        return choice;
    }

    /**
     * Processa a tentativa de compra de uma relíquia.
     *
     * <ul>
     *   <li>Verifica se o herói já possui a relíquia.</li>
     *   <li>Verifica se o herói tem moedas suficientes.</li>
     *   <li>Caso aprovado: debita as moedas e adiciona a relíquia ao inventário.</li>
     * </ul>
     *
     * @param hero   o herói comprador
     * @param chosen a relíquia selecionada
     * @return {@code true} se o jogador deve continuar no menu; {@code false} para encerrar
     */
    private boolean processPurchase(Hero hero, RelicStrategy chosen) {
        if (hero.hasRelic(chosen.getName())) {
            System.out.println("\nLojista: \"Você já tem esse item, campeão. Não vai comprar dois não!\"");
            return true;
        }

        if (hero.getCoins() < chosen.getCost()) {
            System.out.printf("%nLojista: \"Olha, você tem só %d moedas e esse item custa %d.%n", hero.getCoins(), chosen.getCost());
            System.out.println("         Vai lá ganhar mais umas lutas e volta aqui.\"\n");
            return true;
        }

        hero.spendCoins(chosen.getCost());
        hero.addRelic(chosen);

        System.out.println("\n✅ Compra realizada!");
        System.out.println("Lojista: \"Boa escolha! [" + chosen.getName() + "] é seu agora.");
        System.out.println("         Vai com tudo, Spider!\"\n");
        System.out.println("💰 Saldo restante: " + hero.getCoins() + " moedas.\n");

        return true; // Mantém o menu aberto para possível segunda compra
    }

    private void printFarewell() {
        System.out.println("\nLojista: \"Valeu pela visita! Boa sorte no octógono,");
        System.out.println("         Anderson. Traz o cinturão pra cá depois!\"\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         Saindo da loja...            ║");
        System.out.println("╚══════════════════════════════════════╝\n");
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