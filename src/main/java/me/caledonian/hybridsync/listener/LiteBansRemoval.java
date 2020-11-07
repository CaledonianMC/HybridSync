package me.caledonian.hybridsync.listener;

import litebans.api.Entry;
import litebans.api.Events;
import me.caledonian.hybridsync.utils.Files;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public class LiteBansRemoval {
    private JavaPlugin plugin;
    public LiteBansRemoval(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
    }

    public static void register(JDA bot){
        Events.get().register(new Events.Listener(){

            @Override
            public void entryRemoved(Entry entry){
                TextChannel textChannel;
                textChannel = bot.getTextChannelById(Files.discord.getString("channels.litebans"));
                String server = Files.config.getString("core.server-name");
                String player = getPlayerName(Objects.requireNonNull(entry.getUuid()));
                String executor = getPlayerName(Objects.requireNonNull(entry.getExecutorUUID()));
                String type = entry.getType();

                if(entry.getType().equals("ban")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("unban.author.message"), null, Files.discord.getString("unban.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("unban.color.red"), Files.discord.getInt("unban.color.green"), Files.discord.getInt("unban.color.blue")));
                    eb.setDescription(Files.discord.getString("unban.description").replace("%type%", type));
                    eb.addField(Files.discord.getString("unban.server.title"), Files.discord.getString("unban.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("unban.player.title"), Files.discord.getString("unban.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("unban.staff.title"), Files.discord.getString("unban.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("unban.footer.message"), Files.discord.getString("unban.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }else if(entry.getType().equals("mute")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("unmute.author.message"), null, Files.discord.getString("unmute.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("unmute.color.red"), Files.discord.getInt("unmute.color.green"), Files.discord.getInt("unban.color.blue")));
                    eb.setDescription(Files.discord.getString("unmute.description").replace("%type%", type));
                    eb.addField(Files.discord.getString("unmute.server.title"), Files.discord.getString("unmute.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("unmute.player.title"), Files.discord.getString("unmute.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("unmute.staff.title"), Files.discord.getString("unmute.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("unmute.footer.message"), Files.discord.getString("unmute.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }else if(entry.getType().equals("warn")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("unwarn.author.message"), null, Files.discord.getString("unwarn.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("unwarn.color.red"), Files.discord.getInt("unmute.color.green"), Files.discord.getInt("unban.color.blue")));
                    eb.setDescription(Files.discord.getString("unwarn.description").replace("%type%", type));
                    eb.addField(Files.discord.getString("unwarn.server.title"), Files.discord.getString("unwarn.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("unwarn.player.title"), Files.discord.getString("unwarn.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("unwarn.staff.title"), Files.discord.getString("unwarn.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("unwarn.footer.message"), Files.discord.getString("unwarn.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }
            }
        });
    }
    public static String getPlayerName(String uuid) {
        if(uuid.equalsIgnoreCase("console")){
            return "console";
        }
        return Bukkit.getPlayer(UUID.fromString(uuid)) != null ?
                Bukkit.getPlayer(UUID.fromString(uuid)).getName()
                : Bukkit.getOfflinePlayer((UUID.fromString(uuid))).getName();
    }
    public static String getBadge(String uuid){
        String player = getPlayerName(Objects.requireNonNull(uuid));
        Player pl = Bukkit.getPlayer(uuid);
        if(player.equalsIgnoreCase("Caledonian_EH") || (player.equalsIgnoreCase("Caledonian_LH"))){
            player = "<:botdeveloper:766491695965536266> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(player.equalsIgnoreCase("Troggs")){
            player = "<:supporter:66493233173364786> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.owner"))){
            player = "<a:owner:766492501036892211> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.manager"))) {
            player = "<:manager:766492986472923146> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.admin"))) {
            player = "<:admin:766492574499471390> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.mod"))) {
            player = "<:moderator:766492673112014888> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.helper"))) {
            player = "<:helper:766492759917068308> " + getPlayerName(Objects.requireNonNull(uuid));
        }else if(pl.hasPermission(Files.config.getString("discord.badges.builder"))) {
            player = "<:builder:766492832130924544> " + getPlayerName(Objects.requireNonNull(uuid));
        }
        return player;
    }
}
