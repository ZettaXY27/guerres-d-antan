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
					if (!isPlayerInAFaction(uuid)) {
						
						return false;
					} else {
						// setDescription(desc,faction);
				//		setDescription(desc, )
						return true;// setDescription(desc, faction)
					}
				}
				if (extraArguments[0].equalsIgnoreCase("me")) {
				//	sender.sendMessage("Your faction: " + getPlayerFactionName(uuid));
				//	sender.sendMessage("");
					sender.sendMessage("Attempt two: " + getPlayerFaction(uuid));
					sender.sendMessage("Am I in a faction? " + isPlayerInAFaction(uuid));
					return true;
				}

				return true;
			}
		}
		return false;

	}

	/**
	 * This method creates a temporary object representing a faction from
	 * save data.
	 * @param uuid
	 * @return
	 */
	private Faction getPlayerFactionObject(UUID uuid) {
		Faction fac = new Faction();
		
		return fac;
	}
	
	private String getPlayerFaction(UUID uuid) {
		String playerFaction = FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().getName();
		return playerFaction;
	}

	/**
	 * 
	 * @param uuid unique user id of the player
	 * @return whether the player is in the faction
	 */
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
		String playerFaction = "";
		playerFaction = FileManagerRegistrar.factionStorageFileManager.getFileConfiguration()
				.getConfigurationSection("Members").getString(uuid.toString());
		return playerFaction;
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	private boolean isPlayerALeader(UUID uuid) {
		return FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().getConfigurationSection("Leaders")
				.contains(uuid.toString());
	}

	/**
	 * Creates the faction
	 * 
	 * @param name   faction name
	 * @param sender the sender of the command
	 * @param uuid   the UUID of the player who sent the command
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
				.set("Members", createdFaction.getMembersByRank(Rank.LEADER));
		FileManagerRegistrar.factionStorageFileManager.saveConfigFile();

		// Add to player storage
		addToPlayerStorage(new Member(uuid, Rank.LEADER));

		return true;
	}

	private Member getPlayerFromStorage(UUID uuid) {
		// String aestheticTitle = FileManagerRegistrar.play.getFileConfiguration()
		// .getConfigurationSection("Members").getString(uuid.toString());
		String aestheticTitle = FileManagerRegistrar.playerStorageFileManager.getFileConfiguration()
				.getString(uuid.toString());

		return null;
		// return playerFaction;
	}

	/**
	 * Adds the given member of type Member to the player storage file
	 * 
	 * @param member everything about a given player that GDA cares about will go
	 *               into here
	 */
	private void addToPlayerStorage(Member member) {
		// Create section with the unique user iD
		FileManagerRegistrar.playerStorageFileManager.getFileConfiguration().
				createSection(member.getUUID().toString());
		// Sets Rank value
		FileManagerRegistrar.playerStorageFileManager.getFileConfiguration()
				.getConfigurationSection(member.getUUID().toString()).set("Rank:", member.getRank().toString());
		// Sets value for AestheticTitle
		FileManagerRegistrar.playerStorageFileManager.getFileConfiguration()
				.getConfigurationSection(member.getUUID().toString())
				.set("AestheticTitle:", member.getAestheticTitle());
		// Save
		FileManagerRegistrar.playerStorageFileManager.saveConfigFile();
	}

	/**
	 * Makes sure the description is valid and then sets the faction description
	 * 
	 * @param description the faction description
	 * @param faction     object representing the faction
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
	 * @param factionName the faction name
	 * @return
	 */
	private boolean nameIsTaken(String factionName) {
		if (FileManagerRegistrar.factionStorageFileManager.getFileConfiguration().contains(factionName)) {
			return true;
		}
		return false;
	}
}
