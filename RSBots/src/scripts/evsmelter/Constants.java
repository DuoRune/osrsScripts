package scripts.evsmelter;

import org.powerbot.script.Tile;

public class Constants {

    public enum Selection {

    }

    //Copper, Tin
    public static final int[] ORE_IDS = {436, 438};
    //Bronze, Iron, ...
    public static final int[] BAR_IDS = {2349};
    public static final int[] COMPONENT_IDS = {14};

    public static final Tile[] GE_TO_EV = {new Tile(3162, 3489, 0), new Tile(3162, 3485, 0), new Tile(3162, 3481, 0), new Tile(3163, 3477, 0), new Tile(3165, 3473, 0), new Tile(3165, 3469, 0), new Tile(3165, 3465, 0), new Tile(3161, 3465, 0), new Tile(3157, 3465, 0), new Tile(3153, 3463, 0), new Tile(3149, 3464, 0), new Tile(3145, 3465, 0), new Tile(3141, 3465, 0), new Tile(3137, 3465, 0), new Tile(3136, 3469, 0), new Tile(3135, 3473, 0), new Tile(3131, 3475, 0), new Tile(3129, 3479, 0), new Tile(3129, 3483, 0), new Tile(3128, 3487, 0), new Tile(3128, 3491, 0), new Tile(3130, 3495, 0), new Tile(3130, 3499, 0), new Tile(3130, 3503, 0), new Tile(3132, 3507, 0), new Tile(3132, 3511, 0), new Tile(3134, 3515, 0), new Tile(3130, 3516, 0), new Tile(3126, 3516, 0), new Tile(3122, 3515, 0), new Tile(3119, 3511, 0), new Tile(3116, 3508, 0), new Tile(3114, 3504, 0), new Tile(3110, 3504, 0), new Tile(3108, 3500, 0)};

    public static final Tile GE = new Tile(3162, 3489, 0);
    public static final Tile EV = new Tile(3108, 3500, 0);

    public static final int FURNACE = 16469;


}
