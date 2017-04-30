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


@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener {
	@Override
	//debug messages and event registering
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);	
		getSecondaryFile();
		this.getCommand("untag").setExecutor(new CommandExec(this));
	}
	private  FileConfiguration secondary = null;
	private File secondaryFile = null;
	
	public void reloadSecondary() {
	    if (secondaryFile == null) {
	    	secondaryFile = new File(getDataFolder(), "playerTags.yml");
	    }
	    secondary = YamlConfiguration.loadConfiguration(secondaryFile);
	}
	public FileConfiguration getSecondaryFile() {
	    if (secondary == null) {
	    	reloadSecondary();
	    }
	    return secondary;
	}
	public void saveSecondaryFile() {
	    if (secondary == null || secondaryFile == null) {
	        return;
	    }
	    try {
	    	getSecondaryFile().save(secondaryFile);
	    } catch (IOException ex) {
	        getLogger().log(Level.SEVERE, "Could not save config to " + secondaryFile, ex);
	    }
	}
	
    @Override
    public void onDisable() {

    }
}
