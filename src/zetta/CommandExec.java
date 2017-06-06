

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
	Main plugin;
    ChunkManagement chunkManagement;

	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}

    //Actual command, this has to be registered in the main class
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		HashMap<String, String> inviteStore = new HashMap<String, String>();
		Player player = (Player) arg0;
		UUID ID = player.getUniqueId();		
        ChunkManagement.playerName = arg0.getName();
		if(arg1.getName().equalsIgnoreCase("test")) {
			arg0.sendMessage(ChatColor.AQUA + "Yeah, it's working, what a miracle. Now, get your head out of your ass");
			return true;
		}
		else if(arg1.getName().equalsIgnoreCase("gda")){
		  if (arg3.length == 0) { 
				arg0.sendMessage("Usage: /gda (subcommand)");
		  }
		  else {
			  if(arg3[0].equalsIgnoreCase("claim")) {
				  int playerChunkX = player.getLocation().getChunk().getX();
				  int playerChunkZ = player.getLocation().getChunk().getZ();
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
				  if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
					  //TODO: Add faction check for player
					  if(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks") == null) {
						  plugin.getChunkSavesFile().createSection("ClaimedChunks");
						  plugin.saveChunkSavesFile();
						  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "There was an error! Oops! We'll try to fix it, try again :)");
					  }
					  if(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").contains(playerChunkX+","+playerChunkZ)){						  
					      player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "This land is already claimed by " + ChatColor.GOLD + plugin.chunkSavesFile.getConfigurationSection("ClaimedChunks").getString(playerChunkX+","+playerChunkZ));
					      return true;
					  }
					  else {
					  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).contains("Chunks") == true) {
						  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == false){
							  if(plugin.chunkSavesFile.contains("ClaimedChunks")) {
								  chunkList.add(playerChunkX + "," + playerChunkZ);
								  plugin.chunkSavesFile.getConfigurationSection(playerFaction).set("Chunks", chunkList);
								  plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").set(playerChunkX+","+playerChunkZ, playerFaction);
								  plugin.saveChunkSavesFile();
								  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You just claimed " + ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
								  return true;  
							  }
							  else {
								  plugin.chunkSavesFile.createSection("ClaimedChunks");
								  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "Something went wrong :( I'll try to fix it, try it again maybe?");
								  return true;
								  
							  }
						  }
						  else {
							  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "Your faction already owns this land!");
							  return false;
					  }
						  }
					  else {
//						 plugin.chunkSavesFile.getConfigurationSection(playerFaction).createSection("Chunks");
//						 plugin.chunkSavesFile.getConfigurationSection(playerFaction).getConfigurationSection("Chunks").createSection(playerChunkX + " " + playerChunkZ);
//						 ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
//						 player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "There was an error! Oops! We'll try to fix it, try again :)");
//						 return true;
					  
					  }
				  }
				  }
				  else {
					  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You are not a citizen of any country!");
					  return false;
				  }
				  //////////////
			  }
			  else if (arg3[0].equalsIgnoreCase("create")){
				  if(arg3.length == 0) {
					  arg0.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					 String Name = arg3[1];
					 if(plugin.getSecondConfig().contains(Name)) {
						 player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "There is a country called " + Name + " already!");
						 return false;
					 }
					 else {
						 if(plugin.getSecondConfig().contains("Citizens") == false) {
							 plugin.getSecondConfig().createSection("Citizens");
						 }
						 else if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
							player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You are already in a faction!");
							return false;
						 }
						 plugin.getSecondConfig().createSection(Name);
						 plugin.getSecondConfig().getConfigurationSection(Name).set("Owner", player.getName());
						 plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), Name);
						 plugin.chunkSavesFile.createSection(Name).createSection("Chunks");
						 ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
						 plugin.saveSecondConfig();
						 player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You established a new country called " + ChatColor.GOLD + Name);
						 return true; 
					 }
				
				  }
				  
			  }
			  else if (arg3[0].equalsIgnoreCase("invite")){
				@SuppressWarnings("deprecation")
				Player invitedPlayer = (Bukkit.getServer().getPlayer(arg3[1]));
				String invitedPlayerName = invitedPlayer.getName();
				String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  if(arg3.length == 0) {
					  arg0.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					  inviteStore.put(invitedPlayerName, playerFaction);
					  invitedPlayer.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You have been invited to " + playerFaction + "!");
					  return true;
				  }
			  }
			  else if (arg3[0].equalsIgnoreCase("join")) {
				  if(arg3.length == 0) {
					  arg0.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					  if(inviteStore.containsKey(player.getName())) {
						  String factionPlayerHasBeenInvitedTo = inviteStore.get(player.getName());
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), factionPlayerHasBeenInvitedTo);
						  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You have joined " + factionPlayerHasBeenInvitedTo);

					  }
					  return true;
				  }
			  }
			  else if (arg3[0].equalsIgnoreCase("cl")) {
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
				  player.sendMessage(ChatColor.GREEN + "=======================================");
				  for (String s : chunkList ) player.sendMessage(s);
				  player.sendMessage(ChatColor.GREEN + "=======================================");
				  return true;
			  }
			  else if(arg3[0].equalsIgnoreCase("cc")) {
				  int playerChunkX = player.getLocation().getChunk().getX();
				  int playerChunkZ = player.getLocation().getChunk().getZ();
				  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You are standing on chunk " + playerChunkX + " " + playerChunkZ);
				  return true;
			  }
			  else if(arg3[0].equalsIgnoreCase("help")) {
				  player.sendMessage(ChatColor.GREEN + "=====================================================");
				  player.sendMessage(ChatColor.GOLD + "cc: Short for check chunk. Usage: /gda cc");
				  player.sendMessage(ChatColor.GOLD + "cl: Short for claim list. Usage: /gda cl");
				  player.sendMessage(ChatColor.GOLD + "invite: Invites a player to your country. Usage: /gda invite (player)");
				  player.sendMessage(ChatColor.GOLD + "create: Establishes a new country. Usage: /gda create (countryName)");
				  player.sendMessage(ChatColor.GOLD + "claim: Claims a chunk for your country. It costs 1000$ for one chunk. Usage: /gda claim");
				  player.sendMessage(ChatColor.GOLD + "leave: Use this command to leave your country. If you're the owner, the country is deleted. Usage: /gda leave");
				  player.sendMessage(ChatColor.GOLD + "help2: Shows the next page. Usage: /gda help2");
				  player.sendMessage(ChatColor.GREEN + "=====================================================");
				  return true;
			  }
			  else if(arg3[0].equalsIgnoreCase("leave")) {
				  if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
					  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
					  String playerName = player.getName();
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
								    Bukkit.broadcastMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + playerFaction + " has been disbanded!");
								    return true;
								}
							    else {
									player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "Something went wrong :( I'll try to fix it, try it again maybe?");
									return true;
								}
						   }

//						  plugin.saveSecondConfig();
//						  plugin.saveChunkSavesFile();
					  }
					  else {
						  plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
						  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "You left " + playerFaction);
						  return true;
					  }
				  }
				  else {
					  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "Something went wrong :( I'll try to fix it, try it again maybe?"); 
				  }
			  }
			  else if(arg3[0].equalsIgnoreCase("help2")) {
				  //TODO: Add help2 command
			  }

			  }
		}
			  else {
				arg0.sendMessage("That's invalid you inbred fuck!");
				}
		return false;
	}


}