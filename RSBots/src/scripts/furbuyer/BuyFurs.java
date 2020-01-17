package scripts.furbuyer;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ChatOption;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;
import scripts.Task;

import java.util.concurrent.Callable;

import static scripts.furbuyer.FurBuyer.BARAEK;
import static scripts.furbuyer.FurBuyer.CHAT_OPTIONS;

public class BuyFurs extends Task<ClientContext> {

    public BuyFurs(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        System.out.println("Buy Furs: " + !ctx.npcs.select().id(BARAEK).within(5).isEmpty());
        return !ctx.bank.opened() && !ctx.inventory.isFull() && !ctx.npcs.select().id(BARAEK).within(5).isEmpty();
    }

    @Override
    public void execute() {
        if (!ctx.chat.chatting()) {
            //... talk to the trader
            final Npc trader = ctx.npcs.select().id(BARAEK).nearest().poll();
            if (trader.interact("Talk-to")) {
                //wait until the chat interface comes up (prevents spam clicking) for a maximum of 2500ms (250*10)
                //Condition.wait(condition to wait for, how long to sleep for each iteration, how many iterations to go through)
                //sleeps until call() returns true, then wait() returns true;
                //or times out and returns false
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.chat.chatting();
                    }
                }, 250, 10);
            }
        }
        //canContinue() returns if there is a "continue" button available in the chatting interface
        //if there is a continue button...
        else if (ctx.chat.canContinue()) {
            //...continue through it
            //clickContinue() accepts a boolean argument
            //pass 'true' to use a key (press Enter), pass 'false' to use the mouse
            ctx.chat.clickContinue(true);
            //sleep for 350-500ms
            Condition.sleep(Random.nextInt(350, 500));
        }
        //Chat query works like any other query, except for the chat options
        //finds all chat options that match the CHAT_OPTIONS argument
        //if there is an option that matches...
        else if (!ctx.chat.select().text(CHAT_OPTIONS).isEmpty()) {
            final ChatOption option = ctx.chat.poll();
            //... select that option (true to use key instead of mouse)
            if (option.select(true)) {
                //sleep until the interface is completed
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !option.valid();
                    }
                }, 250, 10);
            }
        }
    }
}
