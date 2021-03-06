
package zetta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;;

@SuppressWarnings("unused")
public class CommandExec implements CommandExecutor {
	// Variable declaration field
	final static HashMap<String, BukkitTask> taskIDs = new HashMap<String, BukkitTask>();
	final static HashMap<String, BukkitTask> TPtaskIDs = new HashMap<String, BukkitTask>();
	Main plugin;
	Economy economy = null;
	ChunkManagement chunkManagement;
	HashMap<String, String> inviteStore = new HashMap<String, String>();
	String chunkAmount;

	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}
    
	/**
	 * @author ZettaX
	 * @param relationType
	 *         The relation type that you wish to set. Ally or Enemy, neutral is default.
	 * @param faction1
	 *         The faction you wish to set the relation from.
	 * @param faction2
	 *         The faction you wish to set the relation to.
	 */
	boolean setRelation(String relationType, String faction1, String faction2) {
			
		List<String> relationListByType_f1 = plugin.getSecondConfig().getConfigurationSection(faction1).getStringList(relationType);
		List<String> relationListByType_f2 = plugin.getSecondConfig().getConfigurationSection(faction2).getStringList(relationType);
		
		if(relationType.equalsIgnoreCase("Ally")) {
			relationListByType_f1.add(faction2);
			plugin.getSecondConfig().getConfigurationSection(faction1).set(relationType, relationListByType_f1);
			relationListByType_f2.add(faction1);
			plugin.getSecondConfig().getConfigurationSection(faction2).set(relationType, relationListByType_f2);
			plugin.saveSecondConfig();
			plugin.getLogger().info("RelationType Enemy has been set between " faction2 + " ahd " + faction1);
			return true;
		}
		if(relationType.equalsIgnoreCase("Enemy")) {
			relationListByType_f1.add(faction2);
			plugin.getSecondConfig().getConfigurationSection(faction1).set(relationType, relationListByType_f1);
			relationListByType_f2.add(faction1);
			plugin.getSecondConfig().getConfigurationSection(faction2).set(relationType, relationListByType_f2);
			plugin.saveSecondConfig();
			plugin.getLogger().info("RelationType Enemy has been set between " faction2 + " ahd " + faction1);
			return true;
		}
		return false;
		
	}
	
	List<String> listChunks(String factionName) {
		List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(factionName).getStringList("Chunks");
		return chunkList;

	}
	
	boolean getChangeLogBook(CommandSender sender){
		if(sender instanceof Player == false){
			sender.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR+"Only players can use this command.");
			return false;
		}
		Player player = (Player)sender;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		
		String[] pageArray = new String[] {
				StringConstants.LOGBOOK_PAGE01,
				StringConstants.LOGBOOK_PAGE02,
				StringConstants.LOGBOOK_PAGE03,
				StringConstants.LOGBOOK_PAGE04,
				StringConstants.LOGBOOK_PAGE05,
				StringConstants.LOGBOOK_PAGE06,
				StringConstants.LOGBOOK_PAGE07
		};
		
		bookMeta.setPages( pageArray );
		bookMeta.setAuthor("Inivican");
		bookMeta.setTitle("Changelog 1.0 by Zetta & Inivican");
		
		book.setItemMeta(bookMeta);
		player.getInventory().addItem(book);
		sender.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "Enjoy your very comprehensive book.");
		return true;
	}
	
	
	// Invite check, will return NPE if the HashMap is null.
	boolean playerHasBeenInvited(String player1, String player2) {
		// Value is normally assigned to player1, which is the key
		boolean isInvited = inviteStore.containsKey(player1) && inviteStore.containsValue(player2);
		return isInvited;
	}

	boolean playerHasEnoughBalance(String playerName, int amount) {
		boolean hasEnoughMoney = economy.has(playerName, amount);
		return hasEnoughMoney;

	}

	boolean factionHasEnoughBalance(String bankName, double amount) {
		boolean hasEnoughMoney = economy.bankHas(bankName, amount) != null;
		return hasEnoughMoney;
	}

	boolean playerIsInAFaction(String playerName) {
		boolean isInFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").contains(playerName);
		return isInFaction;
	}

	String getPlayerFaction(String playerName) {
		String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerName);
		return playerFaction;
	}

	// String final getPlayerFaction(String playerName) {
	// String playerFaction =
	// plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerName);
	// return playerFaction;
	// }

	boolean playerIsMember(String playerName, String factionName) {
		boolean playerIsMember = plugin.getSecondConfig().getConfigurationSection(factionName).getStringList("Members")
				.contains(playerName);
		return playerIsMember;
	}

	String getLeaderName(String factionName) {
		return plugin.getSecondConfig().getConfigurationSection(factionName).getString("Owner");
	}

	List<String> listOfficialNames(String factionName, Player player) {
		List<String> officerList;
		return officerList = (plugin.getSecondConfig().getConfigurationSection(factionName)).getStringList("Officers");
	}

	/**
	 * @author Inivican
	 * @param factionName
	 * @return
	 */
	List<String> listMemberNames(String factionName) {
		List<String> memberList = plugin.getSecondConfig().getConfigurationSection(factionName)
				.getStringList("Members");
		return memberList;
	}

	/**
	 * @author ZettaX
	 * @param factionName
	 * @return
	 */
	Boolean playerIsLeader(String factionName, String playerName) {
		Boolean playerIsLeader = plugin.getSecondConfig().getConfigurationSection(factionName).getString("Owner")
				.equals(playerName);
		return playerIsLeader;
	}

	/**
	 * @author Inivican
	 * @param subfactionName the name of the subfaction.
	 * @param playerName the player we are trying to determine association with the subfaction (as in, whether the player is a leader).
	 * @return success of the method
	 */
	Boolean playerIsSubfactionLeader(String subfactionName, String playerName){
		Boolean playerIsSubfactionLeader = plugin.getSecondConfig().getConfigurationSection(subfactionName).getString("Owner")
				.equals(playerName);
		return playerIsSubfactionLeader;
	}
	
	/**
	 * @author Inivican
	 * @param subfactionName the name of the subfaction.
	 * @param playerName the player we are trying to determine association with the subfaction (as in, whether the player is leader of the faction that the subfaction is a part of).
	 * @return
	 */
	Boolean playerIsMasterOfSubfaction(String subfactionName, String playerName){
		Boolean playerIsMasterOfSubfaction = plugin.getSecondConfig().getConfigurationSection(subfactionName).getString("Master")
				.equals(playerName);
		
		return playerIsMasterOfSubfaction;
	}
	
	/**
	 * @author Inivican
	 * @param factionName
	 *            the required faction name as a String
	 * @return Returns a list of all the citizens in a given faction
	 */
	List<String> listCitizens(String factionName) {
		List<String> officerList = plugin.getSecondConfig().getConfigurationSection(factionName)
				.getStringList("Officers");
		List<String> citizenList = officerList;
		String ownerName = plugin.getSecondConfig().getConfigurationSection(factionName).getString("Owner");
		citizenList.add(ownerName);
		citizenList.addAll(listMemberNames(factionName));

		return citizenList;
	}

	/**
	 * @author Inivican
	 * @param factionName
	 * @param leadersAndOfficialsOnly
	 *            just needed for overloading. I felt lazy.
	 * @return Returns Officers and the Leader BUT NOT the citizens.
	 */
	List<String> listCitizens(String factionName, boolean leadersAndOfficialsOnly) {
		List<String> officerList = plugin.getSecondConfig().getConfigurationSection(factionName)
				.getStringList("Officers");
		List<String> citizenList = officerList;
		String ownerName = plugin.getSecondConfig().getConfigurationSection(factionName).getString("Owner");
		citizenList.add(ownerName);

		return citizenList;
	}

	/**
	 * A pretty superfluous method wherein you supply a
	 * @param player object
	 * @return and get a string name of the player
	 */
	String getPlayerName(Player player){
		return player.getName();
	}
	
	
	
	/**
	 * @author Inivican
	 * @param subfactionName === name of subfaction
	 * @param factionName just that, the faction name we are dealing with
	 * @param sender the sender of the motherflipping command, by George
	 * @return command success status
	 */
	boolean createSubfaction(String subfactionName, String factionName, CommandSender sender){
		Boolean infiniteSubFactions = true;
		
		if(subfactionName == null||subfactionName==""||subfactionName==" "||subfactionName.contains(" ")){
			sender.sendMessage(StringConstants.MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS+"; You need to put in a name");
			return false;
		}
		else if(subfactionName.length() > 10){
			sender.sendMessage(StringConstants.MESSAGE_ERROR_TOO_LONG);
			return false;
		}
		String playerName = sender.getName();
		String playerFaction = getPlayerFaction(playerName);
		
		// If the player, as in sender of the command, is either the leader of the faction or 
		//the leader of a subfaction and the creation of nested subfactions is allowed, then allow for the creation of a new subfaction.
		if( playerIsLeader(playerFaction,playerName ) == true ||
				( playerIsSubfactionLeader(factionName, playerName)==true&& infiniteSubFactions==true )){
			if(plugin.getSecondConfig().contains(subfactionName)){
				sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR+"There is already a subfaction/nation called "+subfactionName+" already!");
				return false;//unsuccessful.
			}else{
				if(plugin.getSecondConfig().contains("Citizens")==false){
					plugin.getSecondConfig().createSection("Citizens");
				} //else if (plugin.getSecondConfig().getConfigurationSection("Citizens").contains(playerName)){
					// this is allowed because the player has to be a citizen of a country to create a subfaction of a given country
					// but subfactions are treated as different faction entries and thus the player is moved into the subfaction
				plugin.getSecondConfig().createSection(subfactionName);	
				plugin.getSecondConfig().getConfigurationSection(subfactionName).set("Master", playerName);
				plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, subfactionName);
				plugin.getChunkSavesFile().createSection(subfactionName).createSection("Chunks");
				List<String> factionList = plugin.getSecondConfig().getStringList("FactionList");
				factionList.add(subfactionName);
				plugin.getSecondConfig().set("FactionList", factionList);
				ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile,
						plugin.chunkSavesFileConfiguration );
				plugin.saveSecondConfig();
				/**
				 * We do not immediately create a bank for a
				 *  subfaction because that is up to
				 *   the discretion of the parent nation
				*/
				
				sender.sendMessage(StringConstants.MESSAGE_PREFIX_INFO+"Subfaction "+
				subfactionName+" has been created with "+playerName+" set as its maintainer! Do [/gda subfaction "+
				subfactionName+" claim] to claim a chunk in your faction for the subfaction!"
				);
				
				return true;
			}
			
			
			
		}
		
		
		
		
		return false;
	}
	
	
	
	/**
	 * @Author ZettaX
	 * @param playerList
	 *            A list of players
	 * @return Returns true if one of the players in the list is online
	 */
	@SuppressWarnings("deprecation")// Note: this method does NOT work in Minecraft 1.8 or newer.
	boolean atleastOneIsOnline(List<String> playerList) {
		for (String i : playerList) {
			if (plugin.getServer().getPlayerExact(i) != null) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * @author Inivican
	 * @param playerChunkX
	 * @param playerChunkZ
	 * @return whether or not the claimed chunk is claimed
	 */
	boolean detectWhetherChunkIsClaimed(int playerChunkX, int playerChunkZ) {

		if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
				.contains(playerChunkX + "," + playerChunkZ))
			return true;
		return false;
	}

	/**
	 * @author Inivican
	 * @param player
	 * @return whether or not the chunk is claimed
	 */
	boolean detectWhetherChunkIsClaimed(Player player) {
		int playerChunkX = player.getLocation().getChunk().getX();
		int playerChunkZ = player.getLocation().getChunk().getZ();
		if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
				.contains(playerChunkX + "," + playerChunkZ))
			return true;
		return false;
	}

	// List<String> getNationsInArea(){
	// List<String> listOfNations = ;
	// return listOfNations;
	// }
	
	/**
	 * A really simple helper method for getting the X player chunk
	 * @param player the player in question
	 * @return returns the integer value of the X
	 * 
	 * @author Method implemented by Inivican
	 */
	int getPlayerChunkX(Player player){
		return player.getLocation().getChunk().getX();
	}
	
	/**
	 * A really simple helper method for getting the Z player chunk
	 * @param player the player in question
	 * @return returns the integer value of the Z
	 * 
	 * @author Method implemented by Inivican
	 */
	int getPlayerChunkZ(Player player){
		return player.getLocation().getChunk().getZ();
	}
	
	/**
	 * @author Inivican
	 * This method gets the visa holders list for a given faction
	 * @param factionName THE STRING VARIABLE REPRESENTING A FACTION NAME
	 * @return RETURNS A STRING LIST
	 */
	List<String> getVisaList(String factionName){
		
		
		List<String> result = plugin.getSecondConfig().getConfigurationSection(factionName)
				.getStringList("Visas");
		
		return result;
	}
	
	/**
	 * @author Inivican
	 * @note <b>This method might not work!</b></br> Consider utilizing List<String> getVisaList() instead. 
	 * Overloaded version for arrays
	 * @param factionName
	 * @param resultIsArray
	 * @return the visaList as a string array
	 */
	String[] getVisaList(String factionName, boolean resultIsArray){
		List<String> resultList = plugin.getSecondConfig().getConfigurationSection(factionName)
				.getStringList("Visas");
		String[] resultArray = (String[]) resultList.toArray();
		return resultArray;
	}
	
	/**
	 * TODO: Implement
	 * 
	 * @author Inivican
	 * @param sender
	 * @param player
	 * @param args  Arguments (like {nation} )
	 * @return
	 */
    boolean showVisaCarriersForFaction(CommandSender sender, Player player, String[] args){
    	if(args.length<1){
    		sender.sendMessage(StringConstants.MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS);
    		return false;
    	}
    	List<String> visaList = getVisaList(getPlayerFaction(player.getName()));
    	sender.sendMessage(StringConstants.MESSAGE_PREFIX_OK+"Visastats for "+args[1]+"!");
    	sender.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
    	for(int i = 0;i<visaList.size();i++){
    		sender.sendMessage(visaList.get(i));
    	}
    	sender.sendMessage("Total:"+visaList.size());
    	sender.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
    	return true;
    }
	
    
    
	
	/**
	 * A reimplementation of showChunkMap by @author Inivican from 25 June, 2017
	 * @param sender the individual who issued the command
	 * @param player the player object
	 * @return Whether the command was successful in being issued!
	 * 
	 * @author Method implemented by Inivican
	 */
	boolean showChunkMap(CommandSender sender, Player player){
		
		// Player position, motherfucker
		int x = getPlayerChunkX(player);
		int z = getPlayerChunkZ(player);
		
		// Initialize the message string
		String message = "";
		
		// Initialize factions counter for the area
		int factions = 0;
		String lastFactionNoticed = "";
		
		// Initialize area factions list
		List<String> factionList = null;
		
		sender.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GOLDE);
		
		
		for(int currentX = x - 10; currentX < x + 10; currentX++){
			
			for(int currentZ = z - 10; currentZ < z + 40; currentZ++){
				
				
				if(detectWhetherChunkIsClaimed(currentX, currentZ) ){
					
					// Get the name of the faction that owns the territory
					String claimedChunkFactionName = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
							.getString(currentX+","+currentZ);
					
					if(factionList!=null &&  factionList.contains(claimedChunkFactionName)){
						
						//The position in the list that a faction name is in will determine what color its territory appears as.
						int positionInList = factionList.indexOf(claimedChunkFactionName);
						
						// Evaluate the position in the list that the faction name is in.
						switch(positionInList){
						case 01:
							message += ChatColor.BLUE + "#";
							break;
						case 02:
							message += ChatColor.GREEN + "#";
							break;
						case 03:
							message += ChatColor.RED + "#";
							break;
						case 04:
							message += ChatColor.DARK_PURPLE + "#";
							break;
						case 05:
							message += ChatColor.YELLOW + "#";
							break;
						case 06:
							message += ChatColor.DARK_GRAY + "#";
							break;
						case 07:
							message += ChatColor.AQUA + "#";
							break;
						default:
							message += ChatColor.WHITE+""+ ChatColor.UNDERLINE+"#";
							break;
						}
						
						
					}
					else{
						factionList.add(claimedChunkFactionName);
						factions++;
					}
					
				}
				
				else{
					if((currentX == x) && (currentZ==z)){
						message += ChatColor.WHITE + "@";
					}
				}
				
			}
			sender.sendMessage(message);
			
		}
		sender.sendMessage("Number of factions in the area:"+factions);
		sender.sendMessage("# is claimed territory");
		sender.sendMessage("");
		if(factionList!=null){
			for(int i = 0;i<factionList.size();i++){
				sender.sendMessage(i+". "+factionList.indexOf(i) );
			}
		}
		return true;
	}
	
	
	/**
	 * @author Inivican
	 * @desc shows a general "map" of the chunks in the area
	 * @param sender
	 * @param player
	 * @return Whether the command completed successfully
	 */


	/**
	 * FOR THE COMMAND /gda setvisa (playername) SETVISA / ACTIVATE VISA
	 * 
	 * @author Inivican
	 * @param player
	 *            The minecraft player
	 * @param sender
	 *            The sender of the command
	 * @param arguments
	 *            The extraArguments[]
	 * @return Whether or not the command completes successfully
	 * @note It is really getting hard to navigate this class file to find
	 *       methods.
	 */
	boolean activatePlayerVisa(CommandSender sender, String[] arguments, Player player, String playerFaction) {
		if (arguments.length < 1) {
			sender.sendMessage(StringConstants.MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS);
			return false;
		}
		String playerName = arguments[1];
		playerFaction = getPlayerFaction(sender.getName());
		if (playerFaction == null) {
			sender.sendMessage(
					StringConstants.MESSAGE_PREFIX_ERROR + "You cannot set visa when you aren't even in a nation!");
			return false;
		}
		String factionNameOfSender = playerFaction;
		if (listCitizens(factionNameOfSender).contains(playerName) == true) {
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
					+ "The individual you are trying to set a visa for is a member of your nation already.");
			return false;
			// next line will throw npe lol
		} else if (plugin.getSecondConfig().getConfigurationSection(factionNameOfSender)
				.getStringList("Visas") == null) {
			List<String> visaList = plugin.getSecondConfig().getConfigurationSection(factionNameOfSender)
					.getStringList("Visas");
			plugin.getSecondConfig().getConfigurationSection(factionNameOfSender).set("Visas", visaList);
			plugin.saveSecondConfig();
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
					+ "Created visas section as there hadn't been one in your secondConfig.yml file.");
			return true;
		}

		// Check to make sure the applicant isn't already a visa owner
		else if (plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Visas")
				.contains(playerName) == false) {
			if (listCitizens(factionNameOfSender, true).contains(sender.getName()) == false) {
				sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
						+ "Only an authorized member of a nation can activate a visa!");
				return false;
			}
			List<String> visaList = plugin.getSecondConfig().getConfigurationSection(factionNameOfSender)
					.getStringList("Visas");
			plugin.saveSecondConfig();
			visaList.add(playerName);
			plugin.getSecondConfig().getConfigurationSection(factionNameOfSender).set("Visas", visaList);
			plugin.saveSecondConfig();
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "Granted visa to " + playerName + " for "
					+ factionNameOfSender);

		} else {
			player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
					+ "The invidual you are tring to set a visa for already has a visa for your nation.");
			return false;
		}

		return true;
	}

	/**
	 * @author Inivican
	 * @param player
	 *            the player
	 * @param arguments
	 *            ExtraArguments[]
	 * @param sender
	 *            sender of the command
	 * @return WHETHER OR NOT THE COMMAND WAS SUCCESSFUL IN EXECUTION
	 */
	boolean deactivatePlayerVisa(Player player, String[] arguments, CommandSender sender) {
		if (arguments.length < 1) {
			sender.sendMessage(StringConstants.MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS);
			return false;
		}
		String playerName = arguments[1];
		if (getPlayerFaction(sender.getName()) == null) {
			sender.sendMessage(
					StringConstants.MESSAGE_PREFIX_ERROR + "You cannot set visa when you aren't even in a nation!");
			return false;
		}
		String factionNameOfSender = getPlayerFaction(sender.getName());
		if (listCitizens(factionNameOfSender).contains(playerName) == true) {
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
					+ "The individual you are trying to deactivate a visa for is a member of your nation.");
			return false;
		} else if (plugin.getSecondConfig().getConfigurationSection(factionNameOfSender)
				.getStringList("Visas").contains(playerName) == true) {
			if (listCitizens(factionNameOfSender, true).contains(sender.getName()) == false) {
				sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
						+ "Only an authorized member of a nation can deactivate a visa!");
				return false;
			}
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_OK + playerName
					+ " no longer has a visa for  " + factionNameOfSender + "!");
			List<String> visaList = plugin.getSecondConfig().getConfigurationSection(factionNameOfSender)
					.getStringList("Visas");
			visaList.remove(playerName);
			plugin.getSecondConfig().getConfigurationSection(factionNameOfSender).set("Visas", visaList);
			plugin.saveSecondConfig();
			return true;

		}
		else {
			sender.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
					+ "That player does not have a visa for your nation!");
		}

		return true;
	}

	/**
	 * 
	 * @param player
	 * @param sender
	 * @param arguments
	 * @return returns the command status (whether successful or not)
	 */
	boolean showNationStats(Player player, CommandSender sender, String[] arguments) {

		List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
				.getStringList("Chunks");
		List<String> officerList = plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
				.getStringList("Officers");
		plugin.saveSecondConfig();
		Boolean playerIsOfficer = officerList.contains(player.getName());
		Boolean playerIsLeader = plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
				.getString("Owner").equals(sender.getName());

		String factionName = arguments[1];
		String ownerName = plugin.getSecondConfig().getConfigurationSection(factionName).getString("Owner");

		List<String> citizenList = officerList;
		citizenList.add(ownerName);
		citizenList.addAll(listMemberNames(factionName));

		economy = plugin.getEconomy();

		sender.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
		sender.sendMessage("Number of members:" + citizenList.toArray().length);
		sender.sendMessage("Money:" + economy.bankBalance(factionName).balance);
		sender.sendMessage("Leader: " + ownerName);
		for (String i : citizenList) {
			sender.sendMessage(i);
		}

		return true;
	}

	// Actual command, this has to be registered in the main class
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		Player player = (Player) sender;
		UUID ID = player.getUniqueId();
		if (command.getName().equalsIgnoreCase("test")) {
			sender.sendMessage(
					ChatColor.AQUA + "Yeah, it's working, what a miracle. Now, get your head out of your ass");
			return true;
		} else if (command.getName().equalsIgnoreCase("gda")) {
			if (extraArguments.length == 0) {
				sender.sendMessage("Usage: /gda (subcommand)");
			} else {
				if (extraArguments[0].equalsIgnoreCase("claim")) {
					economy = plugin.getEconomy();
					int playerChunkX = player.getLocation().getChunk().getX();
					int playerChunkZ = player.getLocation().getChunk().getZ();
					// String playerFaction =
					// plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
					if (plugin.getSecondConfig().getConfigurationSection("Citizens").contains(sender.getName())) {
						// TODO: Add faction check for player
						if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks") == null) {
							plugin.getChunkSavesFile().createSection("ClaimedChunks");
							plugin.saveChunkSavesFile();
							player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
							return true;
						}
						if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
								.contains(playerChunkX + "," + playerChunkZ)) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "This land is already claimed by "
									+ ChatColor.GOLD + plugin.chunkSavesFile.getConfigurationSection("ClaimedChunks")
											.getString(playerChunkX + "," + playerChunkZ));
							return true;
						} else {
							if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
									.contains("Chunks") == true) {
								if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
										.getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == false) {
									if (plugin.chunkSavesFile.contains("ClaimedChunks")) {
										List<String> chunkList = plugin.chunkSavesFile
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getStringList("Chunks");
										List<String> officerList = plugin.getSecondConfig()
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getStringList("Officers");
										plugin.saveSecondConfig();
										Boolean playerIsOfficer = officerList.contains(player.getName());
										Boolean playerIsLeader = plugin.getSecondConfig()
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getString("Owner").equals(sender.getName());
										if (playerIsOfficer == true || playerIsLeader == true) {
											int claimCost = plugin.getConfig().getInt("ClaimCost");
											EconomyResponse r = economy.bankWithdraw(getPlayerFaction(player.getName()),
													claimCost);
											if (r.transactionSuccess()) {
												chunkList.add(playerChunkX + "," + playerChunkZ);
												plugin.chunkSavesFile
														.getConfigurationSection(getPlayerFaction(player.getName()))
														.set("Chunks", chunkList);
												plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").set(
														playerChunkX + "," + playerChunkZ,
														getPlayerFaction(player.getName()));
												plugin.saveChunkSavesFile();
												player.sendMessage(StringConstants.MESSAGE_PREFIX_OK
														+ " You just claimed " + ChatColor.DARK_AQUA + playerChunkX
														+ " " + playerChunkZ);
												player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " " + claimCost
														+ " has been withdrawn from your country's treasury!");
												return true;
											} else {
												player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
														+ " Your country does not have enough money!");
												return true;
											}

										} else {
											player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
													+ " You are not allowed to do this!");
											return true;
										}
									} else {
										plugin.chunkSavesFile.createSection("ClaimedChunks");
										player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
										return true;

									}
								} else {
									player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
											+ " Your faction already owns this land!");
									return true;
								}
							} else {
								plugin.getChunkSavesFile().getConfigurationSection(getPlayerFaction(player.getName()))
										.createSection("Chunks");
								plugin.saveChunkSavesFile();
								player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
								return true;

							}
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + " You are not a citizen of any country!");
						return false;
					}
					//////////////
				} else if (extraArguments[0].equalsIgnoreCase("stats")||extraArguments[0].equalsIgnoreCase("nationstats")) {
					
					return showNationStats(player, sender, extraArguments);
					
				} else if (extraArguments[0].equalsIgnoreCase("unclaim")) {
					
					int playerChunkX = player.getLocation().getChunk().getX();
					
					int playerChunkZ = player.getLocation().getChunk().getZ();
					
					if (playerIsInAFaction(player.getName()) == true && getPlayerFaction(player.getName())
							.equals(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
									.get(playerChunkX + "," + playerChunkZ)) == true) {
						if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
								.contains("Chunks") == true) {
							if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
									.getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == true) {
								if (plugin.chunkSavesFile.contains("ClaimedChunks")) {
									List<String> chunkList = plugin.chunkSavesFile
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getStringList("Chunks");
									List<String> officerList = plugin.getSecondConfig()
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getStringList("Officers");
									plugin.saveSecondConfig();
									plugin.saveChunkSavesFile();
									Boolean playerIsOfficer = officerList.contains(player.getName());
									Boolean playerIsLeader = plugin.getSecondConfig()
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getString("Owner").equals(sender.getName());
									if (playerIsOfficer == true || playerIsLeader == true) {
										chunkList.remove(playerChunkX + "," + playerChunkZ);
										plugin.chunkSavesFile
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.set("Chunks", chunkList);
										plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
												.set(playerChunkX + "," + playerChunkZ, null);
										plugin.saveChunkSavesFile();
										player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You just unclaimed "
												+ ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
										return true;

									} else {
										player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
												+ " You need to be an/a official/leader to do this!");
										return true;
									}
								} else {
									plugin.chunkSavesFile.createSection("ClaimedChunks");
									player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
									return true;
								}
							} else {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
										+ " Your country does not own this land!");
								return true;
							}
						} else {
							player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_MISTAKE + " Your country does not own this land!");
						return true;
					}

					// command /gda map
				} else if (extraArguments[0].equalsIgnoreCase("map")) {
					return showChunkMap(sender, player);
				} else if (extraArguments[0].equalsIgnoreCase("create")) {
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						String Name = extraArguments[1];
						if (plugin.getSecondConfig().contains(Name)) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + " There is a country called "
									+ Name + " already!");
							return true;
						} else {
							if (plugin.getSecondConfig().contains("Citizens") == false) {
								plugin.getSecondConfig().createSection("Citizens");
							} else if (plugin.getSecondConfig().getConfigurationSection("Citizens")
									.contains(player.getName())) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
										+ " You are already a citizen of a country!");
								return true;
							}
							economy = plugin.getEconomy();
							plugin.getSecondConfig().createSection(Name);
							plugin.getSecondConfig().getConfigurationSection(Name).set("Owner", player.getName());
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), Name);
							plugin.getChunkSavesFile().createSection(Name).createSection("Chunks");
							List<String> factionList = plugin.getSecondConfig().getStringList("FactionList");
							factionList.add(Name);
							plugin.getSecondConfig().set("FactionList", factionList);
							ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile,
									plugin.chunkSavesFileConfiguration);
							plugin.saveSecondConfig();
							EconomyResponse ec = economy.createBank(Name, player.getName());
							if (ec.transactionSuccess()) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_OK
										+ "You established a new country called " + ChatColor.GOLD + Name);
								return true;
							} else {
								player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
								return true;
							}
						}

					}

				} else if (extraArguments[0].equalsIgnoreCase("invite")) {
					Player invitedPlayer = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String invitedPlayerName = invitedPlayer.getName();
					// String playerFaction =
					// plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						if (playerHasBeenInvited(player.getName(), invitedPlayerName) == true) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + " That player has already been invited!");
							return true;
						} else if (playerIsInAFaction(invitedPlayerName) == true) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
									+ " That player is already a citizen of a country!");
							return true;
						} else if (playerIsInAFaction(invitedPlayerName) == false) {
							inviteStore.put(player.getName(), invitedPlayerName);
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have invited "
									+ invitedPlayerName + " to " + getPlayerFaction(player.getName()));
							invitedPlayer.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have been invited to "
									+ getPlayerFaction(player.getName()) + "!");
							return true;
						}
					}
				} else if (extraArguments[0].equalsIgnoreCase("join")) {
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						Player playerThatInvites = (Bukkit.getServer().getPlayer(extraArguments[1]));
						if (playerHasBeenInvited(playerThatInvites.getName(), player.getName()) == false) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
									+ "You have not been invited to any countries!");
							return true;
						} else if (playerIsInAFaction(player.getName()) == true) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + " You are already in a country!");
							return true;
						} else if (playerHasBeenInvited(playerThatInvites.getName(), player.getName()) == true) {
							String factionPlayerHasBeenInvitedTo = plugin.getSecondConfig()
									.getConfigurationSection("Citizens").getString(playerThatInvites.getName());
							List<String> memberList = plugin.getSecondConfig()
									.getConfigurationSection(factionPlayerHasBeenInvitedTo).getStringList("Members");
							plugin.saveSecondConfig();
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(),
									factionPlayerHasBeenInvitedTo);
							memberList.add(player.getName());
							plugin.getSecondConfig().getConfigurationSection(factionPlayerHasBeenInvitedTo)
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have joined "
									+ factionPlayerHasBeenInvitedTo);
							inviteStore.remove(playerThatInvites.getName());
							return true;

						} else {
							return false;
						}

					}
				} else if (extraArguments[0].equalsIgnoreCase("cl")||extraArguments[0].equalsIgnoreCase("chunklist")) {
					String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
							.getString(sender.getName());
					List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction)
							.getStringList("Chunks");
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
					for (String s : chunkList)
						player.sendMessage(s);
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("cc")
						||extraArguments[0].equalsIgnoreCase("this")
						||extraArguments[0].equalsIgnoreCase("checkchunk")) {
					int playerChunkX = player.getLocation().getChunk().getX();
					int playerChunkZ = player.getLocation().getChunk().getZ();
					player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You are standing on chunk " + playerChunkX
							+ " " + playerChunkZ);
					player.sendMessage(StringConstants.MESSAGE_PREFIX_INFO +
							"Is chunk claimed:"+ detectWhetherChunkIsClaimed(playerChunkX, playerChunkZ));
					
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("help")||extraArguments[0].equalsIgnoreCase("help1")) {
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GOLDE);
					player.sendMessage(ChatColor.GOLD + "cc: Short for check chunk. Usage: /gda cc");
					player.sendMessage(ChatColor.GOLD + "cl: Short for claim list. Usage: /gda cl");
					player.sendMessage(
							ChatColor.GOLD + "invite: Invites a player to your country. Usage: /gda invite (player)");
					player.sendMessage(
							ChatColor.GOLD + "create: Establishes a new country. Usage: /gda create (countryName)");
					player.sendMessage(ChatColor.GOLD
							+ "claim: Claims a chunk for your country. It costs 1000$ for one chunk. Usage: /gda claim");
					player.sendMessage(ChatColor.GOLD
							+ "leave: Use this command to leave your country. If you're the owner, the country is deleted. Usage: /gda leave");
					player.sendMessage(ChatColor.GOLD + "help2: Shows the next page. Usage: /gda help2");
					player.sendMessage(ChatColor.GOLD + "join: Accepts an invite. Usage: /gda join (player)");
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("leave")) {
					if (playerIsInAFaction(player.getName()) == true) {
						String playerName = player.getName();
						String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
								.getString(playerName);
						if (plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
								.getString("Owner").equals(playerName)) {
							List<String> memberList = plugin.getSecondConfig()
									.getConfigurationSection(getPlayerFaction(player.getName()))
									.getStringList("Members");
							plugin.saveSecondConfig();
							List<String> chunkList = plugin.getChunkSavesFile()
									.getConfigurationSection(getPlayerFaction(player.getName()))
									.getStringList("Chunks");
							for (int i = 0; i < chunkList.size(); i++) {
								if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
										.getString(chunkList.get(i)).contains(getPlayerFaction(player.getName()))) {
									plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
											.set(chunkList.get(i), null);
									plugin.saveChunkSavesFile();
									plugin.saveSecondConfig();
								} else {
									player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
								}
							}
							economy = plugin.getEconomy();
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO
									+ getPlayerFaction(player.getName()) + " has been disbanded!");
							List<String> factionList = plugin.getSecondConfig().getStringList("FactionList");
							factionList.remove(playerFaction);
							plugin.getSecondConfig().set("FactionList", factionList);
							plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Officers", null);
							plugin.getChunkSavesFile().set(playerFaction, null);
							plugin.getSecondConfig().set(playerFaction, null);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
							plugin.saveSecondConfig();
							plugin.saveChunkSavesFile();
							for (int i = 0; i < memberList.size(); i++) {
								plugin.getSecondConfig().getConfigurationSection("Citizens").set(memberList.get(i),
										null);
								plugin.saveSecondConfig();
							}
							economy.deleteBank(playerFaction);
							plugin.saveSecondConfig();
							plugin.saveChunkSavesFile();
							return true;
						} else {
							List<String> memberList = plugin.getSecondConfig()
									.getConfigurationSection(getPlayerFaction(player.getName()))
									.getStringList("Members");
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You left "
									+ getPlayerFaction(player.getName()));
							memberList.remove(playerName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
							if (plugin.getSecondConfig().getConfigurationSection(playerFaction)
									.getStringList("Officers").contains(playerName)) {
								plugin.getSecondConfig().getConfigurationSection(playerFaction)
										.getStringList("Officers").remove(playerName);
							}
							plugin.saveSecondConfig();
							return true;
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + "You are not a citizen of any country!");
						return true;
					}
				}//changelog
				else if(extraArguments[0].equalsIgnoreCase("changelog")){
					// give the sender a physical book that contains a changelog
					return getChangeLogBook(sender);
				}
				
				else if (extraArguments[0].equalsIgnoreCase("help2")) {
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GOLDE);
					player.sendMessage(ChatColor.GREEN
							+ "Use /gda overclaim to overtake enemy territory. You must look at an enemy chunk...");

					player.sendMessage(ChatColor.GREEN + "Use /gda denyvisa (player) to cancel a player's visa");
					player.sendMessage(ChatColor.GREEN + "Use /gda setvisa (player) to set a player's visa");
					player.sendMessage(ChatColor.GREEN + "Use /gda stats (nation) to see a nation's stats");
					player.sendMessage(ChatColor.GREEN + "Use /gda visastats (nation) to see a nation's visa holders.");
					player.sendMessage(ChatColor.GREEN + "Use /gda kick (member-name) to remove a member from the faction.");
					player.sendMessage("");
					
					
					
					player.sendMessage(StringConstants.MESSAGE_GENERIC_LINE_GREEN);
					return true;
				} else if(extraArguments[0].equalsIgnoreCase("visastats")||extraArguments[0].equalsIgnoreCase("vstats")){
					
					return showVisaCarriersForFaction(sender, player, extraArguments);
					
				} else if (extraArguments[0].equalsIgnoreCase("denyvisa")) {
					deactivatePlayerVisa(player, extraArguments, sender);
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("setvisa")) {
					return activatePlayerVisa(sender, extraArguments, player, getPlayerFaction(player.getName()));
				} else if (extraArguments[0].equalsIgnoreCase("overclaim")) {
					// TODO: finish overclaim command
					int overclaimTimeInSeconds = plugin.getConfig().getInt("OverclaimTime");
					final String playerName = player.getName();
					final int playerChunkX = player.getLocation().getChunk().getX();
					final int playerChunkZ = player.getLocation().getChunk().getZ();
					String chunkFaction = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
							.getString(playerChunkX + "," + playerChunkZ);
					if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
							.contains(playerChunkX + "," + playerChunkZ)) {
						String chunkFactionName = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
								.getString(playerChunkX + "," + playerChunkZ);
						if (getPlayerFaction(playerName) == null) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + "You are not a citizen of any country!");
							return true;
						} else if (getPlayerFaction(playerName).equals(chunkFactionName)) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_MISTAKE + " You can't overclaim your own land!");
							return true;
						} else {
							if (atleastOneIsOnline(listCitizens(chunkFaction))) {
								Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + " " + playerName
										+ " is going to overclaim " + playerChunkX + "  " + playerChunkZ + " " + "from "
										+ chunkFaction + "  " + "in " + overclaimTimeInSeconds + " seconds!");
								taskIDs.put(playerName, Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
									@Override
									public void run() {
										// unclaim kebab
										List<String> chunkList = plugin.getChunkSavesFile()
												.getConfigurationSection(chunkFaction).getStringList("Chunks");
										chunkList.remove(playerChunkX + "," + playerChunkZ);
										plugin.getChunkSavesFile().getConfigurationSection(chunkFaction).set("Chunks",
												chunkList);
										plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
												.set(playerChunkX + "," + playerChunkZ, null);
										plugin.saveChunkSavesFile();
										Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + "  " + playerName
												+ " overclaimed " + playerChunkX + "  " + playerChunkZ);
									}
								}, (20 * overclaimTimeInSeconds)));
							} else {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
										+ " There has to be atleast one faction member online!");
							}
						}
					}
				} else if (extraArguments[0].equalsIgnoreCase("official")) {
					String playerName = player.getName();
					Player playerToBeGrantedOfficerStatus = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String playerToBeGrantedOfficerStatusName = playerToBeGrantedOfficerStatus.getName();
					Boolean playerIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
							.equals(sender.getName());
					Boolean playersAreInSameFaction = getPlayerFaction(player.getName())
							.equals(getPlayerFaction(playerToBeGrantedOfficerStatusName));
					List<String> memberList = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Members");
					plugin.saveSecondConfig();
					if (playerIsLeader == true && playersAreInSameFaction == true) {
						List<String> officerList = plugin.getSecondConfig()
								.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Officers");
						plugin.saveSecondConfig();
						Boolean playerIsAlreadyOfficer = officerList.contains(playerToBeGrantedOfficerStatusName);
						if (playerIsAlreadyOfficer == true && playersAreInSameFaction == true) {
							officerList.remove(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							memberList.add(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You have unset "
									+ playerToBeGrantedOfficerStatusName + " as officer!");
							playerToBeGrantedOfficerStatus.sendMessage(StringConstants.MESSAGE_PREFIX_OK
									+ "You are no longer an officer of " + getPlayerFaction(player.getName()));
							return true;
						} else {
							officerList.add(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							memberList.remove(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You have set "
									+ playerToBeGrantedOfficerStatusName + " as officer!");
							playerToBeGrantedOfficerStatus.sendMessage(StringConstants.MESSAGE_PREFIX_OK
									+ "You are now an officer of " + getPlayerFaction(player.getName()));
							return true;
						}
					} else {
						if (playerIsLeader == false) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + "You need to be the leader to do this!");
							return true;
						}
						if (playersAreInSameFaction == false) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + "You are not in the same faction!");
							return true;
						}
					}

				} else if (extraArguments[0].equalsIgnoreCase("kick")) {
					String playerName = player.getName();
					Player playerBeingKicked = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String playerBeingKickedName = playerBeingKicked.getName();
					List<String> officerList = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Officers");
					plugin.saveSecondConfig();
					Boolean playerIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
							.equals(sender.getName());
					Boolean playersAreInSameFaction = getPlayerFaction(player.getName())
							.equals(getPlayerFaction(playerBeingKickedName));
					Boolean playerIsOfficer = officerList.contains(player.getName());
					Boolean playerBeingKickedIsOfficer = officerList.contains(playerBeingKickedName);
					Boolean playerBeingKickedIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(playerBeingKickedName)).getString("Owner")
							.equals(playerBeingKickedName);
					if (playerIsLeader == true || playerIsOfficer == true) {
						if (playerBeingKickedIsOfficer == true) {
							if (playerBeingKickedIsLeader == true) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
								return true;
							}
							officerList.remove(playerBeingKickedName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName,
									null);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName
									+ " from the faction!");
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + playerName + " kicked "
									+ playerBeingKickedName + " from " + getPlayerFaction(player.getName())
									+ "! How evil!");
							return true;
						} else {
							if (playerBeingKickedIsLeader == true) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
								return true;
							}
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName,
									null);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName
									+ " from the faction!");
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + playerName + " kicked "
									+ playerBeingKickedName + " from " + getPlayerFaction(player.getName())
									+ "! How evil!");
							return true;
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + "You need to be officer/leader to do this!");
					}
				} else if (extraArguments[0].equalsIgnoreCase("firstRun")) {
					// TODO: add chunk unclaimage
					plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {

						@Override
						public void run() {
							economy = plugin.getEconomy();
							List<String> factionList = plugin.getSecondConfig().getStringList("FactionList");
							for (String i : factionList) {
								int generalTax = plugin.getSecondConfig().getConfigurationSection(i)
										.getInt("GeneralTax");
								for (String j : listCitizens(i)) {
									if (playerIsLeader(i, j) == true) {
										// nothing
										plugin.getLogger().info("did not take tax from leader");
										;
										continue;
									}
									EconomyResponse r = economy.withdrawPlayer(j, generalTax);
									if (r.transactionSuccess() == true) {
										EconomyResponse ec = economy.bankDeposit(getPlayerFaction(j), generalTax);
										if (ec.transactionSuccess() == true) {
											plugin.getLogger().info("did take away tax from " + j);
										} else {
										}
									} else {
										List<String> memberList = plugin.getSecondConfig()
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getStringList("Members");
										memberList.remove(j);
										plugin.getSecondConfig().getConfigurationSection(i).set("Members", memberList);
										plugin.getSecondConfig().getConfigurationSection("Citizens").set(j, null);
										plugin.getLogger().info("removed " + j + " from " + i);
										plugin.saveSecondConfig();
									}

								}
								List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(i)
										.getStringList("Chunks");
								double claimUpkeepCost = plugin.getConfig().getDouble("ClaimUpkeep") * chunkList.size();
								EconomyResponse ec = economy.bankWithdraw(i, claimUpkeepCost);
								if (ec.transactionSuccess()) {
									plugin.getLogger().info("did take away " + claimUpkeepCost + " from " + i);
								} else {
									Random rand = new Random();
									final int randomIndex = rand.nextInt(chunkList.size());
									plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
											.set(chunkList.get(randomIndex), null);
									plugin.getLogger()
											.info("did take away " + chunkList.get(randomIndex) + " from " + i);
									chunkList.remove(randomIndex);
									plugin.chunkSavesFile.getConfigurationSection(i).set("Chunks", chunkList);
									plugin.saveChunkSavesFile();
								}

							}
							// this code will be executed in the main thread
							// each time the
							// task runs
						}
					}, (20 * 10L), 20 * 10); // 10 sec delay, 10800 (or 3 hours)
												// secs cycle
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("nations")||extraArguments[0].equalsIgnoreCase("list")) {
					List<String> factionList = plugin.getSecondConfig().getStringList("FactionList");
					if (factionList.size() < 20) {
						player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " Nation list");
						for (String f : factionList) {
							player.sendMessage(ChatColor.GREEN + " " + f);
						}
						return true;
					}
					if (extraArguments[1].equalsIgnoreCase("2") && factionList.size() > 20) {
						for (int i = 20; i < factionList.size(); i++) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " Nation List 2");
							player.sendMessage(ChatColor.GREEN + " " + i);

						}

					} else if (factionList.size() > 20) {
						player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + " Use /gda nations 2 for next page");
						return true;
					}

				} else if (extraArguments[0].equalsIgnoreCase("capital")) {
					if (playerIsInAFaction(player.getName()) == false) {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + "You are not a citizen of any country!");
						return true;
					} else if (extraArguments[1].equalsIgnoreCase("tp")) {
						player.sendMessage(StringConstants.MESSAGE_PREFIX_INFO + " You will teleport in 10 seconds...");
						TPtaskIDs.put(player.getName(), Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

							@Override
							public void run() {
								String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
										.getString(player.getName());
								World w = Bukkit.getServer().getWorld(plugin.getChunkSavesFile()
										.getConfigurationSection(playerFaction).getString("capitalLocation.World"));
								double x = plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.getDouble("capitalLocation.X");
								double y = plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.getDouble("capitalLocation.Y");
								double z = plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.getDouble("capitalLocation.Z");
								player.teleport(new Location(w, x, y, z));
								player.sendMessage(
										StringConstants.MESSAGE_PREFIX_INFO + " You teleported to your capital!");
								CommandExec.TPtaskIDs.remove(player.getName());
							}

						}, 20 * 10));
						return true;
					} else if (extraArguments[1].equalsIgnoreCase("set")) {
						int playerChunkX = player.getLocation().getChunk().getX();
						int playerChunkZ = player.getLocation().getChunk().getZ();
						Location playerLoc = player.getLocation();
						String claimedChunkFactionName = plugin.getChunkSavesFile()
								.getConfigurationSection("ClaimedChunks").getString(playerChunkX + "," + playerChunkZ);
						String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
								.getString(player.getName());
						Boolean playerIsLeader = plugin.getSecondConfig()
								.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
								.equals(sender.getName());
						Location capitalLoc = player.getLocation();
						if (playerIsLeader == true) {
							boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
							if (chunkPertainsToPlayersFaction == true) {
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction).set("Capital",
										playerChunkX + "," + playerChunkZ);
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.World", capitalLoc.getWorld().getName());
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.X", capitalLoc.getX());
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.Y", capitalLoc.getY());
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.Z", capitalLoc.getZ());
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.Yaw", capitalLoc.getYaw());
								plugin.getChunkSavesFile().getConfigurationSection(playerFaction)
										.set("capitalLocation.Pitch", capitalLoc.getPitch());
								plugin.saveChunkSavesFile();
								player.sendMessage(StringConstants.MESSAGE_PREFIX_INFO + " You set your capital chunk");
								return true;

							} else {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
										+ " Your nation does not own this chunk!");
								return true;
							}
						} else {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_MISTAKE + " You have to be the leader to do this!");
							return true;
						}
					}
				}

				// TODO: Add first run parameters
				else if (extraArguments[0].equalsIgnoreCase("treasury")) {

					Boolean playerIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
							.equals(sender.getName());
					if (extraArguments[1].equalsIgnoreCase("balance")) {
						economy = plugin.getEconomy();
						double bankBalance = economy.bankBalance(getPlayerFaction(player.getName())).balance;
						String bankBalanceToString = String.valueOf(bankBalance);
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_OK + " Your faction bank has " + bankBalanceToString);
						return true;
					} else if ((extraArguments[1].equalsIgnoreCase("deposit") &&

							listOfficialNames(getPlayerFaction(player.getName()), player).contains(player.getName()))
							|| playerIsLeader == true && extraArguments[1].equalsIgnoreCase("deposit")) {
						economy = plugin.getEconomy();
						String amount = extraArguments[2];
						double amountDouble = Double.valueOf(amount);
						EconomyResponse ec = economy.bankDeposit(getPlayerFaction(player.getName()), amountDouble);
						if (ec.transactionSuccess()) {
							EconomyResponse r = economy.withdrawPlayer(player.getName(), amountDouble);
							if (r.transactionSuccess()) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have deposited " + amount
										+ " into your country's bank ");
								return true;
							} else {
								player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
							}
						} else {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + " You do not have " + amount);
							return true;
						}
					} else if ((extraArguments[1].equalsIgnoreCase("take")
							&& listOfficialNames(getPlayerFaction(player.getName()), player).contains(player.getName()))
							|| playerIsLeader == true && extraArguments[1].equalsIgnoreCase("take")) {
						economy = plugin.getEconomy();
						String amount = extraArguments[2];
						EconomyResponse ec = economy.bankWithdraw(getPlayerFaction(player.getName()),
								Double.parseDouble(amount));
						if (ec.transactionSuccess()) {
							economy.depositPlayer(player.getName(), Double.parseDouble(amount));
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have taken " + amount
									+ " from your country into your account ");
							return true;
						} else {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_MISTAKE + " Your country does not have " + amount);
							return true;
						}
					} else if ((extraArguments[1].equalsIgnoreCase("setTaxes") && playerIsLeader == true)) {
						String generalTax = extraArguments[2];
						int generalTaxInt = Integer.valueOf(generalTax);
						plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
								.set("GeneralTax", generalTaxInt);
						plugin.saveSecondConfig();
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_OK + " You have set a tax amount of " + generalTaxInt);
						return true;

					}

				} else if (extraArguments[0].equalsIgnoreCase("debugBanks"))

				{
					List<String> bankList = economy.getBanks();
					for (int i = 0; i < bankList.size(); i++) {
						sender.sendMessage(StringConstants.MESSAGE_PREFIX_INFO + i);
					}
					return true;
					
				}
				else if (extraArguments[0].equalsIgnoreCase("sub")||extraArguments[0].equalsIgnoreCase("createsub"))  {
					 
					/**
					 * createSubfactionMeme 
					 */
					return createSubfaction(extraArguments[1].toString(), getPlayerFaction(player.getName()), sender );
				}
				else if (extraArguments[0].equalsIgnoreCase("ally")) {
					
					return setRelation(extraArguments[0], getPlayerFaction(player.getName()), extraArguments[1]);
					
				}
			}
		} else {
			sender.sendMessage("That's invalid you inbred fuck!");
		}
		return false;
	}

}
