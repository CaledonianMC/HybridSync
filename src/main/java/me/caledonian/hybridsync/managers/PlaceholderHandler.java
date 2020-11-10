package me.caledonian.hybridsync.managers;

import me.caledonian.hybridsync.utils.Data;
import me.caledonian.hybridsync.utils.Files;
import me.caledonian.hybridsync.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderHandler extends PlaceholderExpansion{
    @Override
    public String getIdentifier() {
        return "hybridsync";
    }

    @Override
    public String getAuthor() {
        return "Caledonian";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }
    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if(p == null){
            return "ERROR: Null";
        }
        if (params.equals("islinked")){
            if(Data.get().getBoolean(p.getUniqueId()+".linked") == true){
                return Utils.chat(Files.config.getString("placeholders.isvanished.true"));
            }else{
                return Utils.chat(Files.config.getString("placeholders.isvanished.false"));
            }
        }
        if (params.equals("linkedto")){
            if(Data.get().getBoolean(p.getUniqueId()+".linked") == false){
                return Utils.chat(Files.config.getString("placeholders.linkedto.not-linked"));
            }else{Utils.chat(Files.config.getString("placeholders.linkedto.user").replace("%user%", "Not supported yet!"));}
        }
        return null;
    }
}
