package Core;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Scanner;

import Entities.*;

public class EnemyNode extends DefaultMutableTreeNode{

    private Enemy enemy;

    public EnemyNode() {
    }  

    public EnemyNode(Enemy enemy) {
        this.enemy = enemy;
    }
    private Enemy[] enemies = {
        new KennethAllen("Kenneth Allen", 22, 9),
        new FrancisNgannou("Francis Ngannou", 40, 8),
        new MaxHolloway("Max Holloway", 28, 11),
        new KhabibNurmagomedov("Khabib Nurmagomedov", 35, 10),
        new IsraelAdesanya("Israel Adesanya", 24, 12),
        new JonJones( "Jon Jones", 42, 11),
        new ConnnorMcGregor("Connor McGregor", 30, 14)
    };

    private EnemyNode[] transformEnemies() {
        EnemyNode[] nodes = new EnemyNode[enemies.length];
        for (int i = 0; i < enemies.length; i++)
            nodes[i] = new EnemyNode(enemies[i]);
        return nodes;
    }

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
    
    public Enemy[] nextEnemies() {
        Enemy[] nextEnemies = new Enemy[getChildCount()];
        for (int i = 0; i < getChildCount(); i++)
            nextEnemies[i] = ((EnemyNode) getChildAt(i)).getEnemy();
        return nextEnemies;
    }

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

    public Enemy getEnemy() {
        return this.enemy;
    }

}
