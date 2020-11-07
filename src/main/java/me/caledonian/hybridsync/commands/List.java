package me.caledonian.hybridsync.commands;

import me.caledonian.hybridsync.managers.CommandHandler;
import me.caledonian.hybridsync.utils.Files;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List implements CommandHandler {
    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Player p = (Player) sender;
        for(String string : Files.config.getConfigurationSection("list").getKeys(false)){
            p.sendMessage(Files.config.getString("list."+string+".string"));
        }
    }
}
