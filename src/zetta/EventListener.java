package zetta;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;
//import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;


/**
 * 
 * @author ZettaX, Inivican
 * 
 *  @note       10 June 2017
 *              The name of this class has been changed from ChunkInteraction to EventListener.
 */
@SuppressWarnings("unused")
public class EventListener implements Listener {
	Main plugin;
	final static HashMap<String, BukkitTask> taskIDS = new HashMap<String, BukkitTask>();

	public EventListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		plugin = main;
	}
//	@EventHandler
//	public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
//	if (event.getNamedPlayer().getName().equals("ZettaX")) {
//	event.setTag(ChatColor.GOLD + "["  + ChatColor.YELLOW + "GDAGod" + ChatColor.GOLD +"] " + ChatColor.GREEN + "ZettaX");
//	return;
//	}
//	else {
//		event.setTag("");
//	}
//	}


	/**
	 * 
	 * @param event
	 *            the event of a block being broken
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block blockBeingBroken = event.getBlock();
		int blockChunkX = blockBeingBroken.getChunk().getX();
		int blockChunkZ = blockBeingBroken.getChunk().getZ();
		int playerChunkX = event.getPlayer().getLocation().getChunk().getX();
		int playerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
		try {
			String claimedChunkFactionName = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
					.getString(blockChunkX + "," + blockChunkZ);
			String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
					.getString(player.getName());
			boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
			if (claimedChunkFactionName == null) {
				event.setCancelled(false);
			} else if (chunkPertainsToPlayersFaction == false) {
				event.setCancelled(true);
				player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + "You do not have permission to edit " + blockChunkX + " " + blockChunkZ);
				player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + "This land is owned by " + claimedChunkFactionName);
			}
		} catch (NullPointerException npe) {

			// It's fine if chunkPertainsToPlayersFaction throws an NPE
		}
	}
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (CommandExec.taskIDs.containsKey(playerName) == true) {
			event.setCancelled(true);
			plugin.getServer().getScheduler().cancelTask(CommandExec.taskIDs.get(player.getName()).getTaskId());
			CommandExec.taskIDs.remove(playerName);
			Bukkit.broadcastMessage(
					StringConstants.MESSAGE_PREFIX_INFO + " " + playerName + "  " + "tried to escape!");
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block blockBeingPlaced = event.getBlock();
		int blockChunkX = blockBeingPlaced.getChunk().getX();
		int blockChunkZ = blockBeingPlaced.getChunk().getZ();
		try {
			String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
					.getString(player.getName());
			String claimedChunkFactionName = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
					.getString(blockChunkX + "," + blockChunkZ);
			boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
			if (claimedChunkFactionName == null) {
				event.setCancelled(false);
			} else if (chunkPertainsToPlayersFaction == false) {
				event.setCancelled(true);
				player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + "You do not have permission to edit "
						+ blockChunkX + " " + blockChunkZ);
				player.sendMessage(
						StringConstants.MESSAGE_PREFIX_MISTAKE + "This land is owned by " + claimedChunkFactionName);
			} else {
			}
		} catch (NullPointerException npe) {
			// It's fine if chunkPertainsToPlayersFaction throws an NPE
		}

	}

	@EventHandler
	public void onEntityExplosion(EntityExplodeEvent event) {
		try {
			Entity entityExploding = event.getEntity();
			int entityChunkX = entityExploding.getLocation().getChunk().getX();
			int entityChunkZ = entityExploding.getLocation().getChunk().getZ();
			Boolean chunkIsClaimed = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
					.contains(entityChunkX + "," + entityChunkZ);
			if (chunkIsClaimed == true) {
				event.setCancelled(true);
			}
		} catch (NullPointerException npe) {
			// Catchs the npe
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		try {
			Player player = event.getEntity();
			String playerName = player.getName();
			int taskID = CommandExec.taskIDs.get(playerName).getTaskId();
			if (CommandExec.taskIDs.containsKey(playerName) == true) {
				Bukkit.broadcastMessage(
						StringConstants.MESSAGE_PREFIX_INFO + " " + playerName + "  " + " could not overclaim");
				plugin.getServer().getScheduler().cancelTask(CommandExec.taskIDs.get(player.getName()).getTaskId());
				CommandExec.taskIDs.remove(playerName);
			}
			int TPtaskID = CommandExec.TPtaskIDs.get(playerName).getTaskId();
		    if (CommandExec.TPtaskIDs.containsKey(playerName)) {
				plugin.getServer().getScheduler().cancelTask(CommandExec.TPtaskIDs.get(player.getName()).getTaskId());
				CommandExec.TPtaskIDs.remove(playerName);
				Bukkit.broadcastMessage(
						StringConstants.MESSAGE_PREFIX_INFO + " " + playerName + "  " + " could not teleport");
			}
		} catch (NullPointerException e) {
			// don't cringe, just get used to NPEs in this thing, there will be
			// many but that's ok
		}
	       String playerName = event.getEntity().getName();
		   if (taskIDS.containsKey(playerName) == true) {
			   plugin.getServer().getScheduler().cancelTask(taskIDS.get(playerName).getTaskId());
			   taskIDS.remove(playerName);
		   }

	}
	/**
	 * @author Inivican
	 * @param playerName name of player
	 * @param factionName name of faction the player is interacting with
	 * @return whether the player has an active visa for that faction.
	 */
	public boolean playerDoesNotAlreadyHaveAVisaForAFaction(String playerName, String factionName) {
		if(plugin.getSecondConfig().getConfigurationSection(factionName).getStringList("Visas").contains(playerName)==false){
			return false;
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerMovement(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			final int playerChunkX = event.getPlayer().getLocation().getChunk().getX();
			final int playerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
			final String playerChunk = playerChunkX + "," + playerChunkZ;
			int playerY = event.getPlayer().getLocation().getBlockY();
			String claimedChunkFactionName = plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
					.getString(playerChunkX + "," + playerChunkZ);
			String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
					.getString(player.getName());
			boolean chunkPertainsToPlayersFaction = claimedChunkFactionName.equals(playerFaction);
//			if (claimedChunkFactionName == null || playerFaction == null) {
//                event.setCancelled(false);
//			} else 
			if (chunkPertainsToPlayersFaction == false && playerDoesNotAlreadyHaveAVisaForAFaction(player.getName(), claimedChunkFactionName) == false) {
				player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD + "]"
						+ ChatColor.RED + "WARNING: This land is owned by " + claimedChunkFactionName + " Get out now or you WILL be shot. You have 5 seconds...");
				taskIDS.put(player.getName(), Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					@Override
					public void run() {
						int currentPlayerChunkX = event.getPlayer().getLocation().getChunk().getX();
						int currentPlayerChunkZ = event.getPlayer().getLocation().getChunk().getZ();
						String currentChunk = currentPlayerChunkX + "," + currentPlayerChunkZ;
						if (playerChunk.equals(currentChunk) == false) {
							taskIDS.clear();
							return;
						}
						else if (playerChunk.equals(currentChunk) == true) {
							if(player.isDead()) {
								return;
							}
							player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "GuerresD'Antan"
									+ ChatColor.GOLD + "]" + ChatColor.RED + " You were warned... ");
							player.setHealth(0);
							taskIDS.remove(player.getName());
							taskIDS.clear();
							return;
						} else {
							taskIDS.remove(player.getName());
							taskIDS.clear();
							return;
						}
					}
				}, 20 * 5));
			}
		} catch (NullPointerException npe) {
			// Ignore null bointer exbection
		}
		if(CommandExec.TPtaskIDs.containsKey(player.getName()) && event.getFrom().getBlockX() != event.getTo().getBlockX() || CommandExec.TPtaskIDs.containsKey(player.getName()) && event.getFrom().getBlockY() != event.getTo().getBlockY()) {
			plugin.getServer().getScheduler().cancelTask(CommandExec.TPtaskIDs.get(player.getName()).getTaskId());
			CommandExec.TPtaskIDs.remove(player.getName());
            player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + " You can't move while teleporting!");
		}
		 if(taskIDS.containsKey(player.getName())) {
		     taskIDS.remove(player.getName());
		     taskIDS.clear();
			 return;
			 }
		
	}
}
