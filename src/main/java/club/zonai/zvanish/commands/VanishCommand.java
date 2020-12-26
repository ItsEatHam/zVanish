package club.zonai.zvanish.commands;

import club.zonai.zvanish.zVanish;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class VanishCommand implements CommandExecutor {


    zVanish plugin;

    public VanishCommand(zVanish plugin) {
        this.plugin = plugin;
    }

    Plugin config = zVanish.getPlugin(zVanish.class);



    public boolean onCommand(CommandSender Sender, Command cmd, String s, String[] args) {
        Player player = (Player) Sender;
        if (!(Sender instanceof Player)) { return true; }

        if (args.length == 0) {
            if (player.hasPermission("zVanish.Vanish")) {
                if (plugin.vanish_list.contains(player)) {
                    for (Player others : Bukkit.getOnlinePlayers()) {
                        others.showPlayer(player);
                        if (config.getConfig().getBoolean("join_msg.automatic")) {
                            others.sendMessage(config.getConfig().getString("join_msg.message").replace("&", "§").replace("<player>", player.getDisplayName()));
                        }
                    }
                    plugin.vanish_list.remove(player);
                    player.sendMessage(config.getConfig().getString("self_unvanish_msg").replace("&", "§"));
                } else if(!plugin.vanish_list.contains(player)) {
                    for (Player others : Bukkit.getOnlinePlayers()) {
                        others.hidePlayer(player);
                        if (config.getConfig().getBoolean("leave_msg.automatic")) {
                            others.sendMessage(config.getConfig().getString("leave_msg.message").replace("&", "§").replace("<player>", player.getDisplayName()));
                        }
                    }
                    plugin.vanish_list.add(player);
                    player.sendMessage(config.getConfig().getString("self_vanish_msg").replace("&", "§"));
                }

            } else {
                player.sendMessage(config.getConfig().getString("no_permission"));
            }
        }

        if (args.length == 1) {
            if (player.hasPermission("zVanish.VanishOther")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(config.getConfig().getString("vanish_other.offline_msg"));
                } else {
                    if (plugin.vanish_list.contains(target)) {
                        for (Player others : Bukkit.getOnlinePlayers()) {
                            others.showPlayer(target);
                            if (config.getConfig().getBoolean("join_msg.automatic")) {
                                others.sendMessage(config.getConfig().getString("join_msg.message").replace("&", "§").replace("<player>", target.getDisplayName()));
                            }
                        }
                        plugin.vanish_list.remove(target);
                        player.sendMessage(config.getConfig().getString("vanish_other.un-vanished_other").replace("&", "§").replace("<target>", target.getDisplayName()));
                        target.sendMessage(config.getConfig().getString("vanish_other.been_un-vanished").replace("&", "§").replace("<sender>", player.getDisplayName()));
                    } else if (!plugin.vanish_list.contains(target)) {
                        for (Player others : Bukkit.getOnlinePlayers()) {
                            others.hidePlayer(target);
                            if (config.getConfig().getBoolean("leave_msg.automatic")) {
                                others.sendMessage(config.getConfig().getString("leave_msg.message").replace("&", "§").replace("<player>", target.getDisplayName()));
                            }
                        }
                        plugin.vanish_list.add(target);
                        player.sendMessage(config.getConfig().getString("vanish_other.vanished_other").replace("&", "§").replace("<target>", target.getDisplayName()));
                        target.sendMessage(config.getConfig().getString("vanish_other.been_vanished").replace("&", "§").replace("<sender>", player.getDisplayName()));
                    }
                }
            } else {
                player.sendMessage(config.getConfig().getString("no_permission"));
            }
        }
        return true;
    }
}
