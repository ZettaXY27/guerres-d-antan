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
				// makes all arguments lowercase
				// String[] args = StringHelper.caseifyAll(extraArguments, false);

				if (extraArguments[0].equalsIgnoreCase("create")) {
					String name = extraArguments[1];
					// attempts to create the faction, returns false if failed
					return createFaction(name, sender, uuid);
				}
				if (extraArguments[0].equalsIgnoreCase("desc")) {
					String desc = extraArguments[1];
					if(isPlayerInAFaction(uuid)==false) {
						return false;
					} else
					return true;// setDescription(desc, faction)
				}
				return true;
			}
		}
		return false;

	}

	
	
	private boolean isPlayerInAFaction(UUID uuid) {
		boolean isInFaction = FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.getConfigurationSection("Members").contains(uuid.toString());
		return isInFaction;
	}

	/**
	 * Retrieves the name of the faction that the player is a member of
	 * 
	 * @return the name of the faction
	 */
	private String getPlayerFactionName(UUID uuid) {

		String playerFaction = FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.getConfigurationSection("Members").getString(uuid.toString());
		return playerFaction;
	}
	
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	private boolean isPlayerALeader(UUID uuid) {
		return FileManagerRegistrar.factionStorageFileManager.
				getFileConfiguration().getConfigurationSection("Leaders").contains(uuid.toString());
	}

	/**
	 * Creates the faction
	 * 
	 * @param name
	 *            faction name
	 * @param sender
	 *            the sender of the command
	 * @param uuid
	 *            the UUID of the player who sent the command
	 */
	private boolean createFaction(String name, CommandSender sender, UUID uuid) {
		// Make sure the name is not taken already
		if (nameIsTaken(name)) {
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
	 * Makes sure the description is valid and then sets the faction description
	 * @param description the faction description
	 * @param faction object representing the faction
	 * @return whether the command successfully completed
	 */
	private boolean setDescription(String description, Faction faction) {
		// First make sure the bloody description is valid
		if (StringHelper.isStringValid(description) == false) {
			return false;
		}
		
		faction.setDescription(description);

		// Set the description in the file
		FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.getConfigurationSection(faction.getFactionName()).set("Desc", faction.getDescription());
		// Save config file
		FileManagerRegistrar.factionStorageFileManager.saveConfigFile();

		return true;
	}

	/**
	 * Returns FALSE if the FileConfiguration does not contain the faction name.
	 * 
	 * @param factionName
	 *            the faction name
	 * @return
	 */
	private boolean nameIsTaken(String factionName) {
		if (FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().contains(factionName)) {
			return true;
		}
		return false;
	}
}
