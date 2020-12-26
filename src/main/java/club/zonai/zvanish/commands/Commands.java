package club.zonai.zvanish.commands;

import club.zonai.zvanish.zVanish;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {

    zVanish plugin;

    public Commands(zVanish plugin) {
        this.plugin = plugin;
    }

    Plugin config = zVanish.getPlugin(zVanish.class);

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String s, String[] args) {
        if (!(Sender instanceof Player)) {
            return true;
        }
        Player player = (Player) Sender;

        if (cmd.getName().equalsIgnoreCase("vanishlist")) {
            if (player.hasPermission("zVanish.VanishList")) {
                player.sendMessage(config.getConfig().getString("vanish_list.breaker").replace("&", "§"));
                player.sendMessage(config.getConfig().getString("vanish_list.title").replace("&", "§"));
                player.sendMessage("");
                for (int i = 0; i < plugin.vanish_list.size(); i++) {
                    player.sendMessage(config.getConfig().getString("vanish_list.vanished_name").replace("&", "§").replace("<player>", plugin.vanish_list.get(i).getDisplayName()));
                }
                if (plugin.vanish_list.isEmpty()) {
                    player.sendMessage(config.getConfig().getString("vanish_list.no_vanished_players").replace("&", "§"));
                }
                player.sendMessage(config.getConfig().getString("vanish_list.breaker").replace("&", "§"));
            } else {
                player.sendMessage(config.getConfig().getString("no_permission").replace("&", "§"));
            }
        }

        if (cmd.getName().equalsIgnoreCase("fakeleave")) {
            if (args.length == 1) {
                if (player.hasPermission("zVanish.FakeLeave")) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.sendMessage(config.getConfig().getString("leave_msg.message").replace("<player>", args[0]).replace("&", "§"));
                    }
                } else {
                    player.sendMessage(config.getConfig().getString("no_permission").replace("&", "§"));
                }
            }
            else {
                player.sendMessage("§cUsage: /fakeleave <name>");
            }

        }

        if (cmd.getName().equalsIgnoreCase("fakejoin")) {
            if (args.length == 1) {
                if (player.hasPermission("zVanish.FakeJoin")) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.sendMessage(config.getConfig().getString("join_msg.message").replace("<player>", args[0]).replace("&", "§"));
                    }
                } else {
                    player.sendMessage(config.getConfig().getString("no_permission").replace("&", "§"));
                }
            }
            else {
                player.sendMessage("§cUsage: /fakejoin <name>");
            }

        }

        return true;
    }
}
