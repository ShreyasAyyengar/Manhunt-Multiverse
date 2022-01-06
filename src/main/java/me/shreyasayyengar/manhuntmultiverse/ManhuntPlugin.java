package me.shreyasayyengar.manhuntmultiverse;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.shreyasayyengar.manhuntmultiverse.commands.ManhuntCommand;
import me.shreyasayyengar.manhuntmultiverse.events.Click;
import me.shreyasayyengar.manhuntmultiverse.events.Kill;
import me.shreyasayyengar.manhuntmultiverse.objects.Config;
import me.shreyasayyengar.manhuntmultiverse.objects.ManhuntGame;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ManhuntPlugin extends JavaPlugin {

    public static ManhuntGame manhuntGame;
    private static ManhuntPlugin INSTANCE;
    private static MultiverseCore multiverseCore;

    public static ManhuntPlugin getInstance() {
        return INSTANCE;
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }


    @Override
    public void onEnable() {
        ManhuntPlugin.INSTANCE = this;

        getLogger().info(Utility.colourise("&6Plugin started with no errors"));

        registerCommands();
        registerEvents();
        registerMultiverse();
        Config.init(this);
    }

    private void registerMultiverse() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            getLogger().severe("Multiverse-Core plugin not found!");
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        }
    }


    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new Click(), this);
        this.getServer().getPluginManager().registerEvents(new Kill(), this);
    }

    private void registerCommands() {
        this.getCommand("manhunt").setExecutor(new ManhuntCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
