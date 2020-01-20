package scripts.varrockminer;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import scripts.Task;
import scripts.helper.Walker;

public class MoveToBank extends Task<ClientContext> {

    private final Walker walker;
    private Tile[] path;
    private final Tile bankLocation = new Tile(3253, 3421, 0);

    /* Make the walker and set the path to the one given by the main class */
    public MoveToBank(ClientContext ctx, Tile[] tiles){
        super(ctx);
        this.walker = new Walker(ctx);
        this.path = tiles;
    }

    /* Activate this if the inventory is full and the player is farther than 5 tiles to the bank */
    @Override
    public boolean activate() {
        return ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(bankLocation) > 5;
    }

    /* If the player is not running, then
     * run, if the player has more than a random amount of energy selected between 40 and 64
     * Traverse the path from the mine to the bank, which is the path from the bank to the mine in reverse
     */
    @Override
    public void execute() {
        if(!ctx.movement.running()) {
            ctx.movement.running(ctx.movement.energyLevel() > Random.nextInt(40, 65));
        }
        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPathReverse(path);
        }
    }
}
