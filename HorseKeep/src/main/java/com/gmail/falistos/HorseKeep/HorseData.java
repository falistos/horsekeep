package main.java.com.gmail.falistos.HorseKeep;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class HorseData {

	private String horsesDataFilePath = null;
	private FileConfiguration horsesData = null;
	private File horsesDataFile = null;
	
	private Plugin plugin;
	
	public HorseData(Plugin plugin, String path)
	{
		this.horsesDataFilePath = path;
		this.plugin = plugin;
		this.reload();
	}
	
	// Migration for < 0.2.2 versions
	public void migrate()
	{
		if (plugin.getConfig().get("horses") != null && getHorsesData().get("horses") == null)
		{
			ConfigurationSection horses = plugin.getConfig().getConfigurationSection("horses");

			getHorsesData().set("horses", horses);
			plugin.getConfig().set("horses", null);

			save();
			plugin.saveConfig();
			
			plugin.getLogger().info("Migrated horse data to horses.yml successfully");
		}
	}
	
	public void reload() {
	    if (horsesDataFile == null) {
	    	horsesDataFile = new File(plugin.getDataFolder(), horsesDataFilePath);
	    }
	    horsesData = YamlConfiguration.loadConfiguration(horsesDataFile);

	    InputStream defConfigStream = plugin.getResource(horsesDataFilePath);
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        horsesData.setDefaults(defConfig);
	    }
	    
	    this.save();
	}
	
	public FileConfiguration getHorsesData() {
	    if (horsesData == null) {
	        this.reload();
	    }
	    return horsesData;
	}
	
	public void save() {
	    if (horsesData == null || horsesDataFile == null) {
	    	return;
	    }
	    try {
	        getHorsesData().save(horsesDataFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save horse data to " + horsesDataFile, ex);
	    }
	}
	
}
