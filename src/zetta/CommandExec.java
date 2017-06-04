

package zetta;

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
				  if(plugin.chunkSavesFile.getConfigurationSection("Citizens").contains(player.getName()) == true) {
					  //TODO: Add faction check for player and store the chunk claimed for X faction
				  }
				  int playerChunkX = player.getLocation().getChunk().getX();
				  int playerChunkZ = player.getLocation().getChunk().getZ();
				  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You just claimed " + ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
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
						 plugin.saveSecondConfig();
						 player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + "You established a new country called " + ChatColor.GOLD + Name);
						 return true; 
					 }
				
				  }
				  
			  }
			  else {
				arg0.sendMessage("That's invalid you inbred fuck!");
				}
		  }	
		}
		return false;
	}


}