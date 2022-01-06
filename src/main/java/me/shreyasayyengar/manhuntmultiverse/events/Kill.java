package me.shreyasayyengar.manhuntmultiverse.events;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Kill implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity() instanceof EnderDragon dragon && event.getEntity().getKiller() != null) {

            Player player = dragon.getKiller();

            if (!Utility.isGameRunning()) return;

            if (ManhuntPlugin.manhuntGame.getHunters().contains(player.getUniqueId())) {
                ManhuntPlugin.manhuntGame.endGame(false);
            } else if (ManhuntPlugin.manhuntGame.getRunners().contains(player.getUniqueId())){
                ManhuntPlugin.manhuntGame.endGame(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (!Utility.isGameRunning()) return;

        if (ManhuntPlugin.manhuntGame.getRunners().contains(player.getUniqueId())) {
            Bukkit.getOnlinePlayers().forEach(loopedPlayer -> loopedPlayer.sendMessage(Utility.colourise("&c" + player.getName() + " has LOST the manhunt!")));
            ManhuntPlugin.manhuntGame.endGame(false);
        }
    }
}
