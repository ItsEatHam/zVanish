package club.zonai.zvanish;

import club.zonai.zvanish.commands.Commands;
import club.zonai.zvanish.vanishgui.GUIEvents;
import club.zonai.zvanish.commands.VanishCommand;
import club.zonai.zvanish.events.JoinEvent;
import club.zonai.zvanish.vanishgui.VanishGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public final class zVanish extends JavaPlugin {

    //ArrayLists:
    public ArrayList<Player> vanish_list = new ArrayList<>();
    public ArrayList<Player> blockbreak_list = new ArrayList<>();
    public ArrayList<Player> itempickup_list = new ArrayList<>();
    public ArrayList<Player> keepvanish = new ArrayList<>();



    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("vanishlist").setExecutor(new Commands(this));
        getCommand("VanishGUI").setExecutor(new VanishGUI(this));
        getCommand("fakeleave").setExecutor(new Commands(this));
        getCommand("fakejoin").setExecutor(new Commands(this));
        getCommand("zvanish").setExecutor(this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new GUIEvents(this), this);
    }

    public void OpenGUI(Player player) {
        Inventory vanishgui = Bukkit.createInventory(player, 27, getConfig().getString("vanishgui_list.gui_title").replace("&", "§"));

        for (int i = 0; i < vanish_list.size(); i++) {
            ItemStack VanishedHead = new ItemStack(Material.SKULL_ITEM, 1);
            ItemMeta meta = VanishedHead.getItemMeta();
            meta.setDisplayName(getConfig().getString("vanishgui_list.vanished_player_info.namecolor").replace("&", "§") + vanish_list.get(i).getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.breaker").replace("&", "§"));
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.description").replace("&", "§"));
            lore.add("");
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.players_settings_title").replace("&", "§"));
            if (blockbreak_list.contains(vanish_list.get(i).getPlayer())) {
                lore.add(getConfig().getString("vanishgui_list.vanished_player_info.break_block_false").replace("&", "§"));
            } else {
                lore.add(getConfig().getString("vanishgui_list.vanished_player_info.break_block_true").replace("&", "§"));
            }
            if (itempickup_list.contains(vanish_list.get(i).getPlayer())) {
                lore.add(getConfig().getString("vanishgui_list.vanished_player_info.item_pickup_false").replace("&", "§"));
            } else {
                lore.add(getConfig().getString("vanishgui_list.vanished_player_info.item_pickup_true").replace("&", "§"));
            }
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.breaker").replace("&", "§"));
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.tp_to_player").replace("&", "§"));
            lore.add(getConfig().getString("vanishgui_list.vanished_player_info.breaker").replace("&", "§"));
            meta.setLore(lore);
            VanishedHead.setItemMeta(meta);

            vanishgui.addItem(VanishedHead);
        }

        if (vanish_list.isEmpty()) {
            ItemStack EmptyHead = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta EmptyMeta = EmptyHead.getItemMeta();
            EmptyMeta.setDisplayName(getConfig().getString("vanishgui_list.no_vanished_players_title").replace("&", "§"));
            ArrayList<String> emptylore = new ArrayList<>();
            for (int i = 0; i < getConfig().getList("vanishgui_list.no_vanished_players_lore").size(); i++) {
                emptylore.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishgui_list.no_vanished_players_lore").get(i).toString()));
            }
            EmptyMeta.setLore(emptylore);
            EmptyHead.setItemMeta(EmptyMeta);

            vanishgui.setItem(13, EmptyHead);
        }
        player.openInventory(vanishgui);
    }

    public void OpenGUI1(Player player) {
        Inventory vanishsettings = Bukkit.createInventory(player, 27, getConfig().getString("vanishedgui_settings.gui_title").replace("&", "§"));

        ItemStack BreakBlock = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        //Meta 1
        ItemMeta BBMeta = BreakBlock.getItemMeta();
        BBMeta.setDisplayName(getConfig().getString("vanishedgui_settings.block_break.title").replace("&", "§"));
        ArrayList<String> lore4 = new ArrayList<>();
        for (int i = 0; i < getConfig().getList("vanishedgui_settings.block_break.disabled_lore").size(); i++) {
            lore4.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishedgui_settings.block_break.disabled_lore").get(i).toString()));
        }
        BBMeta.setLore(lore4);

        //Meta 2
        ItemMeta BBMeta1 = BreakBlock.getItemMeta();
        BBMeta1.setDisplayName(getConfig().getString("vanishedgui_settings.block_break.title").replace("&", "§"));
        ArrayList<String> lore44 = new ArrayList<>();
        for (int i = 0; i < getConfig().getList("vanishedgui_settings.block_break.enabled_lore").size(); i++) {
            lore44.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishedgui_settings.block_break.enabled_lore").get(i).toString()));
        }
        BBMeta1.setLore(lore44);
        if (blockbreak_list.contains(player)) {
            BreakBlock.setDurability((short) 14);
            BreakBlock.setItemMeta(BBMeta);
        } else if (!blockbreak_list.contains(player)) {
            BreakBlock.setDurability((short) 5);
            BreakBlock.setItemMeta(BBMeta1);
        }
        vanishsettings.setItem(getConfig().getInt("vanishedgui_settings.block_break.slot"), BreakBlock);



        ItemStack ItemPickup = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        //Meta 1
        ItemMeta IPMeta = ItemPickup.getItemMeta();
        IPMeta.setDisplayName(getConfig().getString("vanishedgui_settings.item_pickup.title").replace("&", "§"));
        ArrayList<String> lore1 = new ArrayList<>();
        for (int i = 0; i < getConfig().getList("vanishedgui_settings.item_pickup.disabled_lore").size(); i++) {
            lore1.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishedgui_settings.item_pickup.disabled_lore").get(i).toString()));
        }
        IPMeta.setLore(lore1);
        //Meta 2
        ItemMeta IPMeta2 = ItemPickup.getItemMeta();
        IPMeta2.setDisplayName(getConfig().getString("vanishedgui_settings.item_pickup.title").replace("&", "§"));
        ArrayList<String> lore11 = new ArrayList<>();
        for (int i = 0; i < getConfig().getList("vanishedgui_settings.item_pickup.enabled_lore").size(); i++) {
            lore11.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishedgui_settings.item_pickup.enabled_lore").get(i).toString()));
        }
        IPMeta2.setLore(lore11);

        if (itempickup_list.contains(player)) {
            ItemPickup.setDurability((short) 14);
            ItemPickup.setItemMeta(IPMeta);
        } else if (!itempickup_list.contains(player)) {
            ItemPickup.setDurability((short) 5);
            ItemPickup.setItemMeta(IPMeta2);
        }
        vanishsettings.setItem(15, ItemPickup);


        ItemStack ToggleVanish = new ItemStack(Material.valueOf(getConfig().getString("vanishedgui_settings.toggle_vanish.material")), 1);
        ItemMeta TVMeta = ToggleVanish.getItemMeta();
        TVMeta.setDisplayName(getConfig().getString("vanishedgui_settings.toggle_vanish.title").replace("&", "§"));
        ArrayList<String> lore2 = new ArrayList<>();
        for (int i = 0; i < getConfig().getList("vanishedgui_settings.toggle_vanish.lore").size(); i++) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', getConfig().getList("vanishedgui_settings.toggle_vanish.lore").get(i).toString()));
        }
        TVMeta.setLore(lore2);
        ToggleVanish.setItemMeta(TVMeta);

        vanishsettings.setItem(getConfig().getInt("vanishedgui_settings.toggle_vanish.slot"), ToggleVanish);

        player.openInventory(vanishsettings);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    player.sendMessage(getConfig().getString("config_reload_msg").replace("&", "§"));
                }
                if (args[0].equalsIgnoreCase("help")) {
                    for (int i = 0; i < getConfig().getList("help_message").size(); i++) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getList("help_message").get(i).toString()));
                    }
                }
            }
            if (args.length == 0) {
                player.sendMessage("§8§m--------------------------------");
                player.sendMessage("§c§lzVanish§7 - §c" + "v1.0");
                player.sendMessage("§7Author: §c" + "Ham @ Zonai Dev");
                player.sendMessage("§7Discord: §chttps://discord.gg/WFRvjNq");
                player.sendMessage("§7For Help use §c'/zVanish help'§7.");
                player.sendMessage("§8§m--------------------------------");
            }
            if (args.length > 1) {
                player.sendMessage("§cUsage: /zVanish <help / reload>");
            }

        return true;
    }

}



