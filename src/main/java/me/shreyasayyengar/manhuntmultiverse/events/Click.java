package me.shreyasayyengar.manhuntmultiverse.events;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Click implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player eventPlayer = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {

            ItemStack currentItem = event.getCurrentItem();
            ItemMeta itemMeta = currentItem.getItemMeta();

            switch (itemMeta.getLocalizedName()) {
                case "manhunt.runner" -> {
                    ManhuntPlugin.manhuntGame.addRunner(eventPlayer.getUniqueId());
                    eventPlayer.sendMessage(Utility.colourise("&aYou are now a runner!"));
                    event.setCancelled(true);
                    eventPlayer.closeInventory();
                }

                case "manhunt.hunter" -> {
                    ManhuntPlugin.manhuntGame.addHunter(eventPlayer.getUniqueId());
                    eventPlayer.sendMessage(Utility.colourise("&cYou are now a hunter!"));
                    event.setCancelled(true);
                    eventPlayer.closeInventory();
                }

            }
        }
    }
}
