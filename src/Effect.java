import java.util.ArrayList;

public abstract class Effect{
    
    private String name;
    private Entity owner;
    private int intensity;

    public Effect (String name, Entity owner, int intensity) {
        this.name = name;
        this.owner = owner;
        this.intensity = intensity;
    }

    public abstract String getString();
    protected abstract void useEffect(Turns turn);
    public abstract void beNotified(Turns turn);

    public String getName() {
        return name;
    }

    public int getIntensity() {
        return intensity;
    }
    private void setIntensity(int newIntensity) {
        intensity = newIntensity;
    }
    public void addIntensity(int value) {
        setIntensity(getIntensity() + value);
    }

    public Entity getOwner() {
        return owner;
    }

    public int getIndex(ArrayList<Effect> effects) {
        int effectIndex = -1;
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i).getName().equals(getName()) && effects.get(i).getOwner() == getOwner())
                effectIndex = i;
        }
        return effectIndex;
    }

    protected void effectFinish(Turns turn) {
        ArrayList<Effect> ownerEffects = getOwner().getEffects();
        ownerEffects.remove(getIndex(ownerEffects));
        turn.unsubscribe(this);
    }
}