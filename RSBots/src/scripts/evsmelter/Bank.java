package scripts.evsmelter;

import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import static scripts.evsmelter.Constants.*;

public class Bank extends Task<ClientContext> {

    private Selection bar;

    public Bank(ClientContext ctx, Selection bar){
        super(ctx);
        this.bar = bar;
    }

    @Override
    public boolean activate() {
        return !ctx.inventory.isFull() && /*ctx.inventory.select().size() > 0 &&*/ ctx.players.local().tile().distanceTo(BANK) < 4;
    }

    @Override
    public void execute() {
        if(ctx.bank.inViewport()){
            if(!ctx.bank.opened()){
                ctx.bank.open();
            }
            ctx.bank.depositInventory();

            float ratio = bar.ratio();
            int bar1 = (int) Math.floor(INVENTORY_SIZE * ratio);
            int bar2 = (int) Math.floor(INVENTORY_SIZE * (1 - ratio));

            ctx.bank.withdraw(bar.reqOres()[0], bar1);
            ctx.bank.withdraw(bar.reqOres()[1], bar2);

            ctx.bank.close();
        }else{
            ctx.camera.turnTo(ctx.bank.nearest());
        }

    }
}
