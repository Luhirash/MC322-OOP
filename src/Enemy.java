public class Enemy extends Entity {
    
    public Enemy(String name, int maxHealth, int maxStamina){//funcao que cria o heroi
        super(name, maxHealth, maxStamina);
        
    }

    public void attack(Hero hero, DamageCard damageCard){
        this.spendStamina(damageCard.getStaminaCost());
        hero.receiveDamage(damageCard.getDamageInflicted());
        System.out.println(this.getName() + " golpeou com um " + damageCard.getName() + " causando " + damageCard.getDamageInflicted() + " de dano");
    }
}
