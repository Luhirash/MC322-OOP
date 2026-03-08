import java.util.Random;

public class Enemy {
    
    private String name;//fato de estar private torna encapsulado
    private int health;
    private int max_health;
    private int shield;
    private int stamina;
    private int max_stamina;


    public Enemy(String name, int max_health, int max_stamina){//funcao que cria o heroi
        this.name = name;
        this.max_health = max_health;
        this.health = max_health;
        this.shield = 0;
        this.max_stamina = max_stamina;
        this.stamina = max_stamina;
    }

    public void receive_damage(int damage){//funcao toma dano
        if (this.shield >= damage){//blocked damage
            this.shield -= damage;
        }
        else{
            int real_damage = damage - this.shield;
            this.shield = 0;
            this.health -= real_damage;
        }
    }

    public void enemy_turn(Hero hero){
        Random number = new Random();

        int jab = 3;
        int cross = 5;
        int lowkick = 8;
        int highkick = 12;
        int focus = 2;

        while(this.stamina > 0 && hero.is_alive()){

            int action = number.nextInt(4);//adaptei para o caso 0

            if(action == 0 && this.stamina >= jab){
                System.out.println(this.name + " golpeou com um jab");
                hero.receive_damage(8);
                this.stamina -= jab;
            }
            else if(action == 1 && this.stamina >= cross){
                System.out.println(this.name + " golpeou com um direto");
                hero.receive_damage(14);
                this.stamina -= cross;
            }
            else if(action == 2 && this.stamina >= lowkick){
                System.out.println(this.name + " golpeou com um chute na perna");
                hero.receive_damage(18);
                this.stamina -= lowkick;
            }
            else if(action == 3 && this.stamina >= highkick){
                System.out.println(this.name + " golpeou com um chute na cabeça");
                hero.receive_damage(22);
                this.stamina -= highkick;
            }
            else if (action == 4 && this.stamina >= 2){
                System.out.println(this.name + " está atento ao próximo ataque que receber");
                this.stamina -= focus;
                this.shield += 10;
            }
            else{//stamina abaixo da minima para uma açao
                break;
            }
        }
        System.out.println(this.name + " encerrou seu turno");
    }

    public boolean is_alive(){
        return this.health > 0;//true se vivo
    }

    public int get_health() {
        return this.health;
    }


    public int get_max_health() {
        return this.max_health;
    }

    public int get_shield() {
        return this.shield;
    }


    public void gain_shield(int shield_points){
        this.shield += shield_points;
    }

    public int get_stamina() {
        return this.stamina;
    }

    public String get_name(){
        return this.name;
    }

    public void new_turn(){
        this.stamina = this.max_stamina;
        this.shield = 0;
    }

}
