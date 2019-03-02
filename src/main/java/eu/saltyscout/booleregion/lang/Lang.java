package eu.saltyscout.booleregion.lang;


import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Peter on 17-Nov-16.
 */
public class Lang {
    public synchronized static void load(Configuration configuration) {
        for (Field field : Lang.class.getDeclaredFields()) {
            if (!field.getType().isArray()) {
                try {
                    String str = configuration.getString(field.getName(), field.getName());
                    str = ChatColor.translateAlternateColorCodes('$', str);
                    field.set(null, str);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    List<String> cVal = configuration.getStringList(field.getName());
                    if (cVal != null) {
                        cVal = cVal.stream().map(str -> ChatColor.translateAlternateColorCodes('$', str)).collect(Collectors.toList());
                        field.set(null, cVal.toArray(new String[cVal.size()]));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String
            PLAYER_ONLY_COMMAND,
            PLAYER_NOT_FOUND,
            ERROR_OCCURRED,
            INVALID_SESSION,
            CLIPBOARD_EMPTY,
            CLIPBOARD_ENTRY_NOT_FOUND,
            CLIPBOARD_CONTENT_FORMAT,
            CLIPBOARD_CLONE_COMPLETE,
            INCOMPLETE_SELECTION,
            SELECTION_STORED,
            SELECTION_LOADED
    ;
}
