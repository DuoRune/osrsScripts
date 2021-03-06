package scripts.varrockminer;

import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import static scripts.varrockminer.Constants.*;
import static scripts.varrockminer.GlobalVariables.*;

public class BankOres extends Task<ClientContext> {

    public BankOres(ClientContext ctx){
        super(ctx);
    }

    /* activate this task if inventory is full and player is closer than 3 tiles to the nearest bank */
    @Override
    public boolean activate() {
        return ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(bankLocation) < 3 && mining;
    }

    /* If the bank is visible, then
     * Open the bank, calculate number of each ore in the inventory
     * If the entire inventory consists of ore, then deposit all
     * else, deposit a number of each ore equal to the number of that ore in the inventory
     * (The above logic is actually reversed, but same idea)
     * close the inventory.
     */
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
