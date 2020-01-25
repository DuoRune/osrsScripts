package scripts.evsmelter;

import org.powerbot.bot.EventDispatcher;
import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import static scripts.evsmelter.Constants.*;

public class Smelt extends Task<ClientContext> implements MessageListener {

    private long millisRecent;
    private long millisCurrent;
    private long dt;
    private boolean smelting = false;
    private Selection bar;

    public Smelt(ClientContext ctx, Selection bar){
        super(ctx);
        millisRecent = 0;
        millisCurrent = System.currentTimeMillis();
        dt = 0;
        this.bar = bar;
        ctx.dispatcher.add(this);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().tile().distanceTo(EV) < 4 && ctx.inventory.select().id(ORE_IDS).size() > 0;
    }

    @Override
    public void execute() {
        millisCurrent = System.currentTimeMillis();
        dt = millisCurrent - millisRecent;
        if(ctx.objects.select().id(FURNACE).nearest().peek().inViewport()){
            if(timeout() || !smelting) {
                System.out.println("Smelting");
                ctx.objects.nearest().peek().interact("Smelt");
                Condition.wait(() -> ctx.widgets.widget(FURNACE_WIDGET).valid(), 200, 15);
                ctx.widgets.widget(FURNACE_WIDGET).component(bar.comp()).interact("Smelt");
                smelting = true;
            }
        }else{
            ctx.camera.turnTo(ctx.objects.nearest().peek());
        }
    }

    private boolean timeout(){
        if(dt > 6000){
            System.out.println("Timeout!");
            smelting = false;
            return true;
        }
        return false;
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        if(messageEvent.text().contains("You smelt")){
            System.out.println("Smelted!");
            millisRecent = millisCurrent;
        }
    }
}
