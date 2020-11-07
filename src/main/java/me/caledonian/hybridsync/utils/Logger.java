package me.caledonian.hybridsync.utils;

import org.bukkit.Bukkit;

public class Logger {
    public static void log(LogLevel l, String msg){
        if(msg == null) return;

        switch (l){
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(Utils.chat("&8(&c&lERROR&8) &f" + msg));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(Utils.chat("&8(&6&lWARNING&8) &f" + msg));
                break;
            case INFO:
                Bukkit.getConsoleSender().sendMessage(Utils.chat("&8(&b&lINFO&8) &f" + msg));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(Utils.chat("&8(&a&lSUCCESS&8) &f" + msg));
                break;
            case OUTLINE:
                Bukkit.getConsoleSender().sendMessage(Utils.chat("&f" + msg));
                break;
        }
    }
    public enum LogLevel {ERROR, WARNING, INFO, SUCCESS, OUTLINE}
}
