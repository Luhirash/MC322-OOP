public class Enemy {
    
    private String name;//fato de estar private torna encapsulado
    private int health;
    private int maxHealth;
    private int shield;
    private int stamina;
    private int maxStamina;


    public Enemy(String name, int maxHealth, int maxStamina){//funcao que cria o heroi
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.shield = 0;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
    }

    public void receiveDamage(int damage){//funcao toma dano
        if (this.shield >= damage){//blocked damage
            this.shield -= damage;
        }
        else{
            int realDamage = damage - this.shield;
            this.shield = 0;
            this.health -= realDamage;
        }
    }

    public void printStats() {
        System.out.println(this.getName() + " (Vida: " + this.getHealth() + "/" + this.getMaxHealth() + ") (Reflexos: " + this.getShield() + ")");
    }
    
    public boolean isAlive(){
        return this.health > 0;//true se vivo
    }

    public int getHealth() {
        return this.health;
    }


    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getShield() {
        return this.shield;
    }


    public void gainShield(int shieldPoints){
        this.shield += shieldPoints;
    }

    public int getStamina() {
        return this.stamina;
    }

    public void spendStamina(int staminaCost) {
        this.stamina -= staminaCost;
    }

    public String getName(){
        return this.name;
    }

    public void newTurn(){
        this.stamina = this.maxStamina;
        this.shield = 0;
    }

}
