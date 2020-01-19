package scripts.varrockminer;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import scripts.Task;
import scripts.helper.Walker;

public class MoveToBank extends Task<ClientContext> {

    private final Walker walker;
    private Tile[] path;
    private final Tile bankLocation = new Tile(3253, 3421, 0);

    public MoveToBank(ClientContext ctx, Tile[] tiles){
        super(ctx);
        this.walker = new Walker(ctx);
        this.path = tiles;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(bankLocation) > 5;
    }

    @Override
    public void execute() {
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPathReverse(path);
        }
    }
}
