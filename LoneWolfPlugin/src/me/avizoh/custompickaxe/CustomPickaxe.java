package me.avizoh.custompickaxe;

import me.avizoh.custompickaxe.commands.PickaxeCommand;
import me.avizoh.custompickaxe.events.ObsidianBreakEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomPickaxe extends JavaPlugin {

    public Economy econ;
    public FileConfiguration config;
    public File configFile;
    public static CustomPickaxe instance;

    PluginManager pm = getServer().getPluginManager();

    @Override
    public void onEnable() {
        instance = this;

        getCommand("suicidepickaxe").setExecutor(new PickaxeCommand());

        if (!setupEconomy()) {
            getLogger().info("Disabled due to no Vault dependency found!");
            pm.disablePlugin(this);
            return;
        }

        pm.registerEvents(new ObsidianBreakEvent(), this);

        createConfig();
    }

    @Override
    public void onDisable() { instance = null; saveConfig(); }

    private void createConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (pm.getPlugin("Vault") == null) { return false; }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) { return false; }
        econ = rsp.getProvider();
        return econ != null;
    }
    public Economy getEconomy() { return econ; }
    public static CustomPickaxe getInstance() { return instance; }
}
