public abstract class Entity {

    private String name;//fato de estar private torna encapsulado
    private int health;
    private int maxHealth;
    private int shield;
    private int stamina;
    private int maxStamina;

    public Entity(String name, int maxHealth, int maxStamina){//funcao que cria a entidade
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.shield = 0;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
    }

    public void receiveDamage(int damageInflicted){
        if (this.shield + this.health <= damageInflicted) {
            this.setShield(0);
            this.setHealth(0);
        }
        else if (this.shield >= damageInflicted){
            this.shield -= damageInflicted;
        }
        else{
            int realDamage = damageInflicted - this.shield;
            this.shield = 0;
            this.health -= realDamage;
        }
        System.out.println(this.name + " (vida: " + this.health + "/" + this.maxHealth + ") (reflexos: " + this.shield + ")\n");
    }

    public void gainShield(int shieldPoints){
        int newShield = this.getShield() + shieldPoints;
        this.setShield(newShield);
    }

    public void printStats() {
        System.out.println(this.getName() + " (Vida: " + this.getHealth() + "/" + this.getMaxHealth() + ") (Reflexos: " + this.getShield() + ")");
    }

    public boolean isAlive(){
        return this.health > 0;//true se vivo
    }

    public void spendStamina(int consumedStamina){
        int newStamina = this.getStamina() - consumedStamina;
        this.setStamina(newStamina);
    }

    public void newTurn(){
        this.setStamina(this.getMaxStamina());;
        this.setShield(0);
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

    public void setStamina(int stamina) {
        this.stamina = stamina;
    } 

    public int getMaxStamina() {
        return this.maxStamina;
    }

    public String getName(){
        return this.name;
    }

}
