package me.avizoh.custompickaxe.events;

import me.avizoh.custompickaxe.CustomPickaxe;
import me.avizoh.custompickaxe.commands.PickaxeCommand;
import me.avizoh.custompickaxe.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class ObsidianBreakEvent extends PickaxeCommand implements Listener {

    @EventHandler
    public void obsidianBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String message = getMessage("messages.broadcast");

        if (event.getBlock().getType() != Material.COBBLESTONE) return;
        if (player.getItemInHand().getType() == Material.DIAMOND_PICKAXE && player.getItemInHand().getItemMeta().getDisplayName().equals(getMessage("pickaxe-name")) && isLore()) {
            player.setHealth(0.0);
            player.spigot().respawn();
            message = message.replace("%player%", player.getName());
            Bukkit.broadcastMessage(message);
            double bal = CustomPickaxe.getInstance().econ.getBalance(player.getName());
            CustomPickaxe.getInstance().econ.withdrawPlayer(player.getName(), bal);
            player.sendMessage(getMessage("messages.balance-reset"));
        }
    }
}
