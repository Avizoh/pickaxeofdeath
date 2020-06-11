package me.avizoh.custompickaxe.util;

import me.avizoh.custompickaxe.CustomPickaxe;
import org.bukkit.ChatColor;

import java.util.List;

public class ChatUtil {

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', CustomPickaxe.getInstance().getConfig().getString(path));
    }
}
