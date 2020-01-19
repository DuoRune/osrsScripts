package scripts.varrockminer;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import scripts.Task;

public class BankOres extends Task<ClientContext> {

    private final Tile bankLocation = new Tile(3253, 3421, 0);
    private final int[] ORE_ITEM_IDS = {436, 438, 440};

    public BankOres(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(bankLocation) < 3;
    }

    @Override
    public void execute() {
        if(ctx.bank.inViewport()){
            ctx.bank.open();
            final int numCopper = ctx.inventory.select().id(ORE_ITEM_IDS[0]).count();
            final int numTin = ctx.inventory.select().id(ORE_ITEM_IDS[1]).count();
            final int numIron = ctx.inventory.select().id(ORE_ITEM_IDS[2]).count();
            if(numCopper + numTin + numIron < 28){
                ctx.bank.deposit(ORE_ITEM_IDS[0], numCopper);
                ctx.bank.deposit(ORE_ITEM_IDS[1], numTin);
                ctx.bank.deposit(ORE_ITEM_IDS[2], numIron);
            }else{
                ctx.bank.depositInventory();
            }
            ctx.bank.close();
        }
    }
}
