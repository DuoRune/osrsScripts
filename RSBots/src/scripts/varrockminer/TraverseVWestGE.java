package scripts.varrockminer;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import scripts.helper.Walker;
import static scripts.varrockminer.Constants.*;
import static scripts.varrockminer.GlobalVariables.*;

public class TraverseVWestGE extends Task<ClientContext> {

    private final Walker walker;
    private boolean toGE = true;

    public TraverseVWestGE(ClientContext ctx){
        super(ctx);
        walker = new Walker(ctx);
    }

    @Override
    public boolean activate(){
        if(!mining){
            return true;
        }
        if(ctx.players.local().tile().distanceTo(bankLocation) < 4){
            if(!ctx.bank.opened()){
                ctx.bank.open();
            }
            if(ctx.bank.select().id(ORE_ITEM_IDS[0]).count(true) < 14 && ctx.bank.select().id(ORE_ITEM_IDS[1]).count(true) < 14){
                mining = true;
                return false;
            }else{
                ctx.bank.depositInventory();
                mining = false;
            }
            ctx.bank.close();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void execute(){
        if(!ctx.movement.running()) {
            ctx.movement.running(ctx.movement.energyLevel() > Random.nextInt(40, 65));
        }
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPathReverse(PATH_VWEST_GE);
        }
    }

}
