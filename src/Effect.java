import java.util.ArrayList;

public abstract class Effect{
    
    private String name;
    private Entity owner;
    private int intensity;

    public abstract void getString();
    public abstract void useEffect(Entity entity);
    public abstract void beNotified(int event);

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
            if (effects.get(i).getName() == getName() && effects.get(i).getOwner() == getOwner())
                effectIndex = i;
        }
        return effectIndex;
    }
}
