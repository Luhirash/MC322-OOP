package Possibilities;

public class HealthPossibility extends Possibility{
    
    private int healthChange;
    
    public HealthPossibility(String description, int healthChange) {
        super(description);
        this.healthChange = healthChange;
    }

    public int getHealthChange() {
        return this.healthChange;
    }
}
