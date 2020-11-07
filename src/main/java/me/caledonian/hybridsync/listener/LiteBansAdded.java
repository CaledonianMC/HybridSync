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

public class LiteBansAdded {
    private JavaPlugin plugin;
    public LiteBansAdded(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
    }

    public static void register(JDA bot){
        Events.get().register(new Events.Listener(){

            @Override
            public void entryAdded(Entry entry){
                TextChannel textChannel;
                textChannel = bot.getTextChannelById(Files.discord.getString("channels.litebans"));
                String server = Files.config.getString("core.server-name");
                String player = getPlayerName(Objects.requireNonNull(entry.getUuid()));
                String executor = getPlayerName(Objects.requireNonNull(entry.getExecutorUUID()));
                String type = entry.getType();
                String reason = entry.getReason();
                String duration = entry.getDurationString();
                Player pl = Bukkit.getPlayer(entry.getUuid());
                if(player.equalsIgnoreCase("Caledonian_EH") || (player.equalsIgnoreCase("Caledonian_LH"))){
                    player = "<:developer:766491695965536266>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(player.equalsIgnoreCase("Troggs")){
                    player = "<:supporter:66493233173364786>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.owner"))){
                    player = "<a:owner:766492501036892211>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.manager"))) {
                    player = "<:manager:766492986472923146>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.admin"))) {
                    player = "<:admin:766492574499471390>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.mod"))) {
                    player = "<:moderator:766492673112014888>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.helper"))) {
                    player = "<:helper:766492759917068308>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }else if(pl.hasPermission(Files.config.getString("discord.badges.builder"))) {
                    player = "<:builder:766492832130924544>" + getPlayerName(Objects.requireNonNull(entry.getUuid()));
                }

                if(entry.getType().equals("ban")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("ban.author.message"), null, Files.discord.getString("ban.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("ban.color.red"), Files.discord.getInt("ban.color.green"), Files.discord.getInt("ban.color.blue")));
                    eb.setDescription(Files.discord.getString("ban.description").replace("%reason%", reason).replace("%duration%", duration).replace("%type%", type));
                    eb.addField(Files.discord.getString("ban.server.title"), Files.discord.getString("ban.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("ban.player.title"), Files.discord.getString("ban.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("ban.staff.title"), Files.discord.getString("ban.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("ban.footer.message"), Files.discord.getString("ban.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }else if(entry.getType().equals("mute")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("mute.author.message"), null, Files.discord.getString("mute.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("mute.color.red"), Files.discord.getInt("mute.color.green"), Files.discord.getInt("mute.color.blue")));
                    eb.setDescription(Files.discord.getString("mute.description").replace("%reason%", reason).replace("%type%", type).replace("%duration%", duration));
                    eb.addField(Files.discord.getString("mute.server.title"), Files.discord.getString("mute.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("mute.player.title"), Files.discord.getString("mute.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("mute.staff.title"), Files.discord.getString("mute.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("mute.footer.message"), Files.discord.getString("mute.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }else if(entry.getType().equals("warn")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("warn.author.message"), null, Files.discord.getString("warn.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("warn.color.red"), Files.discord.getInt("warn.color.green"), Files.discord.getInt("warn.color.blue")));
                    eb.setDescription(Files.discord.getString("warn.description").replace("%reason%", reason).replace("%type%", type));
                    eb.addField(Files.discord.getString("warn.server.title"), Files.discord.getString("warn.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("warn.player.title"), Files.discord.getString("warn.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("warn.staff.title"), Files.discord.getString("warn.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("warn.footer.message"), Files.discord.getString("warn.footer.image"));
                    textChannel.sendMessage(eb.build()).queue();
                }else if(entry.getType().equals("kick")){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(Files.discord.getString("kick.author.message"), null, Files.discord.getString("kick.author.image"));
                    eb.setColor(new Color(Files.discord.getInt("kick.color.red"), Files.discord.getInt("kick.color.green"), Files.discord.getInt("kick.color.blue")));
                    eb.setDescription(Files.discord.getString("kick.description").replace("%reason%", reason).replace("%type%", type));
                    eb.addField(Files.discord.getString("kick.server.title"), Files.discord.getString("kick.server.info").replace("%server%", server), true);
                    eb.addField(Files.discord.getString("kick.player.title"), Files.discord.getString("kick.player.info").replace("%player%", getBadge(entry.getUuid())), true);
                    eb.addField(Files.discord.getString("kick.staff.title"), Files.discord.getString("kick.staff.info").replace("%executor%", getBadge(entry.getExecutorUUID())), true);
                    eb.setFooter(Files.discord.getString("kick.footer.message"), Files.discord.getString("kick.footer.image"));
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
