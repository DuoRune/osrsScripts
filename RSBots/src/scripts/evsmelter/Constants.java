package scripts.evsmelter;

import org.powerbot.script.Tile;

public class Constants {

    public enum Selection {

        BRONZE("Bronze",2349, 14, BRONZE_REQ, 1/2f),
        IRON("Iron",2351, 15, IRON_REQ, 1),
        SILVER("Silver",2355, 16, SILVER_REQ, 1),
        STEEL("Steel", 2353, 17, STEEL_REQ, 1/3f),
        GOLD("Gold",2357, 18, GOLD_REQ, 1),
        MITHRIL("Mithril",2359, 19, MITHRIL_REQ, 1/5f);

        private String name;
        private int id;
        private int comp;
        private int[] reqOres;
        private float ratio;
        private Selection(String name, int id, int comp, int[] reqOres, float ratio){
            this.name = name;
            this.id = id;
            this.comp = comp;
            this.reqOres = reqOres;
            this.ratio = ratio;
        }
        public String metal(){
            return this.name;
        }
        public int id(){
            return this.id;
        }
        public int comp(){
            return this.comp;
        }
        public int[] reqOres(){
            return this.reqOres;
        }
        public float ratio(){
            return ratio;
        }

    }

    //copper, tin, iron, silver, gold, coal, mithril, adamantite, runite
    public static final int[] ORE_IDS = {436, 438, 440, 442, 444, 453, 447};
    private static final int[] BRONZE_REQ = {ORE_IDS[0], ORE_IDS[1]};
    private static final int[] IRON_REQ = {ORE_IDS[2]};
    private static final int[] SILVER_REQ = {ORE_IDS[3]};
    private static final int[] STEEL_REQ = {ORE_IDS[2], ORE_IDS[5]};
    private static final int[] GOLD_REQ = {ORE_IDS[4]};
    private static final int[] MITHRIL_REQ = {ORE_IDS[6], ORE_IDS[5]};

    public static final Tile[] BANK_TO_EV = {new Tile(3108, 3499, 0), new Tile(3105, 3499, 0), new Tile(3102, 3499, 0), new Tile(3099, 3497, 0), new Tile(3096, 3494, 0)};

    public static final Tile BANK = new Tile(3096, 3494, 0);
    public static final Tile EV = new Tile(3108, 3500, 0);

    public static final int FURNACE = 16469;
    public static final int FURNACE_WIDGET = 270;

    public static final int INVENTORY_SIZE = 28;


}
