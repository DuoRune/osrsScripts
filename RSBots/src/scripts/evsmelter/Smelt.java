package scripts.evsmelter;

import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import static scripts.evsmelter.Constants.*;

public class Smelt extends Task<ClientContext> implements MessageListener {

    private boolean wait = false;

    public Smelt(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().tile().distanceTo(EV) < 5 && ctx.inventory.select().id(ORE_IDS).size() > 0;
    }

    @Override
    public void execute() {
        if(ctx.objects.select().id(FURNACE).nearest().peek().inViewport()){
            ctx.objects.nearest().peek().interact("Smelt");
            ctx.widgets.widget(270).component(14).interact("Smelt");
            Condition.wait(() -> wait == true, 100, 20);
            wait = false;
        }else{
            ctx.camera.turnTo(ctx.objects.nearest().peek());
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        if(messageEvent.text().contains("You smelt")){
            wait = true;
        }
    }
}
