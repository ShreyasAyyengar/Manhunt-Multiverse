package me.shreyasayyengar.manhuntmultiverse.events;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortal implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {

        if (event.getTo().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {

            World manhunt_world = ManhuntPlugin.getMultiverseCore().getMVWorldManager().getMVWorld("manhunt_world").getCBWorld();

            Location spawnLocation;

            if (event.getPlayer().getBedSpawnLocation() == null) {
                spawnLocation = manhunt_world.getSpawnLocation();
            } else {
                spawnLocation = event.getPlayer().getBedSpawnLocation();
            }

            event.setTo(spawnLocation);
        }

        if (event.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER)) {

            World manhunt_world_nether = ManhuntPlugin.getMultiverseCore().getMVWorldManager().getMVWorld("manhunt_world_nether").getCBWorld();
            Location spawnLocation = manhunt_world_nether.getSpawnLocation();
            event.setTo(spawnLocation);
        }

        if (event.getTo().getWorld().getEnvironment().equals(World.Environment.THE_END)) {

            World manhunt_world_the_end = ManhuntPlugin.getMultiverseCore().getMVWorldManager().getMVWorld("manhunt_world_the_end").getCBWorld();
            Location spawnLocation = manhunt_world_the_end.getSpawnLocation();
            event.setTo(spawnLocation);
        }
    }
}
