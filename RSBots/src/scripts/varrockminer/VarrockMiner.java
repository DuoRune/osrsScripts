package scripts.varrockminer;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GeItem;

import scripts.Task;
import static scripts.varrockminer.Constants.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Script.Manifest(name = "Varrock Miner", properties = "author=DuoRune; topic=0; client=4;", description = "Start at Varrock mine or varrock west bank. Auto mines specified ore and banks it.")
public class VarrockMiner extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    /* List of each task to be executed */
    private List<Task> taskList = new ArrayList<>();

    private String selection;
    private int startingXp;
    private int totalProfit;

    /* copper, tin, iron */
    private int[] minedOres;
    private int[] orePrices;
    /* bronze mode = true */
    private boolean bronzeMode;

    /* Gets input from the user with regards to which ore to mine.
     * Adds each task to the task list.
     */
    @Override
    public void start(){

        String[] options = {"Copper", "Tin", "Iron", "Bronze"};
        selection = "" + (String) JOptionPane.showInputDialog(null, "Which task will the bot accomplish?", "Varrock Miner", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        startingXp = ctx.skills.experience(Constants.SKILLS_MINING);
        totalProfit = 0;

        minedOres = new int[3];
        orePrices = new int[3];
        for(int i = 0; i < 2; i++){
            orePrices[i] = GeItem.getPrice(ORE_ITEM_IDS[i]);
        }

        taskList.add(new MoveToMine(ctx));
        taskList.add(new MineSelectedRocks(ctx, selection.equals("Copper")? Selection.COPPER : selection.equals("Tin")? Selection.TIN : Selection.IRON));
        taskList.add(new MoveToBank(ctx));
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

    /* Uses incoming messages to tally total ore mined */
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
