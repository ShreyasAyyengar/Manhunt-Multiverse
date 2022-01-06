package me.shreyasayyengar.manhuntmultiverse.commands;

import me.shreyasayyengar.manhuntmultiverse.ManhuntPlugin;
import me.shreyasayyengar.manhuntmultiverse.objects.ManhuntGame;
import me.shreyasayyengar.manhuntmultiverse.utils.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ManhuntCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("create")) {

                    if (Utility.isGameRunning()) {
                        player.sendMessage(Utility.colourise("&cA game is already running!"));
                        return false;
                    }
                    player.sendMessage(Utility.colourise("&b&lCreating worlds. This might take some time; please wait....."));
                    ManhuntPlugin.manhuntGame = new ManhuntGame();
                }

                if (args[0].equalsIgnoreCase("stop")) {
                    if (Utility.isGameRunning()) {
                        ManhuntPlugin.manhuntGame.endGameGenerically();
                    } else player.sendMessage(Utility.colourise("&cThere is no active game running!"));
                }
            }
        }
        return false;
    }
}
