package scripts.powerchopper;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import scripts.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Power Chopper", properties = "author=DuoRune; topic=0; client=4;", description = "test")
public class PowerChopper extends PollingScript<ClientContext> {

    List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start(){
        taskList.addAll(Arrays.asList(new Chop(ctx), new Drop(ctx)));
    }

    @Override
    public void poll(){
        for(Task task: taskList){
            if(task.activate()){
                task.execute();
            }
        }
    }
}

