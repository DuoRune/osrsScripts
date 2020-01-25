package scripts.evsmelter;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import scripts.Task;
import scripts.helper.Walker;
import static scripts.evsmelter.Constants.*;

public class Traverse extends Task<ClientContext> {

    public final Walker walker;

    public Traverse(ClientContext ctx){
        super(ctx);
        walker = new Walker(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.inventory.isFull() && ctx.inventory.select().id(ORE_IDS).size() > 0) || (ctx.inventory.select().id(ORE_IDS).size() == 0);
    }

    @Override
    public void execute() {
        if(!ctx.movement.running()) {
            ctx.movement.running(ctx.movement.energyLevel() > Random.nextInt(40, 65));
        }
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            if(ctx.inventory.select().id(ORE_IDS).size() > 0){
                walker.walkPathReverse(BANK_TO_EV);
            }else if(ctx.inventory.select().id(ORE_IDS).size() == 0){
                walker.walkPath(BANK_TO_EV);
            }
        }
    }
}
