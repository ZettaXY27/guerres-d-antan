package gda2;
import java.io.File;
import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
//It is like despacito 2 but it is gda
import org.bukkit.plugin.java.JavaPlugin;


/**
 * 
 * @author Seth
 *
 */
/*
 * Implement MAIN
 */
public class Main extends JavaPlugin {
	
	//See FileManagement class for the functionalities
	private FileManagement firstFileMngrForConfigFile;
	private FileManagement secondFileMngrForConfigFile;
	
	@Override
	//Everything in this method runs when the plugin is loaded.
	//Things like command registering and event listeners take place here
	public void onEnable() {
		
		//Command Executor List, used to register commands, use put(name, CommandExec)
		HashMap<String, CommandExecutor> commandList = new HashMap<String, CommandExecutor>();

		//File creation
		FileManagerRegistrar.factionStorageFileManager = new FileManagement(new File(this.getDataFolder(), "factionStorage.yml"), "factionStorage.yml");
		FileManagerRegistrar.factionStorageFileManager.createConfigFile();
		FileManagerRegistrar.factionStorageFileManager.saveConfigFile();
	
		commandList.put("gda", new FactionControlExecutor());
		
		//Command Registering
		for (String name : commandList.keySet()) {
		    getCommand(name).setExecutor(commandList.get(name));
		}	
		
		//TODO: add event listener registration
		
	}
	
	
}
