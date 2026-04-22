package Core;

import Entities.Enemy;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes responsável por verificar a funcionalidade da estrutura de árvore
 * de inimigos (EnemyNode) usada no núcleo (Core) do jogo para definir o progresso das lutas.
 */
public class EnemyNodeTest {

    /**
     * Verifica a criação da árvore de inimigos e a estrutura inicial retornada.
     * Assegura que a raiz não é nula, que o primeiro inimigo (raiz) está correto,
     * e que existem exatamente 2 caminhos (filhos) a partir da raiz, validando também
     * os inimigos nesses nós filhos.
     */
    @Test
    void CreateTheTreeAndReturnTheRoot() {
        EnemyNode nodes_factory = new EnemyNode();
        EnemyNode root = nodes_factory.createTree();

        assertNotNull(root.getEnemy(), "Root must not be Null");
        assertEquals("Kenneth Allen", root.getEnemy().getName(), "The first enemy must be Kenneth Allen");

        assertEquals(2, root.getChildCount(), "The root mus have 2 options of path");
        
        EnemyNode first_son = (EnemyNode) root.getChildAt(0);
        EnemyNode second_son = (EnemyNode) root.getChildAt(1);
        
        assertEquals("Francis Ngannou", first_son.getEnemy().getName());
        assertEquals("Max Holloway", second_son.getEnemy().getName());
    }

    /**
     * Verifica se o método que retorna os próximos adversários disponíveis
     * devolve um array com os inimigos corretos correspondentes ao nível subsequente.
     */
    @Test
    void ReturnArrayWithTheEnemiesOfTheNextLevel() {
        EnemyNode node_factory = new EnemyNode();
        EnemyNode root = node_factory.createTree();

        Enemy[] next_enemies = root.nextEnemies();

        assertEquals(2, next_enemies.length, "Must return an array with the next 2 enemies");
        assertEquals("Francis Ngannou", next_enemies[0].getName());
        assertEquals("Max Holloway", next_enemies[1].getName());
    }

    /**
     * Simula a entrada do usuário para testar a escolha do próximo inimigo na árvore.
     * Utiliza um Scanner falso com uma entrada válida e garante que o nó retornado
     * corresponde à escolha feita.
     */
    @Test
    void ChoseTheNextEnemyWithValidInput() {
        EnemyNode node_factory = new EnemyNode();
        EnemyNode root = node_factory.createTree();

        String simulated_input = "1\n"; 
        Scanner fake_Scanner = new Scanner(simulated_input);

        EnemyNode chosen = root.chooseNextEnemy(fake_Scanner);
        assertEquals("Francis Ngannou", chosen.getEnemy().getName(), "Must chose option 1 (Ngannou)");
        fake_Scanner.close();
    }

    /**
     * Testa a robustez da seleção de inimigos simulando entradas inválidas pelo usuário.
     * Garante que o sistema ignora as entradas incorretas e aguarda (em loop) até que
     * uma opção válida seja fornecida, retornando o nó correto da árvore em seguida.
     */
    @Test
    void LoopWithInvalidInputsUntilValidInputAndChoseCorrectly() {
        EnemyNode node_factory = new EnemyNode();
        EnemyNode root = node_factory.createTree();

        String simulated_input = "0\n9\n2\n"; // 0, 9 and then 2
        Scanner fake_scanner = new Scanner(simulated_input);

        EnemyNode chosen = root.chooseNextEnemy(fake_scanner);

        assertEquals("Max Holloway", chosen.getEnemy().getName(), "Must ignore invalid inputs and then pick option 2(Holloway)");
        fake_scanner.close();
    }

    /**
     * Verifica a inicialização do construtor vazio da classe EnemyNode.
     * Assegura que um nó criado sem parâmetros iniciais não contenha nenhum inimigo associado.
     */
    @Test
    void InitializeEmptyConstructorCorrectly() {
        EnemyNode emptyNode = new EnemyNode();
        assertNull(emptyNode.getEnemy(), "Empty node must not have an enemy");
    }

}