package Core;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Scanner;

import Entities.*;

/**
 * Nó de uma árvore binária que representa um inimigo na progressão do jogo.
 *
 * <p>Estende {@link DefaultMutableTreeNode} para aproveitar a estrutura de árvore
 * já fornecida pela biblioteca Swing. Cada nó carrega um {@link Enemy} e pode ter
 * até dois filhos, que representam os inimigos disponíveis para a próxima luta.</p>
 *
 * <p>A estrutura da árvore é fixa e montada por {@link #createTree()}, produzindo
 * a seguinte hierarquia:</p>
 * <pre>
 *            Kenneth Allen
 *           /             \
 *    Francis Ngannou    Max Holloway
 *       /       \          /       \
 *   Khabib   Adesanya   Jon Jones  McGregor
 * </pre>
 *
 * <p>Após cada vitória, o jogador escolhe qual filho (próximo inimigo) enfrentar
 * via {@link #chooseNextEnemy(Scanner)}.</p>
 *
 * @see Enemy
 * @see App
 */
public class EnemyNode extends DefaultMutableTreeNode{

    private Enemy enemy;

    /**
     * Constrói um nó raiz sem inimigo associado.
     * Utilizado apenas como ponto de entrada para chamar {@link #createTree()}.
     */
    public EnemyNode() {
    }

    /**
     * Constrói um nó associado ao inimigo fornecido.
     *
     * @param enemy inimigo que este nó representa
     */
    public EnemyNode(Enemy enemy) {
        this.enemy = enemy;
    }

    /** Conjunto de inimigos disponíveis no jogo, instanciados com nome, vida e fôlego. */
    private Enemy[] enemies = {
        new KennethAllen("Kenneth Allen", 22, 9),
        new FrancisNgannou("Francis Ngannou", 40, 8),
        new MaxHolloway("Max Holloway", 28, 11),
        new KhabibNurmagomedov("Khabib Nurmagomedov", 35, 10),
        new IsraelAdesanya("Israel Adesanya", 24, 12),
        new JonJones( "Jon Jones", 42, 11),
        new ConnnorMcGregor("Connor McGregor", 30, 14)
    };

    /**
     * Converte o array de {@link Enemy} em um array de {@link EnemyNode},
     * um nó por inimigo.
     *
     * @return array de nós correspondentes a {@link #enemies}
     */
    private EnemyNode[] transformEnemies() {
        EnemyNode[] nodes = new EnemyNode[enemies.length];
        for (int i = 0; i < enemies.length; i++)
            nodes[i] = new EnemyNode(enemies[i]);
        return nodes;
    }

    /**
     * Monta a árvore de inimigos e retorna o nó raiz.
     *
     * <p>A topologia da árvore é fixa:</p>
     * <ul>
     *   <li>Raiz: Kenneth Allen</li>
     *   <li>Filhos da raiz: Francis Ngannou, Max Holloway</li>
     *   <li>Filhos de Francis: Khabib Nurmagomedov, Israel Adesanya</li>
     *   <li>Filhos de Max: Jon Jones, Connor McGregor</li>
     * </ul>
     *
     * @return nó raiz da árvore de inimigos (Kenneth Allen)
     */
    public EnemyNode createTree() {
        EnemyNode[] nodes = transformEnemies();
        nodes[0].add(nodes[1]);
        nodes[0].add(nodes[2]);
        nodes[1].add(nodes[3]);
        nodes[1].add(nodes[4]);
        nodes[2].add(nodes[5]);
        nodes[2].add(nodes[6]);

        return nodes[0];
    }

    /**
     * Retorna os inimigos dos nós filhos deste nó, representando as opções
     * de próximo adversário.
     *
     * @return array com os inimigos dos filhos diretos; vazio se este for um nó folha
     */
    public Enemy[] nextEnemies() {
        Enemy[] nextEnemies = new Enemy[getChildCount()];
        for (int i = 0; i < getChildCount(); i++)
            nextEnemies[i] = ((EnemyNode) getChildAt(i)).getEnemy();
        return nextEnemies;
    }

    /**
     * Exibe os inimigos filhos disponíveis e lê a escolha do jogador via terminal.
     *
     * <p>Apresenta nome, vida máxima e fôlego máximo de cada opção. Continua
     * solicitando entrada até que o jogador informe um número válido.</p>
     *
     * @param scanner entrada do usuário via terminal
     * @return o {@link EnemyNode} filho escolhido pelo jogador
     */
    public EnemyNode chooseNextEnemy(Scanner scanner) {
        Enemy[] nextEnemies = nextEnemies();
        System.out.println("Escolha seu próximo inimigo:");
        for (int i = 0; i < nextEnemies.length; i++) {
            System.out.print(i + 1 + " - " );
            System.out.println(nextEnemies[i].getName() + " (Vida: " + nextEnemies[i].getMaxHealth() + ") (Fôlego: " + nextEnemies[i].getMaxStamina() + ")");
        } 
        int choice = scanner.nextInt();
        while (choice < 1 || choice > nextEnemies.length) {
            System.out.println("Escolha inválida! Tente novamente:");
            choice = scanner.nextInt();
        }
        return (EnemyNode)getChildAt(choice - 1);
    }

    /**
     * Retorna o inimigo associado a este nó.
     *
     * @return o {@link Enemy} deste nó, ou {@code null} se for um nó raiz auxiliar
     */
    public Enemy getEnemy() {
        return this.enemy;
    }

}