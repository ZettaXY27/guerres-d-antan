package zetta;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * 
 * @author Inivican
 * Revised by ZettaX
 * This class is to be used for all cases of player interaction with chunks.
 * 
 * KNOWN ISSUES:
 * We have no means of detecting explosive damage on the land as of yet.
 */
@SuppressWarnings("unused")
public class ChunkInteraction implements Listener {
	Main chunkSavesRetrieval;
	public ChunkInteraction(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		chunkSavesRetrieval = main;
	}


	/**
	 * 
	 * @param event the event of a block being broken
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block blockBeingBroken = event.getBlock();
		int blockChunkX = blockBeingBroken.getChunk().getX();
		int blockChunkZ = blockBeingBroken.getChunk().getZ();
		int playerChunkX = event.getPlayer().getLocation().getChunk().getX();
		int playerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
		String claimedChunkFactionName =  chunkSavesRetrieval.getChunkSavesFile().getConfigurationSection("ClaimedChunks").getString(blockChunkX+","+blockChunkZ);
		String playerFaction = chunkSavesRetrieval.getSecondConfig().getConfigurationSection("Citizens").getString(player.getName());
		try {
			boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
			if(chunkPertainsToPlayersFaction == false) {
				event.setCancelled(true);
				player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + "You do not have permission to edit " + blockChunkX + " " + blockChunkZ);
				player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + "This land is owned by " + claimedChunkFactionName);
			}
			else{
			}
		} catch (NullPointerException npe) {
		    // It's fine if chunkPertainsToPlayersFaction throws an NPE
		}
		if(claimedChunkFactionName == null) {
			event.setCancelled(false);
		}
		else {
			
		}
			
//		if(chunkSavesRetrieval.getChunkSavesFile().contains("ClaimedChunks")) {
			//chunkSavesRetrieval.getChunkSavesFile().getConfigurationSection("ClaimedChunks").contains(blockChunkX+","+blockChunkZ) == true working piece of code
//		}
	    //  else {
		//	player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "Something went wrong! :( Inform ZettaX or Inivican immediately! (Error code 00xCI001");
		//    }
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
			Player player = event.getPlayer();
			Block blockBeingPlaced = event.getBlock();
			int blockChunkX = blockBeingPlaced.getChunk().getX();
			int blockChunkZ = blockBeingPlaced.getChunk().getZ();
			int playerChunkX = event.getPlayer().getLocation().getChunk().getX();
			int playerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
			String claimedChunkFactionName =  chunkSavesRetrieval.getChunkSavesFile().getConfigurationSection("ClaimedChunks").getString(blockChunkX+","+blockChunkZ);
			String playerFaction = chunkSavesRetrieval.getSecondConfig().getConfigurationSection("Citizens").getString(player.getName());
			try {
				boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
				if(chunkPertainsToPlayersFaction == false) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "You do not have permission to edit " + blockChunkX + " " + blockChunkZ);
					player.sendMessage(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"]" + ChatColor.RED + "This land is owned by " + claimedChunkFactionName);
				}
				else{
				}
			} catch (NullPointerException npe) {
			    // It's fine if chunkPertainsToPlayersFaction throws an NPE
			}
			if(claimedChunkFactionName == null) {
				event.setCancelled(false);
			}
			else {
				
			}
		
	}
	@EventHandler
	public void onEntityExplosion(EntityExplodeEvent event) {
		try {
			Entity entityExploding = event.getEntity();
			int entityChunkX = entityExploding.getLocation().getChunk().getX();
			int entityChunkZ = entityExploding.getLocation().getChunk().getZ();
			Boolean chunkIsClaimed = chunkSavesRetrieval.getChunkSavesFile().getConfigurationSection("ClaimedChunks").contains(entityChunkX+","+entityChunkZ);
			if(chunkIsClaimed == true) {
				event.setCancelled(true);
			}
		} catch (NullPointerException npe) {
			//Catchs the npe
		}
	}
}
