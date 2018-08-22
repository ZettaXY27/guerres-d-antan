package gda2;
//It is like despacito 2 but it is gda
import org.bukkit.plugin.java.JavaPlugin;

import zetta.CommandExec;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
	
	@Override
	//Everything in this method runs when the plugin is loaded.
	//Things like command registering and event listeners take place here
	public void onEnable() {
		
		//Command Executor List, used to register commands, use put(name, CommandExec)
//		HashMap<String, CommandExecutor> commandList = new HashMap<String, CommandExecutor>();
//		
//		commandList.put("gda", factionControlCommandExecutor);
//
//		for (String name : commandList.keySet()) {
//		    getCommand(name).setExecutor(commandList.get(name));
//		}	
			
		this.getCommand("gda").setExecutor(new FactionControlExecutor());
		
	}
	
	
}
