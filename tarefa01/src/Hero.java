public class Hero {

    private String name;//fato de estar private torna encapsulado
    private int health;
    private int max_health;
    private int shield;
    private int stamina;
    private int max_stamina;

    public Hero(String name, int max_health, int max_stamina){//funcao que cria o heroi
        this.name = name;
        this.max_health = max_health;
        this.health = max_health;
        this.shield = 0;
        this.max_stamina = max_stamina;
        this.stamina = max_stamina;

    }

    public void receive_damage(int damage){
        if (this.shield >= damage){
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


    public boolean is_alive(){
        return this.health > 0;//true se vivo
    }

    public void spend_stamina(int consumed_stamina){
        this.stamina -= consumed_stamina;
    }

    public void new_turn(){
        this.stamina = this.max_stamina;
        this.shield = 0;
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

    public int get_stamina() {
        return this.stamina;
    }

    public String get_name(){
        return this.name;
    }

}
