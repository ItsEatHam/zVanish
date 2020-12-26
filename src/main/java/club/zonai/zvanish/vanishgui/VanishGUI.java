package club.zonai.zvanish.vanishgui;

import club.zonai.zvanish.zVanish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VanishGUI implements CommandExecutor {


    zVanish plugin;

    public VanishGUI(zVanish plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String s, String[] args) {
        if (!(Sender instanceof Player)) { return true; }
        Player player = (Player) Sender;


        if (cmd.getName().equalsIgnoreCase("VanishGUI")) {
            Inventory MainVanish = Bukkit.createInventory(player, 27, plugin.getConfig().getString("vanishgui_main.gui_title").replace("&", "§"));

            ItemStack VSettings = new ItemStack(Material.valueOf(plugin.getConfig().getString("vanishgui_main.vanish_settings.material")), 1);
            ItemMeta VMeta = VSettings.getItemMeta();
            VMeta.setDisplayName(plugin.getConfig().getString("vanishgui_main.vanish_settings.title").replace("&", "§"));
            ArrayList<String> lore = new ArrayList<>();
            for (int i = 0; i < plugin.getConfig().getList("vanishgui_main.vanish_settings.lore").size(); i++) {
                lore.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishgui_main.vanish_settings.lore").get(i).toString()));
            }
            VMeta.setLore(lore);
            VSettings.setItemMeta(VMeta);

            MainVanish.setItem(plugin.getConfig().getInt("vanishgui_main.vanish_settings.slot"), VSettings);

            ItemStack VPlayers = new ItemStack(Material.valueOf(plugin.getConfig().getString("vanishgui_main.vanish_list.material")), 1);
            ItemMeta VPMeta = VPlayers.getItemMeta();
            VPMeta.setDisplayName(plugin.getConfig().getString("vanishgui_main.vanish_list.title").replace("&", "§"));
            ArrayList<String> lore1 = new ArrayList<>();
            for (int i = 0; i < plugin.getConfig().getList("vanishgui_main.vanish_list.lore").size(); i++) {
                lore1.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("vanishgui_main.vanish_list.lore").get(i).toString()));
            }
            VPMeta.setLore(lore1);
            VPlayers.setItemMeta(VPMeta);

            MainVanish.setItem(plugin.getConfig().getInt("vanishgui_main.vanish_list.slot"), VPlayers);

            player.openInventory(MainVanish);

        }


        return true;
    }
}

