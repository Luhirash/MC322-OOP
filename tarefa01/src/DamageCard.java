public class DamageCard {

    private String name;
    private int stamina_cost;
    private int damage_inflicted;

    public DamageCard(String name, int stamina_cost, int damage_inflicted){
        this.name = name;
        this.stamina_cost = stamina_cost;
        this.damage_inflicted = damage_inflicted;
    }
    //aqui eu devo terminar a carta dano, flata um método para utilizar 

    public boolean strike(Hero hero, Enemy enemy){
        if(hero.get_stamina() >= this.stamina_cost){
            hero.spend_stamina(stamina_cost);
            enemy.receive_damage((damage_inflicted));
            System.out.println("-> Voce usou " + this.name + " e causou " + this.damage_inflicted + " de dano!");
            return true;//realizou o golpe
        }
        else{
            System.out.println("-> Energia insuficiente para usar " + this.name + "!");
            return false;//não deu
        }
    }
}
