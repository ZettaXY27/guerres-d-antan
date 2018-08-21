package gda2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FactionControlExecutor implements CommandExecutor{
	
	private FileManagement factionStorageFileManager = new FileManagement("factionStorage.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		
		if (command.getName().equalsIgnoreCase("gda")) {
			if (extraArguments.length == 0) {
				sender.sendMessage("Usage: /gda (subcommand)");
			} else {
				if (extraArguments[0].equalsIgnoreCase("create")) {
					String name = extraArguments[1];
					factionStorageFileManager.getFileConfiguration().createSection(name);
					return true;
				}
			}
		}
		return false;
			
	}

}
