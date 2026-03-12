public class Hero {

    private String name;//fato de estar private torna encapsulado
    private int health;
    private int maxHealth;
    private int shield;
    private int stamina;
    private int maxStamina;

    public Hero(String name, int maxHealth, int maxStamina){//funcao que cria o heroi
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.shield = 0;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;

    }

    public void receiveDamage(DamageCard damageCard){
        int damage = damageCard.getDamageInflicted();
        if (this.shield + this.health <= damage) {
            this.setShield(0);
            this.setHealth(0);
        }
        else if (this.shield >= damage){
            this.shield -= damage;
        }
        else{
            int realDamage = damage - this.shield;
            this.shield = 0;
            this.health -= realDamage;
        }
        System.out.println(this.name + " (vida: " + this.health + "/" + this.maxHealth + ") (reflexos: " + this.shield + ")\n");
    }

    public void printStats() {
        System.out.println(this.getName() + " (Vida: " + this.getHealth() + "/" + this.getMaxHealth() + ") (Reflexos: " + this.getShield() + ")");
    }

    public void gainShield(int shieldPoints){
        this.shield += shieldPoints;
    }


    public boolean isAlive(){
        return this.health > 0;//true se vivo
    }

    public void spendStamina(int consumedStamina){
        this.stamina -= consumedStamina;
    }

    public void newTurn(){
        this.stamina = this.maxStamina;
        this.shield = 0;
    }
    
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getShield() {
        return this.shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }
    public int getStamina() {
        return this.stamina;
    }

    public int getMaxStamina() {
        return this.maxStamina;
    }

    public String getName(){
        return this.name;
    }

}
