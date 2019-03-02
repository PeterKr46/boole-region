package eu.saltyscout.booleregion.plugin;

import eu.saltyscout.booleregion.lang.Lang;
import eu.saltyscout.booleregion.plugin.command.ClipboardCommand;
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
