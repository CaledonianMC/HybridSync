package me.caledonian.hybridsync.discord;

import me.caledonian.hybridsync.HybridSync;
import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class RankSync implements Listener {
    private JavaPlugin plugin;
    JDA bot;
    Role role = null;
    public RankSync(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID  uuid = p.getUniqueId();
        if(Data.get().getBoolean(uuid+".linked") == true){
            for(String string : Files.config.getConfigurationSection("list").getKeys(false)){
                if(HybridSync.getPermissions().getPrimaryGroup(p).equals(Files.config.getString("ranks." + string + ".server-name"))){
                    role = bot.getGuildById(Files.sync.getString("guild.guild-id")).getRolesByName(Files.config.getString("ranks." + string + ".discord-rank"), true).get(0);
                }
            }
            if(HybridSync.getPermissions().getPrimaryGroup(p).equals("owner")){

            }else{
                Bukkit.getServer().broadcastMessage("Nope!");
                Bukkit.getServer().broadcastMessage("Yep");
                role = bot.getGuildById(730174768610803742L).getRolesByName("Owner", true).get(0);
                bot.getGuildById(730174768610803742L).addRoleToMember(467805961596305410L, role).queue();
            }
        }else{return;}
    }
}
