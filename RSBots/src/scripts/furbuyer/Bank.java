package scripts.furbuyer;

import org.powerbot.script.rt4.ClientContext;
import scripts.Task;

import static scripts.furbuyer.FurBuyer.BEAR_FUR;

public class Bank extends Task<ClientContext> {

    public Bank(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        System.out.println("Bank: " + (!ctx.bank.opened() && ctx.inventory.isFull() && ctx.bank.inViewport()));
        return !ctx.bank.opened() && ctx.inventory.isFull() && ctx.bank.inViewport();
    }

    @Override
    public void execute() {
        if (!ctx.bank.opened()) {
            //... open it
            ctx.bank.open();
        }
        //if the user has bear fur in their inventory
        else if (!ctx.inventory.id(BEAR_FUR).isEmpty()) {
            //click the "deposit inventory" button in the bank
            ctx.bank.depositInventory();
        } else {
            //close the bank when we're done
            ctx.bank.close();
        }
    }
}
