package scripts.varrockminer;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GeItem;
import scripts.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Script.Manifest(name = "Varrock Miner", properties = "author=DuoRune; topic=0; client=4;", description = "Start at Varrock mine or varrock west bank. Auto mines specified ore and banks it.")
public class VarrockMiner extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    /* Enum for tracking selected ore by the user.
     * id is for reducing the redundancy in code for the execute() method in MineSelectedRocks.
     */
    public enum Selection{
        COPPER(0), TIN(1), IRON(2);

        private final int id;
        private Selection(int id){
            this.id = id;
        }
        public int id(){
            return id;
        }
    }

    /* The path for the bot to follow back and forth between the mine and the bank.
     */
    public static final Tile[] PATH = {
            new Tile(3253, 3421, 0),
            new Tile(3253, 3425, 0),
            new Tile(3253, 3428, 0),
            new Tile(3255, 3428, 0),
            new Tile(3257, 3428, 0),
            new Tile(3259, 3428, 0),
            new Tile(3261, 3428, 0),
            new Tile(3263, 3428, 0),
            new Tile(3266, 3428, 0),
            new Tile(3268, 3428, 0),
            new Tile(3271, 3428, 0),
            new Tile(3273, 3428, 0),
            new Tile(3275, 3428, 0),
            new Tile(3277, 3428, 0),
            new Tile(3278, 3425, 0),
            new Tile(3278, 3422, 0),
            new Tile(3279, 3420, 0),
            new Tile(3280, 3418, 0),
            new Tile(3282, 3416, 0),
            new Tile(3284, 3414, 0),
            new Tile(3286, 3412, 0),
            new Tile(3288, 3410, 0),
            new Tile(3288, 3407, 0),
            new Tile(3289, 3405, 0),
            new Tile(3290, 3401, 0),
            new Tile(3291, 3399, 0),
            new Tile(3291, 3395, 0),
            new Tile(3291, 3391, 0),
            new Tile(3291, 3387, 0),
            new Tile(3290, 3383, 0),
            new Tile(3289, 3379, 0),
            new Tile(3288, 3376, 0),
            new Tile(3287, 3372, 0),
            new Tile(3286, 3369, 0),
            new Tile(3285, 3365, 0)
    };


    /* List of each task to be executed */
    private List<Task> taskList = new ArrayList<>();

    private String selection;
    private int startingXp;
    private int totalProfit;

    /* copper, tin, iron */
    private int[] minedOres;
    private int[] orePrices;
    private final int[] ORE_ITEM_IDS = {436, 438, 440};

    /* Gets input from the user with regards to which ore to mine.
     * Adds each task to the task list.
     */
    @Override
    public void start(){

        String[] options = {"Copper", "Tin", "Iron"};
        selection = "" + (String) JOptionPane.showInputDialog(null, "Which ore will the bot mine?", "Varrock Miner", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        startingXp = ctx.skills.experience(Constants.SKILLS_MINING);
        totalProfit = 0;

        minedOres = new int[3];
        orePrices = new int[3];
        for(int i = 0; i < 2; i++){
            orePrices[i] = GeItem.getPrice(ORE_ITEM_IDS[i]);
        }

        taskList.add(new MoveToMine(ctx, PATH));
        taskList.add(new MineSelectedRocks(ctx, selection.equals("Copper")? Selection.COPPER : selection.equals("Tin")? Selection.TIN : Selection.IRON));
        taskList.add(new MoveToBank(ctx, PATH));
        taskList.add(new BankOres(ctx));
    }

    /* Iterates over each task, executing them as necessary.
     * Break ensures that only the first valid task will be executed.
     */
    @Override
    public void poll() {
        for(Task task: taskList){
            if(task.activate()) {
                task.execute();
                break;
            }
        }
    }

    /* Records time and calculates useful values to the user, and displays them with a basic graphical display */
    public void repaint(Graphics graphics){

        Condition.wait(() -> selection != null, 100, 100);

        long millis = getTotalRuntime();
        long seconds = (millis/1000) % 60;
        long minutes = (millis/(1000 * 60)) % 60;
        long hours = (millis/(1000 * 60 * 60)) % 60;

        int xpEarned = ctx.skills.experience(Constants.SKILLS_MINING) - startingXp;

        totalProfit = (minedOres[0] * orePrices[0]) + (minedOres[1] * orePrices[1]) + (minedOres[2] * orePrices[2]);

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 180, 140);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(0, 0, 180, 140);

        g.drawString("Varrock Miner", 10, 15);
        g.drawString("Runtime: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 10, 35);
        g.drawString("Xp/Hr: " + (int) (xpEarned * (3600000D / millis)), 10, 55);
        g.drawString("Xp earned: " + xpEarned, 10, 75);
        g.drawString("Profit/Hr: " + (int) (totalProfit * (3600000D / millis)), 10, 95);
        g.drawString("Cur. profit: " + totalProfit, 10, 115);
    }

    public void messaged(MessageEvent messageEvent){
        String message = messageEvent.text();
        if(message.contains("copper")){
            minedOres[0]++;
        }else if(message.contains("tin")){
            minedOres[1]++;
        }else if(message.contains("iron")){
            minedOres[2]++;
        }
    }
}
