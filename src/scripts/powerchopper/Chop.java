package scripts.powerchopper;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import scripts.Task;

public class Chop extends Task<ClientContext> {
    private int[] treeIds = {1278, 1276, 1315};

    public Chop(ClientContext ctx){
        super(ctx);
    }

    public boolean activate(){
        boolean inv = !ctx.inventory.isFull();
        boolean trees = !ctx.objects.select().id(treeIds).isEmpty();
        boolean idling = ctx.players.local().animation() == -1;
        return inv && trees && idling;
    }

    public void execute(){
        GameObject tree = ctx.objects.nearest().poll();
        if(tree.inViewport() && ctx.players.local().animation() == -1){
            tree.interact("Chop");
        }else{
            ctx.movement.step(tree);
            ctx.camera.turnTo(tree);
        }
    }
}
