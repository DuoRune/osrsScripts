package scripts.varrockminer;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import scripts.Task;

import java.util.List;

import scripts.varrockminer.VarrockMiner.Selection;

public class MineSelectedRocks extends Task<ClientContext> {

    private Tile mineLocation = new Tile(3285, 3365, 0);
    private Selection miningSelection;
    private int[] COPPER_IDS = {11161, 10943};
    private int[] TIN_IDS = {11361, 11360};
    private int[] IRON_IDS = {11365, 11364};
    private int[][] ORE_IDS = {COPPER_IDS, TIN_IDS, IRON_IDS};

    public MineSelectedRocks(ClientContext ctx, Selection miningSelection){
        super(ctx);
        this.miningSelection = miningSelection;
    }

    @Override
    public boolean activate(){
        return !ctx.inventory.isFull() && ctx.players.local().tile().distanceTo(mineLocation) < 8;
    }

    @Override
    public void execute(){
        GameObject rock = ctx.objects.select().id(ORE_IDS[miningSelection.id()]).nearest().poll();
        rock.interact("Mine");
        Condition.wait(() -> ctx.players.local().animation() == -1, 400, 10);
    }

}
