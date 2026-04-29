package Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Camp.CampActionCommand;
import Camp.IceBathCommand;
import Camp.SparringCommand;
import Entities.Hero;

/**
 * Evento de Camp de Treinamento — Invoker do padrão Command.
 *
 * <p>O {@code TrainingCamp} representa uma pausa na jornada do Anderson Silva:
 * uma visita à lendária Academia Chute Boxe para descansar, tratar o corpo
 * e afiar as técnicas antes dos próximos combates.</p>
 *
 * <p>No padrão de projeto <em>Command</em> (Refactoring Guru), esta classe
 * desempenha o papel de <b>Invoker</b>:</p>
 * <ul>
 *   <li>Mantém uma lista de {@link CampActionCommand} — os comandos disponíveis.</li>
 *   <li>Não sabe <em>como</em> cada comando funciona — apenas chama {@code execute(hero)}.</li>
 *   <li>É completamente desacoplada dos detalhes de cura ou upgrade de cartas.</li>
 * </ul>
 *
 * <p>Para adicionar uma nova opção ao Camp (ex: comprar suplementos, meditar),
 * basta criar um novo {@link CampActionCommand} concreto e registrá-lo em
 * {@link #registerCommands()} — nenhuma outra classe precisa ser alterada.</p>
 *
 * <p>O jogador pode escolher <b>apenas uma ação</b> por visita ao Camp,
 * reforçando a decisão estratégica característica do gênero.</p>
 *
 * @see CampActionCommand
 * @see Camp.IceBathCommand
 * @see Camp.SparringCommand
 */
public class TrainingCamp extends Event {

    private static final String NAME = "Camp de Treinamento";
    private static final String DESCRIPTION =
            "Academia Chute Boxe — descanse e prepare o corpo para a próxima batalha.";

    /** Scanner compartilhado para interação com o jogador no terminal. */
    private final Scanner scanner;

    /**
     * Lista de comandos disponíveis no Camp.
     * Atua como o repositório de ações do Invoker no padrão Command.
     */
    private final List<CampActionCommand> commands;

    /**
     * Constrói o Camp de Treinamento e registra todas as ações disponíveis.
     *
     * @param scanner entrada do usuário via terminal
     */
    public TrainingCamp(Scanner scanner) {
        this.scanner = scanner;
        this.commands = new ArrayList<>();
        registerCommands();
    }

    /**
     * Registra todos os comandos disponíveis no Camp.
     *
     * <p>Este é o único método que precisa ser alterado para adicionar ou remover
     * ações do Camp de Treinamento, mantendo o restante da classe intacto.</p>
     */
    private void registerCommands() {
        commands.add(new IceBathCommand());
        commands.add(new SparringCommand(scanner));
    }

    /**
     * Inicia o evento do Camp de Treinamento.
     *
     * <p>Fluxo completo do evento:</p>
     * <ol>
     *   <li>Exibe a chegada imersiva do herói à academia.</li>
     *   <li>Lista todas as ações disponíveis com nome e descrição.</li>
     *   <li>Aguarda a escolha do jogador (apenas uma ação por visita).</li>
     *   <li>Invoca o {@link CampActionCommand#execute(Hero)} do comando escolhido.</li>
     *   <li>Exibe a despedida e encerra o evento.</li>
     * </ol>
     *
     * @param hero o herói que está visitando o Camp de Treinamento
     */
    @Override
    public void startEvent(Hero hero) {
        printArrival(hero);
        printMenu();

        int choice = getPlayerChoice();

        CampActionCommand chosenCommand = commands.get(choice - 1);

        System.out.println("\n⚡ Anderson Silva escolheu: " + chosenCommand.getName());
        System.out.println("----------------------------------");

        // O Invoker delega a execução ao Command concreto — sem saber o que vai acontecer.
        chosenCommand.execute(hero);

        printDeparture();
    }

    /**
     * Exibe a chegada temática do herói à Academia Chute Boxe.
     *
     * @param hero o herói que chega ao Camp
     */
    private void printArrival(Hero hero) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   🏋️  ACADEMIA CHUTE BOXE — CAMP  🏋️    ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Anderson Silva empurra a porta da academia.");
        System.out.println("O cheiro de couro e suor velho preenche o ar.");
        System.out.println("Os sparrings param e olham — o Spider chegou.");
        System.out.println();
        System.out.printf("Técnico: \"Descansa um pouco, %s. Mas só um pouco.%n", hero.getName());
        System.out.println("         Você tem uma batalha pela frente e eu sei disso.");
        System.out.println("         O que você vai fazer aqui hoje?\"\n");
    }

    /**
     * Exibe o menu numerado de ações disponíveis no Camp,
     * iterando sobre a lista de comandos registrados.
     */
    private void printMenu() {
        System.out.println("--- O que Anderson Silva vai fazer? ---");
        for (int i = 0; i < commands.size(); i++) {
            CampActionCommand cmd = commands.get(i);
            System.out.printf("  %d) %s%n", i + 1, cmd.getName());
            System.out.println("     └─ " + cmd.getDescription());
        }
        System.out.println("---------------------------------------");
        System.out.println("  ⚠️  Você só pode realizar UMA ação nesta visita.");
        System.out.println("---------------------------------------\n");
    }

    /**
     * Lê e valida a escolha numérica do jogador, tratando input inválido
     * com mensagens temáticas, sem quebrar o fluxo do programa.
     *
     * @return índice escolhido (base 1) correspondente a um comando válido
     */
    private int getPlayerChoice() {
        int choice = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Escolha sua ação: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= commands.size()) {
                    entradaValida = true;
                } else {
                    System.out.printf(
                        "❌ Técnico: \"Não tem essa opção aqui não. Escolhe entre 1 e %d.\" %n",
                        commands.size()
                    );
                }
            } else {
                System.out.println("❌ Técnico: \"Isso não é uma resposta, Spider. Fala um número.\"");
                scanner.next(); // Descarta a entrada de texto inválida
            }
        }
        return choice;
    }

    /**
     * Exibe a mensagem de despedida ao encerrar a visita ao Camp.
     */
    private void printDeparture() {
        System.out.println("----------------------------------");
        System.out.println("Técnico: \"Tá bom. Descansou, treinou.");
        System.out.println("         Agora vai lá e mostra por que te chamam de Spider.\"");
        System.out.println();
        System.out.println("Anderson Silva pega a bolsa e sai da academia.");
        System.out.println("O próximo combate não vai saber o que vai vir.\n");
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          Saindo do Camp...               ║");
        System.out.println("╚══════════════════════════════════════════╝\n");
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