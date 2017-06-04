package zetta;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChunkInteraction implements Listener {
	public Main plugin;
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();

		
		//Define player faction
		String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(event.getPlayer().getName());
		
		
		List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks");
		int playerChunkX = event.getPlayer().getLocation().getChunk().getX();
		int playerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
		
		//Unnecessary
		//Material materialThatIsInHand = player.getItemInHand().getType();
		
		//If a player LEFT CLICKS on a block...
		if(event.getAction() == Action.LEFT_CLICK_BLOCK)
			if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).contains("Chunks") == true) {
				//Prevents a player from breaking blocks within a chunk that does not belong to their faction
				if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks").contains(playerChunkX +" " + playerChunkZ) == false)
				{
				  event.setCancelled(true);
				  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You do not own   " + ChatColor.DARK_AQUA + playerChunkX + ", " + playerChunkZ);
				  
				}
				else {
				 
				  
			}
		}
		
		//If a player RIGHT CLICKS on a block...
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).contains("Chunks") == true) {
				//Prevents a player from placing blocks within a chunk that does not belong to their faction
				if(plugin.chunkSavesFile.getConfigurationSection(playerFaction).getStringList("Chunks").contains(playerChunkX +" " + playerChunkZ) == false)
				{
				  event.setCancelled(true);
				  player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + " You do not own   " + ChatColor.DARK_AQUA + playerChunkX + ", " + playerChunkZ);
				  
				}
				else {
				 
				  
				}
			}
		
		
		}
	
	}
}
