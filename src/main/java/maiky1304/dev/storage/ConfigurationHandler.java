package maiky1304.dev.storage;

import maiky1304.dev.PrisonEscape;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationHandler {

    private File file;
    private FileConfiguration config;

    private PrisonEscape plugin;
    private String fileName;

    public ConfigurationHandler(PrisonEscape plugin, String filename){
        this.plugin = plugin;
        this.fileName = filename;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig(){
        try {
            config.save ( file );
        } catch (IOException e){
            e.printStackTrace ();
        }
    }

    public void loadConfig() {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        config= new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
