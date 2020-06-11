package me.avizoh.custompickaxe.commands;

import me.avizoh.custompickaxe.CustomPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PickaxeCommand implements CommandExecutor {

    ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    List<String> lore;

    public PickaxeCommand() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("suicidepickaxe") && sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Incorrect args. Try &c/suicidepickaxe <player>"));
                return true;
            }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                givePickaxe(target);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have given a suicide pickaxe to [target].").replace("[target]", target.getName()));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have received a suicide pickaxe."));
                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Incorrect args. Try &c/suicidepickaxe <player>"));
            }
        }
        return false;
    }

    public ItemStack createPickaxe() {
        lore = new ArrayList<>();
        this.lore = CustomPickaxe.getInstance().getConfig().getStringList("pickaxe-lore");
        List<String> formatLore = new ArrayList<String>();
        for (String lore : this.lore)
            formatLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        ItemStack pClone = this.pickaxe.clone();
        ItemMeta meta = pClone.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "PICKAXE OF DEATH");
        meta.setLore(formatLore);
        pClone.setItemMeta(meta);
        return pClone;
    }

    public void givePickaxe(Player p) {
        p.getInventory().addItem(createPickaxe());
    }
}
