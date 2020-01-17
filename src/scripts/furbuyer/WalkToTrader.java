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
        System.out.println("To Trader: " + (!ctx.bank.opened() && !ctx.inventory.isFull() && ctx.npcs.select().id(BARAEK).within(5).isEmpty()));
        System.out.println("To Trader1: " + ctx.npcs.select().id(BARAEK).within(5).isEmpty());
        System.out.println("To Trader2: " + !ctx.inventory.isFull());
        System.out.println("To Trader3: " + !ctx.bank.opened());
        System.out.println("Baraek: " + ctx.npcs.select().id(BARAEK).within(5));
        return !ctx.bank.opened() && !ctx.inventory.isFull() && ctx.npcs.select().id(BARAEK).within(5).isEmpty();
    }

    @Override
    public void execute() {
        System.out.println("Valid path to trader: " + toTrader.traverse());
    }
}
