package scripts.VarrockMiner;


import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.TilePath;
import scripts.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Script.Manifest(name = "Varrock Miner", properties = "author=DuoRune; topic=0; client=4;", description = "Start at Varrock mine or varrock west bank. Auto mines specified ore and banks it.")
public class VarrockMiner extends PollingScript<ClientContext> {

    public static final Tile[] PATH = {
            new Tile(3253, 3428, 0),
            new Tile(3259, 3428, 0),
            new Tile(3266, 3428, 0),
            new Tile(3271, 3428, 0),
            new Tile(3277, 3428, 0),
            new Tile(3280, 3418, 0),
            new Tile(3289, 3405, 0),
            new Tile(3291, 3387, 0),
            new Tile(3288, 3376, 0),
            new Tile(3285, 3365, 0)
    };

    public TilePath pathToMine, pathToBank;

    private List<Task> taskList = new ArrayList<>();

    @Override
    public void start(){

        pathToMine = new TilePath(ctx, PATH).randomize(1, 1);
        pathToBank = new TilePath(ctx, PATH).reverse().randomize(1, 1);

        taskList.addAll(Arrays.asList(new MoveToMine(ctx, pathToBank) ));
    }

    @Override
    public void poll() {
        for(Task task: taskList){
            if(task.activate())
                task.execute();
        }
    }
}
/*
1. If
    a) inventory is not full
    b) player not at the mine
    then
    a) move to the mine

    If
    a) inventory is not full
    b) player is at the mine
    then
    a) mine selected rocks

    If
    a) inventory is full
    b) player is at the mine
    then
    a) move to the bank

    If
    a) inventory is full
    b) player is at the bank
    then
    a) bank the ore
 */

