package Cards;
import Entities.*;
import Effects.Effect;

public abstract class EffectCard extends Card{
    
    public EffectCard (String name, int staminaCost, String description) {
        super(name, staminaCost, description);
    }

    public abstract Effect useCard(Entity attacker, Entity receiver); 
    public abstract void printCardStats();
    public abstract int getMainStat();
}
