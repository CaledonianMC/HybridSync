package me.caledonian.hybridsync.utils;

import me.caledonian.hybridsync.HybridSync;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    public static File configFile;
    public static FileConfiguration config;

    public static File msgsFile;
    public static FileConfiguration msgs;

    public static File permFile;
    public static FileConfiguration perms;

    public static File discordFile;
    public static FileConfiguration discord;

    public static File syncFile;
    public static FileConfiguration sync;

    public static void base(HybridSync m) {
        if (!m.getDataFolder().exists()) {
            m.getDataFolder().mkdirs();
        }
        // config.yml
        configFile = new File(m.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File config.yml doesn't exist, creating one...");
            m.saveResource("config.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File config.yml was created.");
        }
        // msgs.yml
        msgsFile = new File(m.getDataFolder(), "msgs.yml");
        if (!msgsFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File msgs.yml doesn't exist, creating one...");
            m.saveResource("msgs.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File msgs.yml was created.");
        }
        // perms.yml
        permFile = new File(m.getDataFolder(), "perms.yml");
        if (!permFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File perms.yml doesn't exist, creating one...");
            m.saveResource("perms.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File perms.yml was created.");
        }
        // discord.yml
        discordFile = new File(m.getDataFolder(), "discord.yml");
        if (!discordFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File discord.yml doesn't exist, creating one...");
            m.saveResource("discord.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File discord.yml was created.");
        }

        // sync.yml
        syncFile = new File(m.getDataFolder(), "sync.yml");
        if (!syncFile.exists()) {
            Logger.log(Logger.LogLevel.INFO, "File sync.yml doesn't exist, creating one...");
            m.saveResource("sync.yml", false);
            Logger.log(Logger.LogLevel.SUCCESS, "File sync.yml was created.");
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        Logger.log(Logger.LogLevel.SUCCESS, "File config.yml was loaded");
        msgs = YamlConfiguration.loadConfiguration(msgsFile);
        Logger.log(Logger.LogLevel.SUCCESS, "File msgs.yml was loaded");
        perms = YamlConfiguration.loadConfiguration(permFile);
        Logger.log(Logger.LogLevel.SUCCESS, "File perms.yml was loaded");
        discord = YamlConfiguration.loadConfiguration(discordFile);
        Logger.log(Logger.LogLevel.SUCCESS, "File discord.yml was loaded");
        sync = YamlConfiguration.loadConfiguration(syncFile);
        Logger.log(Logger.LogLevel.SUCCESS, "File sync.yml was loaded");
    }
    public static void reloadConfig() {config = YamlConfiguration.loadConfiguration(configFile);}
    public static void reloadMsgs() {msgs = YamlConfiguration.loadConfiguration(msgsFile);}
    public static void reloadPerms() {perms = YamlConfiguration.loadConfiguration(permFile);}
    public static void reloadSync() {sync = YamlConfiguration.loadConfiguration(syncFile);}

    public static void saveConfig() throws IOException {config.save(configFile);}
    public static void saveMsgs() throws IOException {msgs.save(msgsFile);}
    public static void savePerms() throws IOException {perms.save(permFile);}
    public static void saveSync() throws IOException {sync.save(syncFile);}
}
