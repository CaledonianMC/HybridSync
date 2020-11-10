package me.caledonian.hybridsync.discord;

import me.caledonian.hybridsync.HybridSync;
import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Utils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class Sync {
    private JavaPlugin plugin;
    JDA bot;
    public Sync(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    public static void sync(Player p, JDA bot, String id){
        UUID uuid = p.getUniqueId();

        if(Data.get().getBoolean(uuid+".linked") == true){
            for(String string : Files.sync.getConfigurationSection("ranks").getKeys(false)) {
                // Primary Group
                if (HybridSync.getPermissions().getPrimaryGroup(p).equals(Files.sync.getString("ranks." + string + ".server-name"))) {
                    if(Files.config.getBoolean("discord-sync.debug") == true){
                        Bukkit.getConsoleSender().sendMessage(Utils.chat("&c(Debug) &fSync runner for &c"+p.getName()+")&f received. &c(mc="+HybridSync.getPermissions().getPrimaryGroup(p)+") (dc="+Files.sync.getString("ranks."+string+".discord-rank")+")"));
                    }
                    Role role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(Files.sync.getString("ranks." + string + ".discord-rank"), true).get(0);
                    bot.getGuildById(Files.sync.getString("guild.guild-id")).addRoleToMember(id, role).queue();
                }
                // Secondary Groups
                List<String> ranks = Files.sync.getStringList("ranks."+string+".secondary-ranks");
                for (String x : ranks) {
                    // Debug
                    if (HybridSync.getPermissions().getPrimaryGroup(p).equals(Files.sync.getString("ranks." + string + ".server-name"))) {
                        if(Files.config.getBoolean("discord-sync.debug") == true){
                            Bukkit.getConsoleSender().sendMessage(Utils.chat("&c(Debug) &fSync runner for &c"+p.getName()+")&f received [Secondary]. &c(mc="+HybridSync.getPermissions().getPrimaryGroup(p)+") (dc="+Files.sync.getString("ranks."+string+".discord-rank")+")"));
                        }
                        Role role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(x, true).get(0);
                        bot.getGuildById(Files.sync.getString("guild.guild-id")).addRoleToMember(id, role).queue();
                    }
                }

                // Group Removal
                if(!HybridSync.getPermissions().getPrimaryGroup(p).equals(Files.sync.getString("ranks." + string + ".server-name"))){
                    Role role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(Files.sync.getString("ranks."+string+".discord-rank"), true).get(0);
                    bot.getGuildById(Files.sync.getString("guild.guild-id")).removeRoleFromMember(id, role).queue();
                }

                for (String x : ranks) {
                    // Debug
                    if (!HybridSync.getPermissions().getPrimaryGroup(p).equals(Files.sync.getString("ranks." + string + ".server-name"))) {
                        Role role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(x, true).get(0);
                        bot.getGuildById(Files.sync.getString("guild.guild-id")).removeRoleFromMember(id, role).queue();
                    }
                }
            }
        }else{return;}
    }
    public static void unsync(JDA bot, String id){
        for(String string : Files.sync.getConfigurationSection("ranks").getKeys(false)) {
            List<String> ranks = Files.sync.getStringList("ranks."+string+".secondary-ranks");

            Role role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(Files.sync.getString("ranks."+string+".discord-rank"), true).get(0);
            bot.getGuildById(Files.sync.getString("guild.guild-id")).removeRoleFromMember(id, role).queue();

            for (String x : ranks) {
                // Debug
                Role rank = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(x, true).get(0);
                bot.getGuildById(Files.sync.getString("guild.guild-id")).removeRoleFromMember(id, rank).queue();
            }
        }
    }
}
