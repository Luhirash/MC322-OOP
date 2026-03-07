public class Hero {

    private String name;//fato de estar private torna encapsulado
    private int health;
    private int shield;
    private int stamina;

    public Hero(String name, int health){//funcao que cria o heroi
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

    public void gain_shield(int shield_points){
        this.shield += shield_points;
    }

    public int get_health() {
        return this.health;
    }

    public int get_stamina() {
        return this.stamina;
    }


    public boolean is_alive(){
        return this.health > 0;//true se vivo
    }

    public void spend_stamina(int consumed_stamina){
        this.stamina -= consumed_stamina;
    }
    
    
}
