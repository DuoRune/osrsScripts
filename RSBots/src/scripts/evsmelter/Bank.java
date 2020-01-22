package scripts.evsmelter;

import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import static scripts.evsmelter.Constants.*;

public class Bank extends Task<ClientContext> {

    public Bank(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (!ctx.inventory.isFull() && ctx.inventory.select().size() > 0) && ctx.players.local().tile().distanceTo(geLoc) < 5;
    }

    @Override
    public void execute() {
        if(ctx.bank.inViewport()){
            if(!ctx.bank.opened()){
                ctx.bank.open();
            }
            ctx.bank.depositInventory();
            ctx.bank.withdraw(ORE_IDS[0], 14);
            ctx.bank.withdraw(ORE_IDS[1], 14);
        }
    }
}
