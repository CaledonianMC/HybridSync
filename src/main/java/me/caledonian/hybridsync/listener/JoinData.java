package me.caledonian.hybridsync.listener;

import me.caledonian.hybridsync.discord.Sync;
import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class JoinData implements Listener {
    private JavaPlugin plugin;
    JDA bot;
    public JoinData(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }
    Integer stop = 0;
    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Data.get().set(uuid+".name", p.getName());
        if(!Data.get().contains(uuid+".linked")){Data.get().set(uuid+".linked", false);}
        if(!Data.get().contains(uuid+".discord")){Data.get().set(uuid+".discord", "0");}
        Data.save();
    }
    @EventHandler
    public void sync(PlayerJoinEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->{
            stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    if(e.getPlayer().isOnline() && Bukkit.getPluginManager().getPlugin("Vault").isEnabled()){
                        Player p = e.getPlayer();
                        UUID uuid = p.getUniqueId();
                        if(Data.get().getBoolean(uuid+".linked") == true){
                            Sync.sync(p, bot, Data.get().getString(uuid+".discord"));
                        }else {Bukkit.getScheduler().cancelTask(stop);}
                    }else{Bukkit.getScheduler().cancelTask(stop);}
                }
            }, 0L, 300L);
        });
    }
}
