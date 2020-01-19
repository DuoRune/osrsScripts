package scripts.VarrockMiner;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Path;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;
import scripts.Walker.Walker;

import java.util.EnumSet;

public class MoveToMine extends Task<ClientContext> {

//    private TilePath path;
    private final Walker walker;
    private Tile[] path;
    private Tile mineLocation = new Tile(3285, 3365, 0);

    public MoveToMine(ClientContext ctx, /*TilePath path*/ Tile[] tiles){
        super(ctx);
        this.walker = new Walker(ctx);
        this.path = tiles;
    }

    @Override
    public boolean activate() {
        Tile playerLocation = ctx.players.local().tile();
        return playerLocation.distanceTo(mineLocation) > 5 && !ctx.inventory.isFull();
    }

    @Override
    public void execute(){
//        if(path.valid()) {
//            if (path.traverse(/*EnumSet.of(Path.TraversalOption.HANDLE_RUN)*/)) {
//                Condition.wait(() -> !ctx.players.local().inMotion() || ctx.movement.destination().distanceTo(ctx.players.local()) < 3, 100, 20);
//                System.out.println("Traversing");
//            }else{
//                System.out.println("No traversal");
//            }
//        }else{
//            System.out.println("Invalid destination: " + path.next().toString());
//            for(Tile tile: path.toArray()){
//                System.out.println(tile.toString());
//            }
//        }

        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            walker.walkPath(path);
        }

    }
}
