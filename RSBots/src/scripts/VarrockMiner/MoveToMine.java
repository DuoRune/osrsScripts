package scripts.VarrockMiner;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Path;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;

import java.util.EnumSet;

public class MoveToMine extends Task<ClientContext> {

    private TilePath path;
    private Tile mineTile = new Tile(3285, 3365, 0);

    public MoveToMine(ClientContext ctx, TilePath path){
        super(ctx);
        this.path = path;
    }

    @Override
    public boolean activate() {
        Tile thisTile = ctx.players.local().tile();
        if(!thisTile.equals(mineTile) && !ctx.inventory.isFull()){
            return true;
        }
        return false;
    }

    @Override
    public void execute(){
        if(path.valid()) {
            if (path.traverse(EnumSet.of(Path.TraversalOption.HANDLE_RUN))) {
                Condition.wait(() -> !ctx.players.local().inMotion() || ctx.movement.destination().distanceTo(ctx.players.local()) < 3, 100, 20);
                System.out.println("Traversing");
            }else{
                System.out.println("No traversal");
            }
        }else{
            System.out.println("Invalid destination: " + path.next().toString());
        }
    }
}
