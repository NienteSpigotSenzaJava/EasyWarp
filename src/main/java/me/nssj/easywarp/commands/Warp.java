package me.nssj.easywarp.commands;

import me.nssj.easywarp.EasyWarp;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Warp implements CommandExecutor, TabCompleter, Listener {

    private final Plugin plugin;
    private static final HashMap<String, HashMap<String, Object>> lastUsage = new HashMap<>();

    public Warp(Plugin plugin) {

        this.plugin = plugin;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            FileConfiguration config = plugin.getConfig();
            Player player = (Player) sender;

            if (args.length != 1) {

                player.sendMessage(config.getString("messages.warpWrongSyntax"));
                return true;

            }

            FileConfiguration warpsConfig = EasyWarp.getWarpsConfig();

            if (warpsConfig.getString("warps." + args[0]) == null) {

                player.sendMessage(config.getString("messages.warpDoNotExist").replace("%warp%", args[0]));
                return true;

            }

            if (config.getBoolean("requireWarpPermission") && !player.hasPermission("easywarp.warp." + args[0])) {

                player.sendMessage(config.getString("messages.warpNoPermission").replace("%warp%", args[0]));
                return true;

            }

            int cooldown = config.getInt("cooldown");

            if (lastUsage.containsKey(player.getName()) && System.currentTimeMillis() - (long) lastUsage.get(player.getName()).get("lastUsage") < cooldown * 1000) {

                player.sendMessage(config.getString("messages.teleportCooldown").replace("%warp%", args[0]).replace("%cooldown%", String.valueOf(cooldown)));
                return true;

            }

            player.sendMessage(config.getString("messages.teleportInProgress").replace("%warp%", args[0]).replace("%cooldown%", String.valueOf(cooldown)));

            HashMap<String, Object> data = new HashMap<>();

            data.put("lastUsage", System.currentTimeMillis());
            data.put("warp", args[0]);

            lastUsage.put(player.getName(), data);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                public void run() {

                    if (lastUsage.containsKey(player.getName())) {

                        if (lastUsage.get(player.getName()).get("warp") != args[0]) return;

                    } else {

                        return;

                    }

                    player.teleport((Location) warpsConfig.get("warps." + args[0]));
                    player.sendMessage(config.getString("messages.teleportSuccessfully").replace("%warp%", args[0]));

                }

            }, (long) cooldown * 20);

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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Block from = e.getFrom().getBlock();
        Block to = e.getTo().getBlock();

        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {

            FileConfiguration config = plugin.getConfig();
            Player player = e.getPlayer();

            if (config.getBoolean("cancelTpOnMove") && lastUsage.containsKey(player.getName()) && System.currentTimeMillis() - (long) lastUsage.get(player.getName()).get("lastUsage") <= config.getInt("cooldown") * 1000) {

                player.sendMessage(config.getString("messages.teleportCancelled").replace("%warp%", (String) lastUsage.get(player.getName()).get("warp")));
                lastUsage.remove(player.getName());

            }

        }

    }

}
