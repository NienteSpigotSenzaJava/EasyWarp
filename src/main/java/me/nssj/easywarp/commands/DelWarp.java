package me.nssj.easywarp.commands;

import me.nssj.easywarp.EasyWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DelWarp implements CommandExecutor, TabCompleter {

    private final Plugin plugin;

    public DelWarp(Plugin plugin) {

        this.plugin = plugin;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            FileConfiguration config = plugin.getConfig();
            Player player = (Player) sender;

            if (args.length != 1) {

                player.sendMessage(config.getString("messages.delWarpWrongSyntax"));
                return true;

            }

            FileConfiguration warpsConfig = EasyWarp.getWarpsConfig();

            if (warpsConfig.getString("warps." + args[0]) == null) {

                player.sendMessage(config.getString("messages.warpDoNotExist").replace("%warp%", args[0]));
                return true;

            }

            warpsConfig.set("warps." + args[0], null);
            EasyWarp.saveWarpsConfig();

            player.sendMessage(config.getString("messages.warpDeleted").replace("%warp%", args[0]));

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {

            ConfigurationSection warps = EasyWarp.getWarpsConfig().getConfigurationSection("warps");

            if (warps.getKeys(false).size() != 0) {

                return new ArrayList<>(warps.getKeys(false));

            }

        }

        return null;

    }

}
