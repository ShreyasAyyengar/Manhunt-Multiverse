package me.shreyasayyengar.manhuntmultiverse.objects;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;

public class Config {

    private static ManhuntPlugin main;

    public static void init(ManhuntPlugin main) {
        Config.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static int getHeadStart() {
        return main.getConfig().getInt("runner-head-start");
    }
}
