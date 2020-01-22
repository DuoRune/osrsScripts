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
        System.out.println("Activating");
        if(!mining){
            System.out.println("Off to GE");
            return true;
        }
        if(ctx.players.local().tile().distanceTo(geLocation) < 6){
            System.out.println("Close to GE");
            return false;
        }
        if(ctx.players.local().tile().distanceTo(bankLocation) < 4){
            if(!ctx.bank.opened()){
                ctx.bank.open();
                System.out.println("Opening bank...");
            }
            if(ctx.bank.select().id(ORE_ITEM_IDS[0]).count(true) < 14 && ctx.bank.select().id(ORE_ITEM_IDS[1]).count(true) < 14){
                mining = true;
                System.out.println("gotta go mine");
                return false;
            }else{
                ctx.bank.depositInventory();
                System.out.println("gotta go smelt");
                mining = false;
            }
            System.out.println("closing bank");
            ctx.bank.close();
        }else{
            System.out.println("false, for some reason");
            return false;
        }
        System.out.println("true, for some reason");
        return true;
    }

    @Override
    public void execute(){
        System.out.println("Off to GE!");
        if(!ctx.movement.running()) {
            ctx.movement.running(ctx.movement.energyLevel() > Random.nextInt(40, 65));
        }
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPath(PATH_VWEST_GE);
        }
    }

}
