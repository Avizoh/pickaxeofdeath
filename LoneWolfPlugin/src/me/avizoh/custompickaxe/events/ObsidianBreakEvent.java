package me.avizoh.custompickaxe.events;

import me.avizoh.custompickaxe.CustomPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ObsidianBreakEvent implements Listener {

    private CustomPickaxe pl;

    public ObsidianBreakEvent(CustomPickaxe plugin) {
        plugin = pl;
    }


    @EventHandler
    public void obsidianBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String message = CustomPickaxe.getInstance().getConfig().getString("broadcast-message");

        if (event.getBlock().getType() != Material.COBBLESTONE) return;
        if (event.getBlock().getType() == Material.COBBLESTONE) {
            if (player.getItemInHand().getType() == Material.DIAMOND_PICKAXE && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', CustomPickaxe.getInstance().getConfig().getString("pickaxe-name"))) && player.getItemInHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', CustomPickaxe.getInstance().getConfig().getString("pickaxe-lore")))) {
                player.setHealth(0.0);
                player.spigot().respawn();
                message = message.replace("%player%", player.getName());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
                double bal = CustomPickaxe.getInstance().econ.getBalance(player.getName());
                CustomPickaxe.getInstance().econ.withdrawPlayer(player.getName(), bal);
                player.sendMessage(ChatColor.GOLD + "Your balance was reset to " + ChatColor.GREEN + "$0");
            }
        }
    }
}
