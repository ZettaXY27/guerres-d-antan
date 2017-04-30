package zetta;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
/*import org.bukkit.entity.Creeper;*/
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class Main extends JavaPlugin implements Listener {
	@Override
	//debug messages and event registering
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);	
		getPlayerTags();
		this.getCommand("untag").setExecutor(new CommandExec(this));
	}
	private  FileConfiguration playerTags = null;
	private File playerTagsFile = null;
	
	public void reloadPlayerTags() {
	    if (playerTagsFile == null) {
	    	playerTagsFile = new File(getDataFolder(), "playerTags.yml");
	    }
	    playerTags = YamlConfiguration.loadConfiguration(playerTagsFile);
	}
	public FileConfiguration getPlayerTags() {
	    if (playerTags == null) {
	    	reloadPlayerTags();
	    }
	    return playerTags;
	}
	public void savePlayerTags() {
	    if (playerTags == null || playerTagsFile == null) {
	        return;
	    }
	    try {
	        getPlayerTags().save(playerTagsFile);
	    } catch (IOException ex) {
	        getLogger().log(Level.SEVERE, "Could not save config to " + playerTagsFile, ex);
	    }
	}
	//Main onDamage event method that does all the tagging
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {                                      
		if((event.getDamager() instanceof LivingEntity) || (event.getDamager() instanceof Arrow) || (event.getDamager() instanceof FallingBlock)  || (event.getDamager() instanceof LargeFireball) || (event.getDamager() instanceof SmallFireball)  || (event.getDamager() instanceof EnderPearl) && event.getEntity() instanceof Player) {
		}
		else {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				java.util.UUID ID = player.getUniqueId();
				if (player.hasPermission("cbtplusplus.immune")) {
					getPlayerTags().set(ID + ".tagged", false); 
					savePlayerTags();
				}
				else {
					if(getPlayerTags().getBoolean(ID + ".tagged") == false ) {
						if(player.getWorld().getName() == "SGworld" || (player.getWorld().getName() == "pa")) {
							getPlayerTags().set(ID + ".tagged", false);
							savePlayerTags();
						}
						getPlayerTags().set(ID + ".tagged", true);
						savePlayerTags();
						player.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + "You have been tagged! Do not log off or you will be punished.");
						getLogger().info("Debug Info X001");
						BukkitScheduler scheduler = getServer().getScheduler();
						    scheduler.scheduleSyncDelayedTask(this, new Runnable() {
						           @Override
						           public void run() {
						                // Scheduler does the stuff below here
						   				if(getPlayerTags().getBoolean(ID + ".tagged") == true ) {
						   					player.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD +  "You can log off now.");
							   				getPlayerTags().set(ID + ".tagged", false);
							   				savePlayerTags();
						   				}
						   				else {
						   					//Nothing here
						   				}
						           }
						 }, /*Amount of ticks */ 400L);
					}
			}
				//Not working currently (Attacker gets tag)
				/*Player player2 = (Player) event.getDamager();
				java.util.UUID ID2 = player2.getUniqueId();
			    if (player2.hasPermission("cbtplusplus.immune")) {
					getPlayerTags().set(ID2 + ".tagged", false); 
					savePlayerTags();
				}
				else {
					if(getPlayerTags().getBoolean(ID2 + ".tagged") == false ) {
						getPlayerTags().set(ID2 + ".tagged", true);
						savePlayerTags();
						player2.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + "You have been tagged! Do not log off or you will be punished.");
						getLogger().info("Debug Info X002");
						BukkitScheduler scheduler = getServer().getScheduler();
						    scheduler.scheduleSyncDelayedTask(this, new Runnable() {
						           @Override
						           public void run() {
						                // Scheduler does the stuff below here
						   				if(getPlayerTags().getBoolean(ID2 + ".tagged") == true ) {
						   					player2.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD +  "You can log off now.");
							   				getPlayerTags().set(ID2 + ".tagged", false);
							   				savePlayerTags();
						   				}
						   				else {
						   					//Nothing here
						   				}
						           }*/
						// }, /*Amount of ticks */ 400L);	
						 
				}
			}
		
		}
        //If the entity being damaged is a player

				
		/*if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof Player) {
			}
			else {
				//Do nothing if its another type of entitee
			}
		
		}
		else {
			// Do nothing if its another type of damage
		}
		*/		       		
	@EventHandler
	//Removes the attachment from the player when they log out, if they have it
	public void onLogout(PlayerQuitEvent event) {
		Player playerThatLoggedOut = event.getPlayer();
		UUID logOutID = playerThatLoggedOut.getUniqueId();
		if(getPlayerTags().getBoolean(logOutID + ".tagged") == true) {
			Player player = event.getPlayer();
			java.util.UUID ID = player.getUniqueId();
				player.setHealth(0);
				getPlayerTags().set(ID + ".tagged", false);
				savePlayerTags();
				this.getServer().broadcastMessage(player.getName() + " combat logged!");

		}
	}
	@EventHandler
	//Blocks teleportation
	public void onTeleport(PlayerTeleportEvent event) {
		Player playerThatTeleports = event.getPlayer();
		UUID teleportID = playerThatTeleports.getUniqueId();
		if(getPlayerTags().getBoolean(teleportID + ".tagged") == true) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + "You are combat tagged! You can't teleport.");
		}
	}
	@EventHandler
	//Removes tag if player dies
	public void onPDeath(PlayerDeathEvent event) {
		Player playerThatDies = event.getEntity();
		UUID deathID = playerThatDies.getUniqueId();
		if(getPlayerTags().getBoolean(deathID + ".tagged") == true) {
			getPlayerTags().set(deathID + ".tagged", false);
			savePlayerTags();
		}
	}
	
	
	
    @Override
    public void onDisable() {
    	getLogger().info("CombatTag++ has been disabled!");
    }
}
