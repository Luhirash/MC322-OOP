package Events;

import Entities.Hero;

public abstract class Event {
    
    public abstract void startEvent(Hero hero);

    public abstract String getName();
    public abstract String getDescription();
}
