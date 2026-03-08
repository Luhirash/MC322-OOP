import java.util.Scanner;


public class App {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        Hero hero = new Hero("Anderson Silva", 40, 20);
        Enemy enemy = new Enemy( "Jon Jones", 42, 20);
        DamageCard jab = new DamageCard("jab", 3, 8);
        DamageCard cross = new DamageCard("direto", 5, 14);
        DamageCard low_kick = new DamageCard("chute na perna", 8, 18);
        DamageCard high_kick = new DamageCard("chute na cabeça", 12, 24);
        ShieldCard focus = new ShieldCard("focar", 2, 10);

        System.out.println("=== A Luta Começou! ===");

        System.out.println(hero.get_name() + " VS " + enemy.get_name());

        while (hero.is_alive() && enemy.is_alive()){

            hero.new_turn();
            boolean player_turn = true;

            while (player_turn && hero.is_alive() && enemy.is_alive()) {
                System.out.println("\n----------------------------------");
                System.out.println(hero.get_name() + " (vida: " + hero.get_health() + "/" + hero.get_max_health() + ") (reflexos: " + hero.get_shield() + ")");
                System.out.println("VS");
                System.out.println(enemy.get_name() + " (vida: " + enemy.get_health() + "/" + enemy.get_max_health() + ") (reflexos: " + enemy.get_shield() + ")");
                System.out.println("\n" + hero.get_stamina() + " de fôlego disponível");
                System.out.println("1 - Golpear com " + jab.get_name() + " (Dano: 8 | Custo: " + jab.get_cost() + ")");
                System.out.println("2 - Golpear com " + cross.get_name() + " (Dano: 14 | Custo: " + cross.get_cost() + ")");
                System.out.println("3 - Golpear com " + low_kick.get_name() + " (Dano: 18 | Custo: " + low_kick.get_cost() + ")");
                System.out.println("4 - Golpear com " + high_kick.get_name() + " (Dano: 24 | Custo: " + high_kick.get_cost() + ")");
                System.out.println("5 - " +  focus.get_name() + " no próximo movimento do adversário (Bloqueio: 10 | Custo: " + focus.get_cost() + ")");
                System.out.println("6 - Encerrar turno ");

                int choice = scanner.nextInt();

                if(choice == 1){
                    jab.strike(hero, enemy);
                }
                else if (choice == 2){
                    cross.strike(hero, enemy);
                }
                else if(choice == 3){
                    low_kick.strike(hero, enemy);
                }
                else if(choice == 4){
                    high_kick.strike(hero, enemy);
                }
                else if(choice == 5){
                    focus.use_ShieldCard(hero);
                }
                else if (choice == 6){
                    player_turn = false;//isso acaba o laço
                    System.out.println("Turno encerrado");
                }
                else{
                    System.out.println("Escolha inválida!");
                }

            }

            if( enemy.is_alive()){
                enemy.new_turn();
                enemy.enemy_turn(hero);
            }
        }

        System.out.println("\n--Fim da luta--");
        if(hero.is_alive()){
            System.out.println("Anderson silva ganhou a luta!");
        }
        else{
            System.out.println("Anderson silva foi derrotado!");
        }

        scanner.close();
    }
}
