package scripts.varrockminer;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import scripts.Task;
import scripts.helper.Walker;

public class MoveToMine extends Task<ClientContext> {

    private final Walker walker;
    private Tile[] path;
    private final Tile mineLocation = new Tile(3285, 3365, 0);

    public MoveToMine(ClientContext ctx, Tile[] tiles){
        super(ctx);
        this.walker = new Walker(ctx);
        this.path = tiles;
    }

    @Override
    public boolean activate() {
        Tile playerLocation = ctx.players.local().tile();
        return playerLocation.distanceTo(mineLocation) > 6 && !ctx.inventory.isFull();
    }

    @Override
    public void execute(){
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPath(path);
        }
    }
}
