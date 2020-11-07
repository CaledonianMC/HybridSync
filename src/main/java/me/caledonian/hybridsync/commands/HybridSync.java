package me.caledonian.hybridsync.commands;

import me.caledonian.hybridsync.managers.CommandHandler;
import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Logger;
import me.caledonian.hybridsync.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HybridSync implements CommandHandler {
    private JavaPlugin plugin;
    JDA bot;
    TextChannel textChannel;
    public HybridSync(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
        textChannel = null;
    }

    String prefix = Utils.chat(Files.msgs.getString("core.prefix"));
    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            // Main Command
            if(args.length == 0){
                // Player Mesasge
                if(!p.hasPermission(Files.perms.getString("core.main-cmd-admin"))){
                    List<String> msg1 = Files.msgs.getStringList("core.core-cmd-player");
                    for (String x : msg1) {
                        x = x.replace("%prefix%", prefix);
                        sender.sendMessage(Utils.chat(x));
                    }
                    // Admin Message
                }else if(p.hasPermission(Files.perms.getString("core.main-cmd-admin"))){
                    List<String> msg1 = Files.msgs.getStringList("core.core-cmd-admin");
                    for (String x : msg1) {
                        x = x.replace("%prefix%", prefix).replace("%version%", plugin.getDescription().getVersion());
                        sender.sendMessage(Utils.chat(x));
                    }
                }else{Logger.log(Logger.LogLevel.ERROR, "Could not get access permission.");}
            }else if(args.length >= 1){
                // Help
                if(args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))){
                    List<String> msg1 = Files.msgs.getStringList("help.page1");
                    for (String x : msg1) {
                        x = x.replace("%prefix%", prefix);
                        sender.sendMessage(Utils.chat(x));
                    }
                }else if(args[0].equalsIgnoreCase("bot")) {
                    if (args[1].equalsIgnoreCase("help")) {
                        List<String> msg1 = Files.msgs.getStringList("help.bot");
                        for (String x : msg1) {
                            x = x.replace("%prefix%", prefix);
                            sender.sendMessage(Utils.chat(x));
                        }
                    } else if (args[1].equalsIgnoreCase("send")) {
                        if (p.hasPermission(Files.perms.getString("discord.bot-send"))) {
                            textChannel = bot.getTextChannelById(766421963627298876L);
                            textChannel.sendMessage("Test");
                            p.sendMessage(Utils.chat("&aAttempting to send"));
                        } else {
                            p.sendMessage(Utils.chat(Files.msgs.getString("core.no-permission").replace("%prefix%", prefix)));
                        }
                    }
                }
            }else{p.sendMessage(Utils.chat(Files.msgs.getString("core.404")));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error")));}
    }
}
