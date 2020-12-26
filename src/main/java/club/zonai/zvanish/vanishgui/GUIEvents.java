package club.zonai.zvanish.vanishgui;

import club.zonai.zvanish.zVanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;


public class GUIEvents implements Listener {

    zVanish plugin;

    public GUIEvents(zVanish plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("vanishgui_main.gui_title").replace("&", "§"))) {
            if (event.getCurrentItem().getType() == Material.valueOf(plugin.getConfig().getString("vanishgui_main.vanish_settings.material"))) {
                plugin.OpenGUI1(player);
            }
            if (event.getCurrentItem().getType() == Material.valueOf(plugin.getConfig().getString("vanishgui_main.vanish_list.material"))) {
                plugin.OpenGUI(player);
            }
            event.setCancelled(true);
        }


        if (event.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("vanishgui_list.gui_title").replace("&", "§"))) {
            if (event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                event.setCancelled(true);
                String name = (event.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&"));
                if (!name.replace(plugin.getConfig().getString("vanishgui_list.vanished_player_info.namecolor"), "").equalsIgnoreCase(player.getDisplayName())) {
                    player.sendMessage("§8[§cSTAFF§8] §7Teleported to §c" + name.replace(plugin.getConfig().getString("vanishgui_list.vanished_player_info.namecolor"), "") + "§7!");
                    player.teleport(Bukkit.getPlayer(name.replace(plugin.getConfig().getString("vanishgui_list.vanished_player_info.namecolor"), "")));
                } else {
                    player.sendMessage(plugin.getConfig().getString("cant_tp_to_self").replace("&", "§"));
                }
            }
        }

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.gui_title").replace("&", "§"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.toggle_vanish.title").replace("&", "§"))) {
                player.performCommand("vanish");
                String tv = plugin.getConfig().getString("vanishedgui_settings.toggle_vanish.sound");
                player.playSound(player.getLocation(), Sound.valueOf(tv), 1.0f, 1.0f);
                player.closeInventory();
            }
            event.setCancelled(true);
        }


        if (event.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.gui_title").replace("&", "§"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.block_break.title").replace("&", "§"))) {
                if (plugin.blockbreak_list.contains(player)) {
                    plugin.blockbreak_list.remove(player);
                    player.sendMessage(plugin.getConfig().getString("break_blocks.on_msg").replace("&", "§"));
                    ItemStack item = event.getCurrentItem();
                    item.setDurability((short)5);

                    //Meta 1
                    ItemMeta BBMeta = item.getItemMeta();
                    BBMeta.setDisplayName(plugin.getConfig().getString("vanishedgui_settings.block_break.title").replace("&", "§"));
                    ArrayList<String> lore4 = new ArrayList<>();
                    for (int i = 0; i < plugin.getConfig().getList("vanishedgui_settings.block_break.enabled_lore").size(); i++) {
                        lore4.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishedgui_settings.block_break.enabled_lore").get(i).toString()));
                    }
                    BBMeta.setLore(lore4);
                    item.setItemMeta(BBMeta);
                    String enable = plugin.getConfig().getString("vanishedgui_settings.block_break.enabled_sound");
                    player.playSound(player.getLocation(), Sound.valueOf(enable), 1.0f, 1.0f);
                } else if(!plugin.blockbreak_list.contains(player)) {
                    plugin.blockbreak_list.add(player);
                    player.sendMessage(plugin.getConfig().getString("break_blocks.off_msg").replace("&", "§"));
                    ItemStack item = event.getCurrentItem();
                    item.setDurability((short)14);

                    //Meta 2
                    ItemMeta BBMeta1 = item.getItemMeta();
                    BBMeta1.setDisplayName(plugin.getConfig().getString("vanishedgui_settings.block_break.title").replace("&", "§"));
                    ArrayList<String> lore44 = new ArrayList<>();
                    for (int i = 0; i < plugin.getConfig().getList("vanishedgui_settings.block_break.disabled_lore").size(); i++) {
                        lore44.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishedgui_settings.block_break.disabled_lore").get(i).toString()));
                    }
                    BBMeta1.setLore(lore44);
                    item.setItemMeta(BBMeta1);
                    String disable = plugin.getConfig().getString("vanishedgui_settings.block_break.disabled_sound");
                    player.playSound(player.getLocation(), Sound.valueOf(disable), 1.0f, 1.0f);
                }
            }
            event.setCancelled(true);
        }

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.gui_title").replace("&", "§"))) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("vanishedgui_settings.item_pickup.title").replace("&", "§"))) {
                if (plugin.itempickup_list.contains(player)) {
                    plugin.itempickup_list.remove(player);
                    player.sendMessage(plugin.getConfig().getString("item_pickups.on_msg").replace("&", "§"));
                    ItemStack item = event.getCurrentItem();
                    item.setDurability((short)5);

                    //Meta 1
                    ItemMeta IPMeta = item.getItemMeta();
                    IPMeta.setDisplayName(plugin.getConfig().getString("vanishedgui_settings.item_pickup.title").replace("&", "§"));
                    ArrayList<String> lore1 = new ArrayList<>();
                    for (int i = 0; i < plugin.getConfig().getList("vanishedgui_settings.item_pickup.enabled_lore").size(); i++) {
                        lore1.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishedgui_settings.item_pickup.enabled_lore").get(i).toString()));
                    }
                    IPMeta.setLore(lore1);
                    item.setItemMeta(IPMeta);
                    String disable = plugin.getConfig().getString("vanishedgui_settings.item_pickup.enabled_sound");
                    player.playSound(player.getLocation(), Sound.valueOf(disable), 1.0f, 1.0f);
                } else if(!plugin.itempickup_list.contains(player)) {
                    player.sendMessage(plugin.getConfig().getString("item_pickups.off_msg").replace("&", "§"));
                    plugin.itempickup_list.add(player);
                    ItemStack item = event.getCurrentItem();
                    item.setDurability((short)14);

                    //Meta 2
                    ItemMeta IPMeta2 = item.getItemMeta();
                    IPMeta2.setDisplayName(plugin.getConfig().getString("vanishedgui_settings.item_pickup.title").replace("&", "§"));
                    ArrayList<String> lore11 = new ArrayList<>();
                    for (int i = 0; i < plugin.getConfig().getList("vanishedgui_settings.item_pickup.disabled_lore").size(); i++) {
                        lore11.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishedgui_settings.item_pickup.disabled_lore").get(i).toString()));
                    }
                    IPMeta2.setLore(lore11);
                    item.setItemMeta(IPMeta2);
                    String disable = plugin.getConfig().getString("vanishedgui_settings.item_pickup.disabled_sound");
                    player.playSound(player.getLocation(), Sound.valueOf(disable), 1.0f, 1.0f);
                }
            }
            event.setCancelled(true);
        }
    }


    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            if (plugin.vanish_list.contains(((Player) event.getEntity()).getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.blockbreak_list.contains(event.getPlayer())) {
            if (plugin.vanish_list.contains(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (plugin.itempickup_list.contains(event.getPlayer())) {
            if (plugin.vanish_list.contains(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

}


