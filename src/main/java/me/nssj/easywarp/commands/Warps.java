package me.nssj.easywarp.commands;

import me.nssj.easywarp.EasyWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Warps implements CommandExecutor {

    private final Plugin plugin;

    public Warps(Plugin plugin) {

        this.plugin = plugin;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            FileConfiguration config = plugin.getConfig();
            Player player = (Player) sender;

            if (args.length != 0) {

                player.sendMessage(config.getString("messages.listWarpWrongSyntax"));
                return true;

            }

            FileConfiguration warpsConfig = EasyWarp.getWarpsConfig();
            ConfigurationSection warps = warpsConfig.getConfigurationSection("warps");

            if (warps.getKeys(false).size() == 0) {

                player.sendMessage(config.getString("messages.noWarps"));
                return true;

            }

            StringBuilder list = new StringBuilder(config.getString("messages.listWarp"));

            for (String warp : warps.getKeys(false)) {

                list.append("\n" + config.getString("messages.listWarpItem").replace("%warp%", warp));

            }

            player.sendMessage(list.toString());

        }

        return true;

    }

}
