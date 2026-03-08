public class ShieldCard {
    private String name;
    private int stamina_cost;
    private int damage_blocked;

    public ShieldCard(String name, int stamina_cost, int damage_blocked){
        this.name = name;
        this.stamina_cost = stamina_cost;
        this.damage_blocked = damage_blocked;
    }

    public void use_ShieldCard(Hero hero){
        if(hero.get_stamina() >= this.stamina_cost){
            System.out.println("Você usou " + this.name + "!");
            hero.spend_stamina(this.stamina_cost);
            hero.gain_shield(this.damage_blocked);
        }
        else{
            System.out.println("Energia insuficiente para usar " + this.name + ".");
        }

    }
    public String get_name(){
        return this.name;
    }

    public int get_cost(){
        return this.stamina_cost;
    }




}
