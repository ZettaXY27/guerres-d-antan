package gda2;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
/*import org.bukkit.entity.Creeper;*/
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManagement {
	
	private Main mainClass;
	private FileConfiguration fileConfig;//config
	private File actualFile;//configuration file
	private String name;
	
	public FileManagement() {
		actualFile = new File(mainClass.getDataFolder(), name);
	}
	
	public FileConfiguration getFileConfiguration() {
		return this.fileConfig;
	}
	
	public File getConfigFile() {
		return this.actualFile;
	}
	
	// Saves the file
	public void saveConfigFile() {
		if (fileConfig == null || actualFile == null) {
			return;
		}
		try {
		    getFileConfiguration().save(actualFile);
		} catch (IOException ex) {
			mainClass.getLogger().log(Level.SEVERE, "Could not save config to " + actualFile, ex);
		}
	}
	
	// Reloads the file, if it doesn't exist, it creates it
	public void reloadConfigFile() {
		if(actualFile == null) {		
			actualFile = new File(mainClass.getDataFolder(), name);
		}
		fileConfig = YamlConfiguration.loadConfiguration(actualFile);
	}

	
}
