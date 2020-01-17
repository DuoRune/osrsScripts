package scripts.furbuyer;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;

public class WalkToBank extends Task<ClientContext> {

    private TilePath toBank;

    public WalkToBank(ClientContext ctx, TilePath toBank){
        super(ctx);
        this.toBank = toBank;
    }

    @Override
    public boolean activate() {
        System.out.println("To Bank: " + (!ctx.bank.opened() && ctx.inventory.isFull() && !ctx.bank.inViewport()));
        return !ctx.bank.opened() && ctx.inventory.isFull() && !ctx.bank.inViewport();
    }

    @Override
    public void execute() {
        toBank.traverse();
    }
}
