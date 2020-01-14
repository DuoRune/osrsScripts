package scripts.furbuyer;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;

import static scripts.furbuyer.FurBuyer.BARAEK;

public class WalkToTrader extends Task<ClientContext> {

    private TilePath toTrader;

    public WalkToTrader(ClientContext ctx, TilePath toTrader){
        super(ctx);
        this.toTrader = toTrader;
    }

    @Override
    public boolean activate() {
        System.out.println("To Trader: " + (!ctx.bank.opened() && ctx.inventory.isFull() && !ctx.npcs.select().id(BARAEK).within(10).isEmpty()));
        return !ctx.bank.opened() && !ctx.inventory.isFull() && ctx.npcs.select().id(BARAEK).within(10).isEmpty();
    }

    @Override
    public void execute() {
        toTrader.traverse();
    }
}
