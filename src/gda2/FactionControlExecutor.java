package gda2;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionControlExecutor implements CommandExecutor{
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		Player player = (Player) sender;
		UUID uuid = player.getUniqueId();
		
		if (command.getName().equalsIgnoreCase("gda")) {
			if (extraArguments.length == 0) {
				sender.sendMessage("Usage: /gda (subcommand)");
				return true;
			} else {
				if (extraArguments[0].equalsIgnoreCase("create")) {
					String name = extraArguments[1];
					Faction createdFaction = new Faction(name, uuid);
					sender.sendMessage("you created: " + name);
					FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().createSection(createdFaction.getFactionName());
					FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().getConfigurationSection(createdFaction.getFactionName())
					.set("Executive", createdFaction.getMemberList().get(0));
					FileManagerRegistrar.factionStorageFileManager.saveConfigFile();
					return true;
				}
				return true;
			}
		}
		return false;
			
	}

}
