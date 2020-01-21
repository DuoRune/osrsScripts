package scripts.varrockminer;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Player;

import scripts.Task;
import static scripts.varrockminer.Constants.*;
import static scripts.varrockminer.GlobalVariables.*;

public class MineSelectedRocks extends Task<ClientContext> {

    /* User selection of ore to mine */
    private Selection miningSelection;
    /* List of each list of ore IDs, will be used to reduce redundancy in execute() */
    private int[][] ORE_IDS = {COPPER_IDS, TIN_IDS, IRON_IDS};

    public MineSelectedRocks(ClientContext ctx, Selection miningSelection){
        super(ctx);
        this.miningSelection = miningSelection;
    }

    /* activate this task if inventory is not full and player is closer than 8 tiles from the mine and if the player is idling */
    @Override
    public boolean activate(){
        return !ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(mineLocation) < 8 && ctx.players.local().animation() == -1 && mining;
    }

    /* Waits either 8 seconds or until bot has finished mining the current rock.
     * Locates the nearest player
     * Locates the nearest rock of specified ore
     * If standing on top of the nearest player, select a farther rock of specified ore
     * Else, select the nearest rock of specified ore
     * Mine selected rock
     */
    @Override
    public void execute(){
        Condition.wait(() -> ctx.players.local().animation() == -1, 400, 20);
        ctx.players.select().nearest().select(new Filter<Player>() {
            @Override
            public boolean accept(Player player) {
                return !player.equals(ctx.players.local());
            }
        });
        GameObject rock;
        ctx.objects.select().id(ORE_IDS[miningSelection.id()]).nearest();
        if(ctx.players.poll().tile().equals(ctx.players.local().tile())){
            rock = ctx.objects.limit(2, 3).poll();
            System.out.println("Selected farther rock to avoid standing on another player.");
            System.out.println("Rock loc: " + rock.tile());
        }else{
            rock = ctx.objects.select().id(ORE_IDS[miningSelection.id()]).nearest().poll();
        }
        rock.interact("Mine");
    }
}
