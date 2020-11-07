package me.caledonian.hybridsync.commands.sync;

import me.caledonian.hybridsync.HybridSync;
import me.caledonian.hybridsync.discord.DiscordLink;
import me.caledonian.hybridsync.discord.Sync;
import me.caledonian.hybridsync.managers.CommandHandler;
import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.UUID;

public class Link implements CommandHandler {
    private JavaPlugin plugin;
    JDA bot;
    TextChannel textChannel;
    public Link(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
        textChannel = bot.getTextChannelById(Files.sync.getString("guild.sync-channel"));
    }

    String prefix = Utils.chat(Files.msgs.getString("core.prefix"));
    String user = null;
    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();
            if (p.hasPermission(Files.perms.getString("discord.link"))) {
                if (args.length == 0) {
                    p.sendMessage(Utils.chat(Files.msgs.getString("link.usage").replace("%prefix%", prefix)));
                } else if (args.length == 1) {
                    if(Data.get().getBoolean(uuid+"linked") == false){
                        if (DiscordLink.codes.containsValue(args[0].toString())) {
                            //user = bot.getUserById(DiscordLink.getKey(DiscordLink.codes, args[0].toString())).getAsTag();
                            p.sendMessage(Utils.chat(Files.msgs.getString("link.successful").replace("%prefix%", prefix)).replace("%user%", "a"));

                            bot.getUserById(DiscordLink.getKey(DiscordLink.codes, args[0].toString())).openPrivateChannel().queue((channel) ->{
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setAuthor(Files.sync.getString("messages.success.author.message"), null, Files.sync.getString("messages.success.author.image"));
                                eb.setColor(new Color(Files.sync.getInt("messages.success.color.red"), Files.sync.getInt("messages.success.color.green"), Files.sync.getInt("messages.success.color.blue")));
                                eb.addField(Files.sync.getString("messages.success.player.title"), Files.sync.getString("messages.success.player.info").replace("%player%", p.getName()), true);
                                eb.addField(Files.sync.getString("messages.success.rank.title"), Files.sync.getString("messages.success.rank.info").replace("%primary_rank%", HybridSync.getPermissions().getPrimaryGroup(p)).replace("%synced%", "false"), true);
                                eb.setFooter(Files.sync.getString("messages.success.footer.message"), Files.sync.getString("messages.success.footer.image"));
                                eb.setThumbnail(Files.sync.getString("messages.success.thumbnail-url").replace("%player%", p.getName()));
                                channel.sendMessage(eb.build()).queue();
                            });
                            Data.get().set(uuid + ".linked", true);
                            Data.get().set(uuid + ".discord", DiscordLink.getKey(DiscordLink.codes, args[0].toString()));
                            Data.save();
                            Sync.sync(p, bot, DiscordLink.getKey(DiscordLink.codes, args[0].toString()));

                            Bukkit.getScheduler().runTaskLater(plugin, () ->{
                                DiscordLink.codes.remove(args[0]);
                                if(Files.config.getBoolean("discord-sync.debug") == true){
                                    Bukkit.getConsoleSender().sendMessage(Utils.chat("&c(Debug) &fCodes for &c"+p.getName()+")&f were deleted. &c[Linked Account]"));
                                }
                            }, 30L);
                        }else{p.sendMessage(Utils.chat(Files.msgs.getString("link.invalid-code").replace("%prefix%", prefix).replace("%code%", args[0])));}
                    }else{p.sendMessage(Utils.chat(Files.msgs.getString("link.already-linked").replace("%prefix%", prefix).replace("%user%", bot.getUserById(Data.get().getString(uuid+".discord")).getAsTag())));}
                }
            }
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error")));}
    }
}
