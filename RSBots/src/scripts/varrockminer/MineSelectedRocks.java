package scripts.varrockminer;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
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
        ctx.objects.select((GameObject rock) -> {
            ctx.players.select().within(MINEABLES_DIST + 1).select((Player player) -> !player.equals(ctx.players.local()));
            Tile[] adjLocs = new Tile[4];
            adjLocs[0] = new Tile(rock.tile().x() + 1, rock.tile().y());
            adjLocs[1] = new Tile(rock.tile().x() -1, rock.tile().y());
            adjLocs[2] = new Tile(rock.tile().x(), rock.tile().y() + 1);
            adjLocs[3] = new Tile(rock.tile().x(), rock.tile().y() - 1);
            for(Player player : ctx.players){
                for(int n = 0; n < adjLocs.length; n++){
                    if(player.tile().equals(adjLocs[n])){
                        return false;
                    }
                }
            }
            return true;
        }).id(ORE_IDS[miningSelection.id()]).within(MINEABLES_DIST).nearest();
        if(ctx.objects.isEmpty()){
            //TODO: Hop worlds
        }
        GameObject rock = ctx.objects.nearest().poll();
        rock.interact("Mine");
        Condition.wait(() -> ctx.players.local().animation() != ANIMATION_MINING, 200, 10);
    }
}
