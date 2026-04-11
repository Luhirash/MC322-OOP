package Core;

import Effects.Effect;

public interface Publisher {
    public void subscribe(Effect effect);
    public void unsubscribe(Effect effect);
    public void notifyEvent();
}
