package scripts.furbuyer;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GeItem;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Fur Buyer", properties = "author=DuoRune; topic=0; client=4;", description="Buys and banks furs")
public class FurBuyer extends PollingScript<ClientContext> {

    public static final int BEAR_FUR_COST = 20;
    public static final int BEAR_FUR = 948;
    public static final int BARAEK = 547;
    public static final String[] CHAT_OPTIONS = {"Can you sell me some furs?", "Yeah, okay, here you go."};
    public static final Tile[] PATH = {
            new Tile(3186, 3437, 0),
            new Tile(3186, 3432, 0),
            new Tile(3193, 3429, 0),
            new Tile(3203, 3429, 0),
            new Tile(3212, 3433, 0),
            new Tile(3216, 3434, 0)
    };

    private TilePath pathToBank, pathToTrader;
    private int fursBought = 0, furGePrice = 0;

    List<Task> taskList = new ArrayList<>();

    @Override
    public void start() {
        pathToTrader = new TilePath(ctx, PATH);
        pathToBank = new TilePath(ctx, PATH).reverse();
        furGePrice = new GeItem(BEAR_FUR).price;

        taskList.addAll(Arrays.asList(new WalkToTrader(ctx, pathToTrader), new BuyFurs(ctx), new WalkToBank(ctx, pathToBank), new Bank(ctx)));
    }

    @Override
    public void poll(){
        if(ctx.inventory.select().id(995).count(true) < 20){
            System.out.println("No more gold!");
            ctx.controller.stop();
        }
        for(Task task: taskList){
            if(task.activate()){
                task.execute();
            }
        }
    }

}
