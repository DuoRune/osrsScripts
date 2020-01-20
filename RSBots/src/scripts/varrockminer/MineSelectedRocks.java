package scripts.varrockminer;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Player;
import scripts.Task;

import scripts.varrockminer.VarrockMiner.Selection;

public class MineSelectedRocks extends Task<ClientContext> {

    /* Tile which represents the location of the mine */
    private Tile mineLocation = new Tile(3285, 3365, 0);
    /* User selection of ore to mine */
    private Selection miningSelection;
    /* Hard-coded IDs of each ore rock in Varrock west */
    private int[] COPPER_IDS = {11161, 10943};
    private int[] TIN_IDS = {11361, 11360};
    private int[] IRON_IDS = {11365, 11364};
    /* List of each list of ore IDs, will be used to reduce redundancy in execute() */
    private int[][] ORE_IDS = {COPPER_IDS, TIN_IDS, IRON_IDS};

    public MineSelectedRocks(ClientContext ctx, Selection miningSelection){
        super(ctx);
        this.miningSelection = miningSelection;
    }

    @Override
    public boolean activate(){
        return !ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(mineLocation) < 8 && ctx.players.local().animation() == -1;
    }

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
        if(ctx.players.poll().tile().equals(ctx.players.local().tile())){
            rock = ctx.objects.select().id(ORE_IDS[miningSelection.id()]).nearest().limit(2, 3).poll();
            System.out.println("Selected farther rock to avoid standing on another player.");
            System.out.println("Rock loc: " + rock.tile());
        }else{
            rock = ctx.objects.select().id(ORE_IDS[miningSelection.id()]).nearest().poll();
        }
        rock.interact("Mine");
    }
}
