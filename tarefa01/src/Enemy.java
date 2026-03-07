public class Enemy {
    
    private String name;//fato de estar private torna encapsulado
    private int health;
    private int shield;
    private int stamina;
    private int damage;


    public Enemy(String name, int health){//funcao que cria o heroi
        this.name = name;
        this.health = health;
        this.shield = 0;
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

    public void attack(Hero hero){
        System.out.println("O " + this.name + " ataca causando " + this.damage + " de dano!");
        hero.receive_damage(this.damage);
    }

    public boolean is_alive(){
        return this.health > 0;//true se vivo
    }

    public int get_health() {
        return this.health;
    }


    public void gain_shield(int shield_points){
        this.shield += shield_points;
    }

}
