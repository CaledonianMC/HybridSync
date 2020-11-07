package me.caledonian.hybridsync.discord;

import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DiscordLink extends ListenerAdapter {
    public static HashMap<String, String> codes = new HashMap<String, String>();
    private JavaPlugin plugin;
    public DiscordLink(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase("s-link")){
            e.getChannel().sendMessage(Files.sync.getString("messages.start.guild-message"));
            String code = getSaltString();
            e.getAuthor().openPrivateChannel().queue((channel) ->{
                if(Files.config.getBoolean("discord-sync.debug") == true){
                    Bukkit.getConsoleSender().sendMessage(Utils.chat("&c(Debug) &fCodes generated for &c"+e.getAuthor().getAsTag()+")&f were created. &c[Ran Command]"));
                }

                codes.put(e.getAuthor().getId().toString(), code);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor(Files.sync.getString("messages.start.author.message"), null, Files.sync.getString("messages.start.author.image"));
                eb.setColor(new Color(Files.sync.getInt("messages.start.color.red"), Files.sync.getInt("messages.start.color.green"), Files.sync.getInt("messages.start.color.blue")));
                eb.addField(Files.sync.getString("messages.start.code.title"), Files.sync.getString("messages.start.code.info").replace("%code%", code), true);
                eb.addField(Files.sync.getString("messages.start.expires.title"), Files.sync.getString("messages.start.expires.title"), true);
                eb.setFooter(Files.sync.getString("messages.start.footer.message"), Files.sync.getString("messages.start.footer.image"));
                channel.sendMessage(eb.build()).queue();
                System.out.println(codes.get(e.getAuthor().getId()));
                System.out.println(codes.toString());
                System.out.println(getKey(codes, code));

            });
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if(codes.containsValue(code)) {
                        if (Files.config.getBoolean("discord-sync.debug") == true) {
                            Bukkit.getConsoleSender().sendMessage(Utils.chat("&c(Debug) &fCodes for &c" + e.getAuthor().getAsTag() + ")&f were deleted. &c[Codes Expired]"));
                        }
                    }
                }
            }, 1200L);
        }
    }
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static <K, V> K getKey(Map<K, V> map, V value){
        for(Map.Entry<K, V> entry : map.entrySet()){
            if(value.equals((entry.getValue()))){
                return entry.getKey();
            }
        }
        return null;
    }
}
