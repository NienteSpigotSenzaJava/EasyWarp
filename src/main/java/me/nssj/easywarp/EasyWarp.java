package me.nssj.easywarp;

import me.nssj.easywarp.commands.*;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class EasyWarp extends JavaPlugin {

    private ConsoleCommandSender console;
    private static FileConfiguration warpsConfig;
    private static File warpsConfigFile;

    @Override
    public void onEnable() {

        console = getServer().getConsoleSender();

        console.sendMessage("§6╔═══════════════════════════════════════════════════╗");
        console.sendMessage("§6║                  §aEasyWarp enabled                 §6║");
        console.sendMessage("§6║ §bhttps://github.com/NienteSpigotSenzaJava/EasyWarp §6║");
        console.sendMessage("§6╚═══════════════════════════════════════════════════╝");

        getServer().getPluginManager().registerEvents(new Warp(this), this);

        getCommand("warp").setExecutor(new Warp(this));
        getCommand("setwarp").setExecutor(new SetWarp(this));
        getCommand("delwarp").setExecutor(new DelWarp(this));
        getCommand("warps").setExecutor(new Warps(this));

        createWarpsConfig();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {

        console.sendMessage("§6╔═══════════════════════════════════════════════════╗");
        console.sendMessage("§6║                 §cEasyWarp disabled                 §6║");
        console.sendMessage("§6║ §bhttps://github.com/NienteSpigotSenzaJava/EasyWarp §6║");
        console.sendMessage("§6╚═══════════════════════════════════════════════════╝");

    }

    public static FileConfiguration getWarpsConfig() {

        return warpsConfig;

    }

    private void createWarpsConfig() {

        warpsConfigFile = new File(getDataFolder(), "warps.yml");

        if (!warpsConfigFile.exists()) {

            warpsConfigFile.getParentFile().mkdirs();
            saveResource("warps.yml", false);

        }

        warpsConfig = YamlConfiguration.loadConfiguration(warpsConfigFile);

    }

    public static void saveWarpsConfig() {

        try {

            getWarpsConfig().save(warpsConfigFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
