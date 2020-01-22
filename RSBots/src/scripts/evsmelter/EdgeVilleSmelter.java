package scripts.evsmelter;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;

import scripts.Task;
import scripts.helper.Walker;
import scripts.varrockminer.TraverseVWestGE;

import static scripts.evsmelter.Constants.*;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "EdgeVille Smelter", properties = "author=Octa; topic=0; client=4;", description = "Smelts various bars at edgeville furnace")
public class EdgeVilleSmelter extends PollingScript<ClientContext> {

    private List<Task> taskList = new ArrayList<>();
    Walker walker = new Walker(ctx);

    @Override
    public void start() {
        taskList.add(new Bank(ctx));
        taskList.add(new Smelt(ctx));
        taskList.add(new Traverse(ctx));
    }

    @Override
    public void poll() {
        for(Task task: taskList){
            if(task.activate()){
                task.execute();
                break;
            }
        }
    }
}
