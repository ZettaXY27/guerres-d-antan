package gda2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FactionControlExecutor implements CommandExecutor{
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		
		if (command.getName().equalsIgnoreCase("gda")) {
			if (extraArguments.length == 0) {
				sender.sendMessage("Usage: /gda (subcommand)");
				return true;
			} else {
				if (extraArguments[0].equalsIgnoreCase("create")) {
					String name = extraArguments[1];
					sender.sendMessage("you created: " + name);
					FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().createSection(name);
					FileManagerRegistrar.factionStorageFileManager.saveConfigFile();
					return true;
				}
				return true;
			}
		}
		return false;
			
	}

}
