package scripts;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {

    protected boolean locked = false;

    public Task(C ctx){
        super(ctx);
    }

    protected void lock(){
        locked = !locked;
    }

    public boolean isLocked(){
        return locked;
    }

    public abstract boolean activate();
    public abstract void execute();
}
