package me.caledonian.hybridsync;

import me.caledonian.hybridsync.discord.DiscordLink;
import me.caledonian.hybridsync.discord.RankSync;
import me.caledonian.hybridsync.listener.JoinData;
import me.caledonian.hybridsync.listener.LiteBansAdded;
import me.caledonian.hybridsync.listener.LiteBansRemoval;
import me.caledonian.hybridsync.managers.CommandManager;
import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Logger;
import me.caledonian.hybridsync.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.awt.*;

public final class HybridSync extends JavaPlugin {
    private static HybridSync plugin;
    public static HybridSync instance;
    JDA bot = null;
    TextChannel textChannel;
    private static Permission perms = null;


    @Override
    public void onEnable() {
        Logger.log(Logger.LogLevel.INFO, "Starting plugin HybridSMP v1.0-BETA by Caledonian EH");
        // Important
        Files.base(this);
        Data.setup();







        // Discord
        JDABuilder jdaBuilder = JDABuilder.createDefault(Files.discord.getString("setup.bot-token"));
        try{
            bot = jdaBuilder.build();
            bot.awaitReady();
        }catch (LoginException | InterruptedException e){Logger.log(Logger.LogLevel.ERROR, "Could not load discord bot");e.printStackTrace();}
        // Enable
        String server = Files.config.getString("core.server-name");

        textChannel = bot.getTextChannelById(Files.discord.getString("channels.general"));
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(Files.discord.getString("online.author.message"), null, Files.discord.getString("online.author.image"));
        eb.setColor(new Color(Files.discord.getInt("online.color.red"), Files.discord.getInt("online.color.green"), Files.discord.getInt("online.color.blue")));
        eb.addField(Files.discord.getString("online.server.title"), Files.discord.getString("online.server.info").replace("%server%", server), true);
        eb.addField(Files.discord.getString("online.status.title"), Files.discord.getString("online.status.info"), true);
        eb.setFooter(Files.discord.getString("online.footer.message"), Files.discord.getString("online.footer.image"));
        textChannel.sendMessage(eb.build()).queue();
        // Status
        jdaBuilder.setActivity(Activity.watching(Files.discord.getString("setup.activity")));
        if(Files.discord.getInt("setup.status") == 1){
            jdaBuilder.setStatus(OnlineStatus.ONLINE);
        }else if(Files.discord.getInt("setup.status") == 2){
            jdaBuilder.setStatus(OnlineStatus.IDLE);
        }else if(Files.discord.getInt("setup.status") == 3){
            jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        }else if(Files.discord.getInt("setup.status") == 0){
            jdaBuilder.setStatus(OnlineStatus.OFFLINE);
        }
        bot.addEventListener(new DiscordLink(this));


        if(getServer().getPluginManager().getPlugin("LiteBans") != null){
            LiteBansAdded.register(this.bot);
            LiteBansRemoval.register(this.bot);
        }else{Logger.log(Logger.LogLevel.ERROR, "Could not find LiteBans, any litebans hooks will be disabled.");}
        if(getServer().getPluginManager().getPlugin("LuckPerms") != null){

        }else{Logger.log(Logger.LogLevel.ERROR, "Could not find LuckPerms, any luckperms hooks will be disabled.");}
        new CommandManager(this, bot);
        Logger.log(Logger.LogLevel.SUCCESS, "HybridSync has been fully loaded.");
        if(getServer().getPluginManager().getPlugin("Vault") != null){
            setupPermssions();
            //Bukkit.getServer().getPluginManager().registerEvents(new RankSync(this, bot), this);
        }else{Logger.log(Logger.LogLevel.ERROR, "Could not find Vault, discord rank sync will be disabled.");}
        Bukkit.getServer().getPluginManager().registerEvents(new JoinData(this, bot), this);
    }

    @Override
    public void onDisable() {
        Logger.log(Logger.LogLevel.INFO, "Disabling HybridSync...");
        for (Player p: Bukkit.getServer().getOnlinePlayers()) {
            p.kickPlayer(Utils.chat("\n&c&lHYBRID &f&lSYNC IS BEING RELOADED\n&f \n&7&oYou were kicked because &f&oHybrid Sync &7&ocannot reload with players"));
        }


        String server = Files.config.getString("core.server-name");

        textChannel = bot.getTextChannelById(Files.discord.getString("channels.general"));
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(Files.discord.getString("offline.author.message"), null, Files.discord.getString("offline.author.image"));
        eb.setColor(new Color(Files.discord.getInt("offline.color.red"), Files.discord.getInt("offline.color.green"), Files.discord.getInt("offline.color.blue")));
        eb.addField(Files.discord.getString("offline.server.title"), Files.discord.getString("offline.server.info").replace("%server%", server), true);
        eb.addField(Files.discord.getString("offline.status.title"), Files.discord.getString("offline.status.info"), true);
        eb.setFooter(Files.discord.getString("offline.footer.message"), Files.discord.getString("offline.footer.image"));
        textChannel.sendMessage(eb.build()).queue();
        bot.shutdown();
        Logger.log(Logger.LogLevel.SUCCESS, "HybridSync by Caledonian EH is fully disabled.");
    }

    private boolean setupPermssions(){
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Permission getPermissions(){
        return perms;
    }
}
