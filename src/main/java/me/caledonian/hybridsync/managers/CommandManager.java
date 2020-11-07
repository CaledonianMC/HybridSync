package me.caledonian.hybridsync.managers;

import me.caledonian.hybridsync.commands.HybridSync;
import me.caledonian.hybridsync.commands.sync.Link;
import me.caledonian.hybridsync.commands.List;
import me.caledonian.hybridsync.commands.sync.Unlink;
import net.dv8tion.jda.api.JDA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private Map<String, CommandHandler> commands = new HashMap<>();
    private JavaPlugin javaPlugin;
    private JDA bot;
    public CommandManager(JavaPlugin javaPlugin, JDA bot){
        this.javaPlugin = javaPlugin;
        this.bot = bot;
        initCommands();
    }
    private void initCommands(){
        // Core
        commands.put("hybridsync", new HybridSync(javaPlugin, bot));
        commands.put("link", new Link(javaPlugin, bot));
        commands.put("unlink", new Unlink(javaPlugin, bot));
        commands.put("list", new List());
        registerCommands();
    }
    private void registerCommands() { commands.forEach((s, c) -> javaPlugin.getCommand(s).setExecutor(this));}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdname = command.getName();
        CommandHandler commandHandler = commands.get(cmdname);
        if(commandHandler != null) commandHandler.execute(sender, command,args);
        return false;
    }
}
