package me.shreyasayyengar.manhuntmultiverse.utils;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility {

    private Utility() {}

    public static boolean canJoin = true;

    public static String colourise(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void openStartGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, colourise("&6Manhunt"));

        ItemStack hunter = new ItemStack(Material.IRON_SWORD);
        ItemMeta hunterItemMeta = hunter.getItemMeta();
        hunterItemMeta.setDisplayName(colourise("&cClick this to become a hunter!"));
        hunterItemMeta.setLocalizedName("manhunt.hunter");
        hunter.setItemMeta(hunterItemMeta);

        ItemStack runner = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta runnerItemMeta = runner.getItemMeta();
        runnerItemMeta.setDisplayName(colourise("&aClick this to become a runner!"));
        runnerItemMeta.setLocalizedName("manhunt.runner");
        runner.setItemMeta(runnerItemMeta);

        inventory.setItem(3, hunter);
        inventory.setItem(5, runner);

        player.openInventory(inventory);
    }

    public static boolean isGameRunning() {
        return ManhuntPlugin.manhuntGame != null;
    }
}
