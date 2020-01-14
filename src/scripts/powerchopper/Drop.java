package scripts.powerchopper;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import scripts.Task;

public class Drop extends Task<ClientContext> {

    public Drop(ClientContext ctx){
        super(ctx);
    }

    public boolean activate(){
        return ctx.inventory.isFull();
    }

    public void execute(){
        if(ctx.game.tab() != Game.Tab.INVENTORY) {
            ctx.game.tab(Game.Tab.INVENTORY);
        }

        for(int i = 0; i < 28; i++){
            ctx.inventory.itemAt(i).interact("Drop");
        }
    }

}
