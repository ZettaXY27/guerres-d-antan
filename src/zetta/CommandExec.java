

package zetta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
				  if(plugin.getSecondConfig().getConfigurationSection("Citizens").contains(player.getName())) {
					  //TODO: Add faction check for player
					  int playerChunkX = player.getLocation().getChunk().getX();
					  int playerChunkZ = player.getLocation().getChunk().getZ();
					  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).contains("Chunks") == true) {
						  if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks").contains(playerChunkX + " " + playerChunkZ) == false){
							  chunkList.add(playerChunkX + " " + playerChunkZ);
							  plugin.chunkSavesFile.getConfigurationSection(playerFaction).set("Chunks", chunkList);
							  ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
							  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You just claimed " + ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
							  return true;  
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
				String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  if(arg3.length == 0) {
					  arg0.sendMessage("Not enough arguments");
					  return false;
				  }
				  else {
					  invitedPlayer.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You have been invited to " + playerFaction + "!");
					  return true;
				  }
			  }
			  else if (arg3[0].equalsIgnoreCase("claimlist")) {
				  String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(arg0.getName());
				  List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks.Chunks");
				  for (String s : chunkList ) player.sendMessage(s);
			  }
			  }
		}
			  else {
				arg0.sendMessage("That's invalid you inbred fuck!");
				}
		return false;
	}


}