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

public class Unlink implements CommandHandler {
    private JavaPlugin plugin;
    JDA bot;
    public Unlink(JavaPlugin plugin, JDA bot){
        this.plugin = plugin;
        this.bot = bot;
    }

    String prefix = Utils.chat(Files.msgs.getString("core.prefix"));

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            UUID uuid = p.getUniqueId();
            if(Data.get().getBoolean(uuid+".linked") == true){
                p.sendMessage(Utils.chat(Files.msgs.getString("unlink.successful").replace("%prefix%", prefix)));

                //this.bot.getUserById(Data.get().getString(p.getUniqueId()+".discord")).openPrivateChannel().queue((channel) ->{
                    //EmbedBuilder eb = new EmbedBuilder();
                    //eb.setAuthor(Files.sync.getString("messages.unlink.author.message"), null, Files.sync.getString("messages.unlink.author.image"));
                    //eb.setColor(new Color(Files.sync.getInt("messages.unlink.color.red"), Files.sync.getInt("messages.unlink.color.green"), Files.sync.getInt("messages.unlink.color.blue")));
                    //eb.setDescription(Files.sync.getString("messages.unlink.description"));
                    //eb.addField(Files.sync.getString("messages.unlink.player.title"), Files.sync.getString("messages.unlink.player.info").replace("%player%", p.getName()), true);
                    //eb.setFooter(Files.sync.getString("messages.unlink.footer.message"), Files.sync.getString("messages.unlink.footer.image"));
                    //eb.setThumbnail(Files.sync.getString("messages.unlink.thumbnail-url").replace("%player%", p.getName()));
                    //channel.sendMessage(eb.build()).queue();
                    //channel.sendMessage("yo").queue();
                //});
                //Sync.unsync(bot, Data.get().getString(uuid+".discord"));
                Data.get().set(uuid + ".linked", false);
                Data.get().set(uuid + ".discord", "0");
                Data.save();

            }else{p.sendMessage(Utils.chat(Files.msgs.getString("unlink.not-linked").replace("%prefix%", prefix)));}
        }else{Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error")));}
    }
}
