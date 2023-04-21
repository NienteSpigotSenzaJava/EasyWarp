package me.nssj.easywarp.commands;

import me.nssj.easywarp.EasyWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetWarp implements CommandExecutor {

    private final Plugin plugin;

    public SetWarp(Plugin plugin) {

        this.plugin = plugin;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            FileConfiguration config = plugin.getConfig();
            Player player = (Player) sender;

            if (args.length != 1) {

                player.sendMessage(config.getString("messages.setWarpWrongSyntax"));
                return true;

            }

            FileConfiguration warpsConfig = EasyWarp.getWarpsConfig();

            if (warpsConfig.getString("warps." + args[0]) != null) {

                player.sendMessage(config.getString("messages.warpAlreadyExists").replace("%warp%", args[0]));
                return true;

            }

            warpsConfig.set("warps." + args[0], player.getLocation());
            EasyWarp.saveWarpsConfig();

            player.sendMessage(config.getString("messages.warpCreated").replace("%warp%", args[0]));

        }

        return true;

    }

}
