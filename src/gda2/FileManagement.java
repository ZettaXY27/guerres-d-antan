package gda2;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
/*import org.bukkit.entity.Creeper;*/
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManagement {

	private Main mainClass;
	private FileConfiguration fileConfig;// config
	private File actualFile;// configuration file
	private String name = "config.yml";

	/**
	 * Default constructor that creates the actual file with the default file
	 * -name of 'config.yml'.
	 */
	public FileManagement() {
		this.actualFile = new File(this.mainClass.getDataFolder(), this.name);
	}

	/**
	 * Allows for specification of the file object AND the file-name.
	 * @param actualFile the object representing the file
	 * @param name the name of the file
	 */
	public FileManagement(File actualFile, String name) {
		this.actualFile = actualFile;
		this.name = name;
	}
	
	/**
	 * Allows one to specify the file object, the object representing the main 
	 * class and the file-name. It also creates the file.
	 * @param actualFile the file object of the actual file
	 * @param mainClass the object of the main class which is derived from 
	 * JavaPlugin
	 * @param name the file-name of the file
	 */
	public FileManagement(File actualFile, Main mainClass, String name) {
		this.actualFile = actualFile;
		this.mainClass = mainClass;
		this.name = name;
		
		//Creates the file!!!! YEAHHH!!! Not sure why it is necessary
		this.actualFile = new File(this.mainClass.getDataFolder(), this.name);
		
	}

	/**
	 * Initializes FileManagement with a specified filename
	 * 
	 * @param name
	 *            the specified filename. If you do not specify it as an argument,
	 *            it will automatically be set to 'config.yml'.
	 */
	public FileManagement(String name) {
		this.actualFile = new File(mainClass.getDataFolder(), name);
	}

	public FileConfiguration getFileConfiguration() {
		return this.fileConfig;
	}

	public File getConfigFile() {
		return this.actualFile;
	}

	// Saves the file
	public void saveConfigFile() {
		if (this.fileConfig == null || this.actualFile == null) {
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
		if (actualFile == null) {
			actualFile = new File(mainClass.getDataFolder(), name);
		}
		fileConfig = YamlConfiguration.loadConfiguration(actualFile);
	}

}
