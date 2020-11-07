package me.caledonian.hybridsync.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandHandler {
    // Don't touch or I will kill u
    void execute (CommandSender sender, Command command, String[] args);
}
