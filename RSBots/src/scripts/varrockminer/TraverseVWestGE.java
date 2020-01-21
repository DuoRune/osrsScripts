package scripts.varrockminer;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import scripts.helper.Walker;
import static scripts.varrockminer.Constants.*;

public class TraverseVWestGE extends Task<ClientContext> {

    private final Walker walker;

    public TraverseVWestGE(ClientContext ctx){
        super(ctx);
        walker = new Walker(ctx);
    }

    @Override
    public boolean activate(){
        if(ctx.players.local().tile().distanceTo(geLocation) > 5 && ctx.players.local().tile().distanceTo(bankLocation) < 4){
            if(!ctx.bank.opened()){
                ctx.bank.open();
            }
            if(ctx.bank.id(ORE_ITEM_IDS[0]).count(true) < 14 && ctx.bank.id(ORE_ITEM_IDS[1]).count(true) < 14){
                ctx.bank.close();
                return false;
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
