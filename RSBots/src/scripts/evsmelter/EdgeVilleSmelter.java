package scripts.evsmelter;

import org.powerbot.script.ClientContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import scripts.Task;

import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "EdgeVille Smelter", properties = "author=Octa; topic=0; client=4;", description = "Smelts various bars at edgeville furnace")
public class EdgeVilleSmelter extends PollingScript<ClientContext> {

    private List<Task> taskList = new ArrayList<>();

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void poll() {

    }
}
