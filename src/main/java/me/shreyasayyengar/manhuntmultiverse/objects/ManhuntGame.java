package me.shreyasayyengar.manhuntmultiverse.objects;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import me.shreyasayyengar.manhuntmultiverse.events.hunterevents.HunterEvents;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ManhuntGame implements Listener {

    private final Set<UUID> hunters = new HashSet<>();
    private final Set<UUID> runners = new HashSet<>();
    private final Set<UUID> allPlayers = new HashSet<>();

    private final Location spawnLocation;

    private GameState state;

    public ManhuntGame() {
        createWorlds();
        this.state = GameState.BEGUN;

        spawnLocation = ManhuntPlugin.getMultiverseCore().getMVWorldManager().getMVWorld("manhunt_world").getCBWorld().getSpawnLocation();

        gatherData();
        startTask();
    }

    private void startTask() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (runners.size() + hunters.size() == Bukkit.getOnlinePlayers().size()) {
                    teleportPlayers();
                    registerEvents();
                    startInitialCountdown();
                    cancel();
                }
            }
        }.runTaskTimer(ManhuntPlugin.getInstance(), 0L, 20L);
    }


    private void startInitialCountdown() {
        this.state = GameState.COUNTDOWN;
        ManhuntPlugin.getInstance().getServer().getPluginManager().registerEvents(new HunterEvents(), ManhuntPlugin.getInstance());

        final int[] seconds = {5};
        new BukkitRunnable() {
            @Override
            public void run() {

                if (seconds[0] > 1) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(Utility.colourise("&cGame begins in:"), Utility.colourise("&e" + seconds[0] + " seconds!"), 0, 25, 0));
                }

                if (seconds[0] == 1) {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(Utility.colourise("&cGame begins in:"), Utility.colourise("&e" + seconds[0] + " second!"), 0, 25, 0));
                }

                if (seconds[0] == 0) {

                    startPregame();
                    cancel();
                }

                seconds[0]--;

            }
        }.runTaskTimer(ManhuntPlugin.getInstance(), 20L, 20L);
    }

    private void startPregame() {

        this.state = GameState.HEAD_START;
//        ((CraftServer) ManhuntPlugin.getInstance().getServer()).getHandle().b().g(true);
        ((CraftServer) ManhuntPlugin.getInstance().getServer()).getHandle().getServer().setAllowFlight(true);

        final int[] head_start = {Config.getHeadStart()};
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(Utility.colourise("&cThe runners now have &l" + head_start[0] + "&c to run!"));
            player.getInventory().clear();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + player.getName() + " everything");
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                if (head_start[0] == 0) {
                    startRealGame();
                    cancel();
                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Utility.colourise("&e" + head_start[0] + " seconds until hunters can run!")));
                }

                head_start[0]--;
            }
        }.runTaskTimer(ManhuntPlugin.getInstance(), 0L, 20L);
    }

    private void startRealGame() {
        this.state = GameState.BEGUN;
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
    }

    private void teleportPlayers() {
        allPlayers.forEach(uuid -> Bukkit.getPlayer(uuid).teleport(spawnLocation));
    }

    private void registerEvents() {
        ManhuntPlugin.getInstance().getServer().getPluginManager().registerEvents(this, ManhuntPlugin.getInstance());
    }

    private void createWorlds() {
        ManhuntPlugin.getMultiverseCore().getMVWorldManager().addWorld(
                "manhunt_world",
                World.Environment.NORMAL,
                null,
                WorldType.NORMAL,
                true,
                null
        );

        ManhuntPlugin.getMultiverseCore().getMVWorldManager().addWorld(
                "manhunt_world_nether",
                World.Environment.NETHER,
                null,
                WorldType.NORMAL,
                true,
                null
        );

        ManhuntPlugin.getMultiverseCore().getMVWorldManager().addWorld(
                "manhunt_world_the_end",
                World.Environment.THE_END,
                null,
                WorldType.NORMAL,
                true,
                null
        );
    }

    private void gatherData() {

        if (!Utility.canJoin) return;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Utility.openStartGUI(onlinePlayer);
        }
    }

    public void endGame(boolean didRunnerWin) {
        allPlayers.forEach(uuid -> {
            if (Bukkit.getPlayer(uuid) != null) {
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;

                player.sendTitle(Utility.colourise("&cGame Over!"), null, 0, 100, 0);

                if (didRunnerWin) {
                    player.sendMessage(Utility.colourise("&aThe manhunt is over and &lthe runners won!"));
                } else {
                    player.sendMessage(Utility.colourise("&cThe manhunt is over and &lthe hunters won!"));
                }

                getRunners().clear();
                getHunters().clear();
                allPlayers.clear();
                ManhuntPlugin.manhuntGame = null;
            }
        });

        deleteWorlds();
    }

    public void endGameGenerically() {
        allPlayers.forEach(uuid -> {
            if (Bukkit.getPlayer(uuid) != null) {
                Player player = Bukkit.getPlayer(uuid);
                assert player != null;

                player.sendTitle(Utility.colourise("&cGame Over!"), null, 0, 100, 0);
                player.sendMessage(Utility.colourise("&cThe manhunt is over because the game was stopped!"));

                getRunners().clear();
                getHunters().clear();
                allPlayers.clear();
                ManhuntPlugin.manhuntGame = null;
            }
        });

        deleteWorlds();
    }

    private void deleteWorlds() {
        new BukkitRunnable() {

            @Override
            public void run() {
                ManhuntPlugin.getMultiverseCore().getMVWorldManager().deleteWorld("manhunt_world");
                ManhuntPlugin.getMultiverseCore().getMVWorldManager().deleteWorld("manhunt_world_nether");
                ManhuntPlugin.getMultiverseCore().getMVWorldManager().deleteWorld("manhunt_world_the_end");
            }
        }.runTaskLater(ManhuntPlugin.getInstance(), 100L);
    }


    // -----------------------------------------------------------------------------------------------------------------

    public Set<UUID> getHunters() {
        return hunters;
    }

    public Set<UUID> getRunners() {
        return runners;
    }

    public Set<UUID> getAllPlayers() {
        return allPlayers;
    }

    public void addHunter(UUID uuid) {
        this.allPlayers.add(uuid);
        this.hunters.add(uuid);
    }

    public void addRunner(UUID uuid) {
        this.allPlayers.add(uuid);
        this.runners.add(uuid);
    }

    public GameState getState() {
        return state;
    }
}