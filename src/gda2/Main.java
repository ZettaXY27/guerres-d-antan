package gda2;
//It is like despacito 2 but it is gda
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import org.bukkit.command.CommandExecutor;

/**
 * 
 * @author Seth
 *
 */
/*
 * Implement MAIN
 */
public class Main extends JavaPlugin {
	
	//private FileConfiguration fileConfig2;//See FileManagement class for the functionalities
	private FileManagement firstFileMngrForConfigFile;
	private FileManagement secondFileMngrForConfigFile;
	private FactionControlExecutor factionControlCommandExecutor;
	
	@Override
	//Everything in this method runs when the plugin is loaded.
	//Things like command registering and event listeners take place here
	public void onEnable() {
		
		//Command Executor List, used to register commands, use put(name, CommandExec)
		HashMap<String, CommandExecutor> commandList = new HashMap<String, CommandExecutor>();
		
		commandList.put("gda", factionControlCommandExecutor);

		for (String name : commandList.keySet()) {
		    getCommand(name).setExecutor(commandList.get(name));
		}
		
		firstFileMngrForConfigFile.reloadConfigFile();
		
	}
	
	
}
