

package zetta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class CommandExec implements CommandExecutor {
	//Variable declaration field
	Main plugin;
    ChunkManagement chunkManagement;
//    String plugin.MESSAGE_PREFIX_MISTAKE = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED;
//	String plugin.MESSAGE_PREFIX_ERROR = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.DARK_RED;
//	String plugin.MESSAGE_PREFIX_OK = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.GREEN;
//	String plugin.MESSAGE_PREFIX_INFO = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.BLUE;
//	String plugin.MESSAGE_GENERIC_ERROR = plugin.MESSAGE_PREFIX_ERROR + "Something went wrong :( I'll try to fix it, try it again maybe?";
    
    
    
	HashMap<String, String> inviteStore = new HashMap<String, String>();
	

	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}

    //Actual command, this has to be registered in the main class
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		Player player = (Player) sender;
		UUID ID = player.getUniqueId();		
        ChunkManagement.playerName = sender.getName();
		if(command.getName().equalsIgnoreCase("test")) {
			sender.sendMessage(ChatColor.AQUA + "Yeah, it's working, what a miracle. Now, get your head out of your ass");
			return true;
		}
		else if(command.getName().equalsIgnoreCase("gda")){
		  if (extraArguments.length == 0) { 
				sender.sendMessage("Usage: /gda (subcommand)");
		  }
		  else {
			  if(extraArguments[0].equalsIgnoreCase("claim")) {
				  int playerChunkX = player.getLocation().getChunk().getX();
				  int playerChunkZ = player.getLocation().getChunk().getZ();
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
				  if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(sender.getName())) {
					  //TODO: Add faction check for player
					  if(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks") == null) {
						  plugin.getChunkSavesFile().createSection("ClaimedChunks");
						  plugin.saveChunkSavesFile();
						  player.sendMessage(plugin.MESSAGE_GENERIC_ERROR);
						  return true;
					  }
					  if(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").contains(playerChunkX+","+playerChunkZ)){						  
					      player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "This land is already claimed by " + ChatColor.GOLD + plugin.chunkSavesFile.getConfigurationSection("ClaimedChunks").getString(playerChunkX+","+playerChunkZ));
					      return true;
					  }
					  else {
					  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).contains("Chunks") == true) {
						  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == false){
							  if(plugin.chunkSavesFile.contains("ClaimedChunks")) {
								  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
								  List<String> officerList = plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Officers");
								  plugin.saveSecondConfig();
								  Boolean playerIsOfficer = officerList.contains(player.getName());
								  Boolean playerIsLeader = plugin.getSecondConfig().getConfigurationSection(playerFaction).getString("Owner").equals(sender.getName());
								  if(playerIsOfficer == true || playerIsLeader == true) {
									  chunkList.add(playerChunkX + "," + playerChunkZ);
									  plugin.chunkSavesFile.getConfigurationSection(playerFaction).set("Chunks", chunkList);
									  plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").set(playerChunkX+","+playerChunkZ, playerFaction);
									  plugin.saveChunkSavesFile();
									  player.sendMessage(plugin.MESSAGE_PREFIX_OK + " You just claimed " + ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
									  return true;  
								  }
								  else {
									  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You are not allowed to do this!");
									  return true;
								  }
							  }
							  else {
								  plugin.chunkSavesFile.createSection("ClaimedChunks");
								  player.sendMessage(plugin.MESSAGE_GENERIC_ERROR);
								  return true;
								  
							  }
						  }
						  else {
							  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "Your faction already owns this land!");
							  return false;
					  }
						  }
					  else {
						 plugin.getChunkSavesFile().getConfigurationSection(playerFaction).createSection("Chunks");
						 plugin.saveChunkSavesFile();
						 player.sendMessage(plugin.MESSAGE_GENERIC_ERROR);
						 return true;
					  
					  }
				  }
				  }
				  else {
					  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You are not a citizen of any country!");
					  return false;
				  }
				  //////////////
			  }
			  else if (extraArguments[0].equalsIgnoreCase("create")){
				  if(extraArguments.length == 0) {
					  sender.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					 String Name = extraArguments[1];
					 if(plugin.getSecondConfig().contains(Name)) {
						 player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "There is a country called " + Name + " already!");
						 return false;
					 }
					 else {
						 if(plugin.getSecondConfig().contains("Citizens") == false) {
							 plugin.getSecondConfig().createSection("Citizens");
						 }
						 else if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
							player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You are already in a faction!");
							return false;
						 }
						 plugin.getSecondConfig().createSection(Name);
						 plugin.getSecondConfig().getConfigurationSection(Name).set("Owner", player.getName());
						 plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), Name);
						 plugin.getChunkSavesFile().createSection(Name).createSection("Chunks");
						 ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
						 plugin.saveSecondConfig();
						 player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You established a new country called " + ChatColor.GOLD + Name);
						 return true; 
					 }
				
				  }
				  
			  }
			  else if (extraArguments[0].equalsIgnoreCase("invite")){
				Player invitedPlayer = (Bukkit.getServer().getPlayer(extraArguments[1]));
				String invitedPlayerName = invitedPlayer.getName();
				String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
				  if(extraArguments.length == 0) {
					  sender.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					  inviteStore.put(player.getName(), invitedPlayerName);
					  invitedPlayer.sendMessage(plugin.MESSAGE_PREFIX_OK + "You have been invited to " + playerFaction + "!");
					  return true;
				  }
			  }
			  else if (extraArguments[0].equalsIgnoreCase("join")) {
				  if(extraArguments.length == 0) {
					  sender.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					  Player playerThatInvites = (Bukkit.getServer().getPlayer(extraArguments[1]));
					  if(inviteStore.get(playerThatInvites.getName()).equals(player.getName())) {
						  String factionPlayerHasBeenInvitedTo = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerThatInvites.getName());
						  List<String> memberList = plugin.getSecondConfig().getConfigurationSection(factionPlayerHasBeenInvitedTo).getStringList("Members");
						  plugin.saveSecondConfig();
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), factionPlayerHasBeenInvitedTo);
						  memberList.add(player.getName());
						  plugin.getSecondConfig().getConfigurationSection(factionPlayerHasBeenInvitedTo).set("Members", memberList);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You have joined " + factionPlayerHasBeenInvitedTo);
						  inviteStore.remove(playerThatInvites.getName());
						  return true;

					  }
					  else {
						  return false;
					  }
						  
				  }
			  }
			  else if (extraArguments[0].equalsIgnoreCase("cl")) {
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
				  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
				  player.sendMessage(ChatColor.GREEN + "=======================================");
				  for (String s : chunkList ) player.sendMessage(s);
				  player.sendMessage(ChatColor.GREEN + "=======================================");
				  return true;
			  }
			  else if(extraArguments[0].equalsIgnoreCase("cc")) {
				  int playerChunkX = player.getLocation().getChunk().getX();
				  int playerChunkZ = player.getLocation().getChunk().getZ();
				  player.sendMessage(plugin.MESSAGE_PREFIX_OK + " You are standing on chunk " + playerChunkX + " " + playerChunkZ);
				  return true;
			  }
			  else if(extraArguments[0].equalsIgnoreCase("help")) {
				  player.sendMessage(ChatColor.GREEN + "=====================================================");
				  player.sendMessage(ChatColor.GOLD + "cc: Short for check chunk. Usage: /gda cc");
				  player.sendMessage(ChatColor.GOLD + "cl: Short for claim list. Usage: /gda cl");
				  player.sendMessage(ChatColor.GOLD + "invite: Invites a player to your country. Usage: /gda invite (player)");
				  player.sendMessage(ChatColor.GOLD + "create: Establishes a new country. Usage: /gda create (countryName)");
				  player.sendMessage(ChatColor.GOLD + "claim: Claims a chunk for your country. It costs 1000$ for one chunk. Usage: /gda claim");
				  player.sendMessage(ChatColor.GOLD + "leave: Use this command to leave your country. If you're the owner, the country is deleted. Usage: /gda leave");
				  player.sendMessage(ChatColor.GOLD + "help2: Shows the next page. Usage: /gda help2");
				  player.sendMessage(ChatColor.GOLD + "join: Accepts an invite. Usage: /gda join (player)");
				  player.sendMessage(ChatColor.GREEN + "=====================================================");
				  return true;
			  }
			  else if(extraArguments[0].equalsIgnoreCase("leave")) {
				  if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
					  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
					  String playerName = player.getName();
					  List<String> memberList = plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Members");
					  plugin.saveSecondConfig();
					  List<String> chunkList = plugin.getChunkSavesFile().getConfigurationSection(playerFaction).getStringList("Chunks");
					  if(plugin.getSecondConfig().getConfigurationSection(playerFaction).getString("Owner").equals(playerName)) 
					  {
						  for (int i = 0; i < chunkList.size(); i++) {
								if(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").getString(chunkList.get(i)).contains(playerFaction)) {
								    plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").set(chunkList.get(i), null);
								    plugin.getSecondConfig().set(playerFaction, null);
								    plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
								    plugin.getChunkSavesFile().set(playerFaction, null);
								    plugin.saveChunkSavesFile();
								    plugin.saveSecondConfig();
								    Bukkit.broadcastMessage(plugin.MESSAGE_PREFIX_INFO + playerFaction + " has been disbanded!");
								    return true;
								}
							    else {
									player.sendMessage(plugin.MESSAGE_GENERIC_ERROR);
								}
						   }

//						  plugin.saveSecondConfig();
//						  plugin.saveChunkSavesFile();
					  }
					  else {
						  memberList.remove(playerName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Members", memberList);
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You left " + playerFaction);
						  return true;
					  }
				  }
				  else {
					  player.sendMessage(plugin.MESSAGE_GENERIC_ERROR); 
				  }
			  }
			  else if(extraArguments[0].equalsIgnoreCase("help2")) {
				  //TODO: Add help2 command
			  }
			  else if(extraArguments[0].equalsIgnoreCase("officer")) {
				  String playerName = player.getName();
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
				  Player playerToBeGrantedOfficerStatus = (Bukkit.getServer().getPlayer(extraArguments[1]));
				  String playerToBeGrantedOfficerStatusName = playerToBeGrantedOfficerStatus.getName();
				  String playerToBeGrantedOfficerStatusFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerToBeGrantedOfficerStatusName);
				  Boolean playerIsLeader = plugin.getSecondConfig().getConfigurationSection(playerFaction).getString("Owner").equals(sender.getName());
				  Boolean playersAreInSameFaction = playerFaction.equals(playerToBeGrantedOfficerStatusFaction);
				  List<String> memberList = plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Members");
				  plugin.saveSecondConfig();
				  if(playerIsLeader == true && playersAreInSameFaction == true) {
					  List<String> officerList = plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Officers");
					  plugin.saveSecondConfig();
					  Boolean playerIsAlreadyOfficer = officerList.contains(playerToBeGrantedOfficerStatusName);
					  if(playerIsAlreadyOfficer == true && playersAreInSameFaction == true) {
						  officerList.remove(playerToBeGrantedOfficerStatusName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Officers", officerList);
						  memberList.add(playerToBeGrantedOfficerStatusName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Members", memberList);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You have unset " + playerToBeGrantedOfficerStatusName + " as officer!" );
						  playerToBeGrantedOfficerStatus.sendMessage(plugin.MESSAGE_PREFIX_OK + "You are no longer an officer of " + playerFaction);
						  return true;
					  }
					  else {
						  officerList.add(playerToBeGrantedOfficerStatusName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Officers", officerList);
						  memberList.remove(playerToBeGrantedOfficerStatusName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Members", memberList);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You have set " + playerToBeGrantedOfficerStatusName + " as officer!" );
						  playerToBeGrantedOfficerStatus.sendMessage(plugin.MESSAGE_PREFIX_OK + "You are now an officer of " + playerFaction);
						  return true;
					  }
				  }
				  else {
					  if(playerIsLeader == false) {
						  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You need to be the leader to do this!");
						  return true;
					  }
					  if(playersAreInSameFaction == false) {
						  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You are not in the same faction!");
						  return true;
					  }
				  }
			
				  
			  }
			  else if(extraArguments[0].equalsIgnoreCase("kick")) {
				  String playerName = player.getName();
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
				  Player playerBeingKicked = (Bukkit.getServer().getPlayer(extraArguments[1]));
				  String playerBeingKickedName = playerBeingKicked.getName();
				  String playerBeingKickedFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerBeingKickedName);
				  List<String> officerList = plugin.getSecondConfig().getConfigurationSection(playerFaction).getStringList("Officers");
				  plugin.saveSecondConfig();
				  Boolean playerIsLeader = plugin.getSecondConfig().getConfigurationSection(playerFaction).getString("Owner").equals(sender.getName());
				  Boolean playersAreInSameFaction = playerFaction.equals(playerBeingKickedFaction);
				  Boolean playerIsOfficer = officerList.contains(player.getName());
				  Boolean playerBeingKickedIsOfficer = officerList.contains(playerBeingKickedName);
				  Boolean playerBeingKickedIsLeader = plugin.getSecondConfig().getConfigurationSection(playerBeingKickedFaction).getString("Owner").equals(playerBeingKickedName);
				  if (playerIsLeader == true || playerIsOfficer == true) {
					  if(playerBeingKickedIsOfficer == true) {
						  if(playerBeingKickedIsLeader == true) {
							  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
							  return true;
						  }
						  officerList.remove(playerBeingKickedName);
						  plugin.getSecondConfig().getConfigurationSection(playerFaction).set("Officers", officerList);
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName, null);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName + " from the faction!");
						  Bukkit.broadcastMessage(plugin.MESSAGE_PREFIX_ERROR + playerName + " kicked " + playerBeingKickedName + " from " + playerFaction + "! How evil!");
						  return true;
					  }
					  else {
						  if(playerBeingKickedIsLeader == true) {
							  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
							  return true;
						  }
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName, null);
						  plugin.saveSecondConfig();
						  player.sendMessage(plugin.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName + " from the faction!");
						  Bukkit.broadcastMessage(plugin.MESSAGE_PREFIX_ERROR + playerName + " kicked " + playerBeingKickedName + " from " + playerFaction + "! How evil!");
						  return true;  
					  }
				  }
				  else {
					  player.sendMessage(plugin.MESSAGE_PREFIX_ERROR + "You need to be officer/leader to do this!");
				  }
			  }
			  else if(extraArguments[0].equalsIgnoreCase("firstRun")) {
				  //TODO: Add first run parameters
			  }
			  }
		}
			  else {
				sender.sendMessage("That's invalid you inbred fuck!");
				}
		return false;
	}


}