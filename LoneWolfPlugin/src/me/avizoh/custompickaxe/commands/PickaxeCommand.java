package me.avizoh.custompickaxe.commands;

import me.avizoh.custompickaxe.CustomPickaxe;
import me.avizoh.custompickaxe.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PickaxeCommand extends ChatUtil implements CommandExecutor {

    ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
    List<String> lore;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("suicidepickaxe")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (!target.isOnline()) {
                        player.sendMessage(getMessage("messages.target-offline").replace("%target%", target.getName()));
                        return true;
                    }
                    if (target.isOnline()) {
                        givePickaxe(target);
                        player.sendMessage(getMessage("messages.pickaxe-given").replace("%target%", target.getName()));
                        target.sendMessage(getMessage("messages.pickaxe-received"));
                        return true;
                    }
                } else {
                    player.sendMessage(getMessage("messages.incorrect-args"));
                    return true;
                }
            } else {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (!target.isOnline()) {
                        Bukkit.getServer().getConsoleSender().sendMessage(Bukkit.getPlayer(args[0]) + " is not online.");
                        return true;
                    }
                    if (target.isOnline()) {
                        givePickaxe(target);
                        Bukkit.getServer().getConsoleSender().sendMessage("You have given a suicide pickaxe to " + Bukkit.getPlayer(args[0]));
                        target.sendMessage(getMessage("messages.pickaxe-received"));
                        return true;
                    }
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage("Incorrect args. Try /suicidepickaxe <player>");
                    return true;
                }
            }
        }
        return false;
    }

    public ItemStack createPickaxe() {
        lore = new ArrayList<>();
        this.lore = CustomPickaxe.getInstance().getConfig().getStringList("pickaxe-lore");
        List<String> formatLore = new ArrayList<String>();
        for (String lore : this.lore)
            formatLore.add(color(lore));
        ItemStack pClone = this.pickaxe.clone();
        ItemMeta meta = pClone.getItemMeta();
        meta.setDisplayName(getMessage("pickaxe-name"));
        meta.setLore(formatLore);
        pClone.setItemMeta(meta);
        return pClone;
    }

    public void givePickaxe(Player p) {
        p.getInventory().addItem(createPickaxe());
    }

    public boolean isLore() {
        lore = new ArrayList<>();
        this.lore = CustomPickaxe.getInstance().getConfig().getStringList("pickaxe-lore");
        List<String> formatLore = new ArrayList<String>();
        for (String lore : this.lore)
            formatLore.add(color(lore));
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setLore(formatLore);
        return true;
    }
}
