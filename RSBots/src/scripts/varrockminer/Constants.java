package scripts.varrockminer;

import org.powerbot.script.Tile;

public class Constants {

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

    //Copper, tin, iron
    public static final int[] ORE_ITEM_IDS = {436, 438, 440};

    // Radius around the player within which rocks will be selected to mine
    public static final int MINEABLES_DIST = 12;
    // Id of the player's animations
    public static final int ANIMATION_MINING = 628;
    public static final int ANIMATION_IDLE = -1;

    /* Hard-coded IDs of each ore rock in Varrock west mine */
    public static final int[] COPPER_IDS = {11161, 10943};
    public static final int[] TIN_IDS = {11361, 11360};
    public static final int[] IRON_IDS = {11365, 11364};
    /* Location of VW mine */
    public static final Tile mineLocation = new Tile(3285, 3365, 0);
    /* Location of Varrock West */
    public static final Tile bankLocation = new Tile(3253, 3421, 0);
    /* Location of the Grand Exchange */
    public static final Tile geLocation = new Tile(3163, 3486, 0);

    /* The path for the bot to follow back and forth between the mine and the bank. */
    public static final Tile[] PATH_VWEST_MINE = {new Tile(3253, 3421, 0), new Tile(3253, 3425, 0), new Tile(3253, 3428, 0), new Tile(3255, 3428, 0), new Tile(3257, 3428, 0), new Tile(3259, 3428, 0), new Tile(3261, 3428, 0), new Tile(3263, 3428, 0), new Tile(3266, 3428, 0), new Tile(3268, 3428, 0), new Tile(3271, 3428, 0), new Tile(3273, 3428, 0), new Tile(3275, 3428, 0), new Tile(3277, 3428, 0), new Tile(3278, 3425, 0), new Tile(3278, 3422, 0), new Tile(3279, 3420, 0), new Tile(3280, 3418, 0), new Tile(3282, 3416, 0), new Tile(3284, 3414, 0), new Tile(3286, 3412, 0), new Tile(3288, 3410, 0), new Tile(3288, 3407, 0), new Tile(3289, 3405, 0), new Tile(3290, 3401, 0), new Tile(3291, 3399, 0), new Tile(3291, 3395, 0), new Tile(3291, 3391, 0), new Tile(3291, 3387, 0), new Tile(3290, 3383, 0), new Tile(3289, 3379, 0), new Tile(3288, 3376, 0), new Tile(3287, 3372, 0), new Tile(3286, 3369, 0), new Tile(3285, 3365, 0)};

    public static final Tile[] PATH_VWEST_GE = {new Tile(3253, 3421, 0), new Tile(3253, 3425, 0), new Tile(3250, 3428, 0), new Tile(3246, 3428, 0), new Tile(3242, 3428, 0), new Tile(3238, 3428, 0), new Tile(3234, 3429, 0), new Tile(3230, 3429, 0), new Tile(3226, 3429, 0), new Tile(3222, 3429, 0), new Tile(3218, 3430, 0), new Tile(3214, 3432, 0), new Tile(3211, 3435, 0), new Tile(3207, 3437, 0), new Tile(3204, 3440, 0), new Tile(3201, 3443, 0), new Tile(3197, 3445, 0), new Tile(3193, 3446, 0), new Tile(3190, 3449, 0), new Tile(3187, 3452, 0), new Tile(3183, 3452, 0), new Tile(3179, 3451, 0), new Tile(3175, 3453, 0), new Tile(3172, 3456, 0), new Tile(3169, 3459, 0), new Tile(3166, 3462, 0), new Tile(3166, 3466, 0), new Tile(3166, 3470, 0), new Tile(3166, 3474, 0), new Tile(3165, 3478, 0), new Tile(3163, 3482, 0), new Tile(3163, 3486, 0)};

}
