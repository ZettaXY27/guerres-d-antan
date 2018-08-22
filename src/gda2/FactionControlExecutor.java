package gda2;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionControlExecutor implements CommandExecutor {

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
					//attempts to create the faction, returns false if failed
					return createFaction(name, sender, uuid);
				}
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Creates the faction
	 * @param name faction name
	 * @param sender the sender of the command
	 * @param uuid the UUID of the player who sent the command
	 */
	private boolean createFaction(String name, CommandSender sender, UUID uuid) {
		//Make sure the name is not taken already
		if(nameIsTaken(name)) {
			return false;
		}
		
		Faction createdFaction = new Faction(name, uuid);
		sender.sendMessage("you created: " + name);
		FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.createSection(createdFaction.getFactionName());
		// It is really worth noting that there isn't going to be more than one LEADER
		// upon faction creation
		FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.getConfigurationSection(createdFaction.getFactionName())
				.set("Leaders", createdFaction.getMembersByRank(Rank.LEADER));
		FileManagerRegistrar.factionStorageFileManager.saveConfigFile();
		return true;
	}

	/**
	 * Returns FALSE if the FileConfiguration does not contain the faction name.
	 * @param factionName the faction name
	 * @return
	 */
	private boolean nameIsTaken(String factionName) {
		if(FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().contains(factionName)) {
			return true;
		}
		return false;
	}
}
