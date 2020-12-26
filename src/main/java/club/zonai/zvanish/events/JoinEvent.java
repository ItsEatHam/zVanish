package club.zonai.zvanish.events;

import club.zonai.zvanish.zVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinEvent implements Listener {

    zVanish plugin;

    public JoinEvent(zVanish plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("break_blocks.on_by_default")) {
            if (!plugin.blockbreak_list.contains(player)) {
                plugin.blockbreak_list.add(player);
            }
        }
        if (plugin.getConfig().getBoolean("item_pickups.on_by_default")) {
            if (!plugin.itempickup_list.contains(player)) {
                plugin.itempickup_list.add(player);
            }
        }
        for (int i = 0; i < plugin.vanish_list.size(); i++) {
            player.hidePlayer(plugin.vanish_list.get(i));
        }
        if (player.hasPermission("zVanish.VanishOnJoin")) {
            if (player.hasPermission("zVanish.Vanish")) {
                if (plugin.getConfig().getBoolean("vanish_on_join.enabled")) {
                    plugin.vanish_list.add(player);
                    for (Player others : Bukkit.getOnlinePlayers()) {
                        others.hidePlayer(player);
                        player.sendMessage(plugin.getConfig().getString("vanish_on_join.message").replace("&", "§"));
                        event.setJoinMessage(null);
                    }
                }
            }
        }
    }
}
