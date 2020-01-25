package scripts.evsmelter;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GeItem;
import static org.powerbot.script.rt4.Constants.*;

import scripts.Task;
import scripts.helper.Walker;

import javax.swing.*;

import static scripts.evsmelter.Constants.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "EdgeVille Smelter", properties = "author=Octa; topic=0; client=4;", description = "Smelts various bars at edgeville furnace")
public class EdgeVilleSmelter extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    private List<Task> taskList = new ArrayList<>();
    Walker walker = new Walker(ctx);

    private String selection;
    private Selection bar;

    private int startingXp;
    private int smeltedBars;
    private int trueProfit;
    private int relativeProfit;
    private int barPrice;
    private int oresPrice;

    @Override
    public void start() {

        startingXp = ctx.skills.experience(SKILLS_SMITHING);
        smeltedBars = 0;

        String[] options = {"Bronze", "Iron", "Silver", "Steel", "Gold", "Mithril"};
        selection = "" + (String) JOptionPane.showInputDialog(null, "Which metal to smelt?", "EV Smelter", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if(selection.equals(options[0])){
            bar = Selection.BRONZE;
        }else if(selection.equals(options[1])){
            bar = Selection.IRON;
        }else if(selection.equals(options[2])){
            bar = Selection.SILVER;
        }else if(selection.equals(options[3])){
            bar = Selection.STEEL;
        }else if(selection.equals(options[4])){
            bar = Selection.GOLD;
        }else if(selection.equals(options[5])){
            bar = Selection.MITHRIL;
        }

        barPrice = GeItem.getPrice(bar.id());
        oresPrice = 0;
        for(int ore: bar.reqOres()){
            oresPrice += GeItem.getPrice(ore);
        }


        taskList.add(new Bank(ctx, bar));
        taskList.add(new Smelt(ctx, bar));
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

    @Override
    public void repaint(Graphics graphics){

        Condition.wait(() -> selection != null, 100, 100);

        long millis = getTotalRuntime();
        long seconds = (millis/1000) % 60;
        long minutes = (millis/(1000 * 60)) % 60;
        long hours = (millis/(1000 * 60 * 60)) % 60;

        int xpEarned = ctx.skills.experience(SKILLS_SMITHING) - startingXp;

        trueProfit = barPrice * smeltedBars;
        relativeProfit = (barPrice - oresPrice) * smeltedBars;

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 180, 145);

        g.setColor(new Color(255, 0, 0));
        g.drawRect(0, 0, 180, 145);

        g.drawString("Varrock Miner", 10, 15);
        g.drawString("Runtime: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 10, 35);
        g.drawString("Xp/Hr: " + (int) (xpEarned * (3600000D / millis)), 10, 55);
        g.drawString("Xp earned: " + xpEarned, 10, 75);
        g.drawString("Profit/Hr(true): " + (int) (trueProfit * (3600000D / millis)), 10, 95);
        g.drawString("Cur. Profit(relative): " + relativeProfit, 10, 115);
        g.drawString("Cur. profit(true): " + trueProfit, 10, 135);
    }

    @Override
    public void messaged(MessageEvent messageEvent){
        String message = messageEvent.text();
        if(message.contains("You retrieve")){
            smeltedBars++;
        }
    }

}
