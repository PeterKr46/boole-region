package com.gmail.pkr4mer.boole.plugin;

import com.gmail.pkr4mer.boole.lang.Lang;
import com.gmail.pkr4mer.boole.plugin.command.ClipboardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Peter on 03-Dec-16.
 */
public class BooleRegionPlugin extends JavaPlugin {
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    @Override
    public void onEnable() {
        File langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            saveResource("lang.yml", false);
        }
        Lang.load(YamlConfiguration.loadConfiguration(langFile));
        getCommand("clip").setExecutor(new ClipboardCommand(this));
        sessionFactory = new SessionFactory();
    }
}
