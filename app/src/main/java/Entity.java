import java.util.ArrayList;

public abstract class Entity{

    private String name;//fato de estar private torna encapsulado
    private int health;
    private int maxHealth;
    private int shield;
    private int stamina;
    private int maxStamina;
    private ArrayList<Effect> effects;

    public Entity(String name, int maxHealth, int maxStamina){//funcao que cria a entidade
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.shield = 0;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.effects = new ArrayList<Effect>();
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
    }

    public abstract Card[] getHits();

    public void gainShield(int shieldPoints){
        int newShield = this.getShield() + shieldPoints;
        this.setShield(newShield);
    }

    public void gainHealth(int healthPoints){
        setHealth(getHealth() + healthPoints);
    }

    public void printStats() {
        if (isAlive())
            System.out.print(this.getName() + " (Vida: " + this.getHealth() + "/" + this.getMaxHealth() + ") (Bloqueio: " + this.getShield() + ")");
        else
            System.out.print(this.getName() + " (Vida: 0/" + this.getMaxHealth() + ") (Bloqueio: " + this.getShield() + ")");
        // Imprime efeitos ativos ao lado dos stats
        if (!effects.isEmpty()) {
            System.out.print(" [Efeitos: ");
            for (int i = 0; i < effects.size(); i++) {
                System.out.print(effects.get(i).getString());
                if (i < effects.size() - 1) System.out.print(", ");
            }
            System.out.print("]");
        }
        System.out.println();
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

    public void applyEffect(Effect effect) { //Adiciona o efeito na lista de efeitos, ou aumenta sua intensidade
        int effectIndex = effect.getIndex(effects);
        if (effectIndex == -1)
            effects.add(effect);
        else
            effects.get(effectIndex).addIntensity(effect.getIntensity()); //adiciona intensidade no efeito já existente.
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

    public int getStrengthBonus() {
        for (Effect e : effects) {
            if (e.getName().equals("Força"))
                return e.getIntensity();//retorna o bonus de dano se houver efeito
        }
        return 0;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }
}