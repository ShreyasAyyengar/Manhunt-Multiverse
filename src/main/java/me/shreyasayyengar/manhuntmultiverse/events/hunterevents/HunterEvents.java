package me.shreyasayyengar.manhuntmultiverse.events.hunterevents;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HunterEvents implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!Utility.isGameRunning()) return;


        switch (ManhuntPlugin.manhuntGame.getState()) {
            case COUNTDOWN -> {
                if (ManhuntPlugin.manhuntGame.getAllPlayers().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
//                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100000, 100, false, false, false));
//                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 100, false, false, false));
                }
            }

            case HEAD_START -> {
                if (ManhuntPlugin.manhuntGame.getHunters().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100000, 100, false, false, false));
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 100, false, false, false));
                }
            }
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (!Utility.isGameRunning()) return;

        switch (ManhuntPlugin.manhuntGame.getState()) {
            case COUNTDOWN -> {
                if (ManhuntPlugin.manhuntGame.getAllPlayers().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }

            case HEAD_START -> {
                if (ManhuntPlugin.manhuntGame.getHunters().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockBreakEvent event) {

        if (!Utility.isGameRunning()) return;

        switch (ManhuntPlugin.manhuntGame.getState()) {
            case COUNTDOWN -> {
                if (ManhuntPlugin.manhuntGame.getAllPlayers().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }

            case HEAD_START -> {
                if (ManhuntPlugin.manhuntGame.getHunters().contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
