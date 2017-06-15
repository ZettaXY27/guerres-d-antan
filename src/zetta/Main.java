package zetta;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
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
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

/**
 * 
 * @author sherendeen, ZettaX
 *
 */
@SuppressWarnings("unused")
public class Main extends JavaPlugin {
	public static Economy economy = null;
	public FileConfiguration secondConfig = null;
	public File secondConfigFile = null;
	public File chunkSavesFileConfiguration;
	public FileConfiguration chunkSavesFile;

	// Reloads the secondary config file, if it's non existent it will attempt
	// to create one
	public void saveChunkSavesFile() {
		try {
			chunkSavesFile.save(chunkSavesFileConfiguration);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadSecondConfig() {
		if (secondConfigFile == null) {
			secondConfigFile = new File(getDataFolder(), "secondConfig.yml");
		}
		secondConfig = YamlConfiguration.loadConfiguration(secondConfigFile);
	}

	// Returns the secondary config file
	public FileConfiguration getSecondConfig() {
		if (secondConfig == null) {
			reloadSecondConfig();
		}
		return secondConfig;
	}

	public FileConfiguration getChunkSavesFile() {
		if (chunkSavesFile == null) {
			reloadChunkSavesFile();
		}
		return chunkSavesFile;
	}

	public void reloadChunkSavesFile() {
		if (chunkSavesFileConfiguration == null) {
			chunkSavesFileConfiguration = new File(getDataFolder(), "chunk_saves.yml");
		}
		chunkSavesFile = YamlConfiguration.loadConfiguration(chunkSavesFileConfiguration);
	}

	// Saves the secondary config file
	public void saveSecondConfig() {
		if (secondConfig == null || secondConfigFile == null) {
			return;
		}
		try {
			getSecondConfig().save(secondConfigFile);
		} catch (IOException ex) {
			getLogger().log(Level.SEVERE, "Could not save config to " + secondConfigFile, ex);
		}
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public Economy getEconomy() {
		return economy;
	}

	@Override
	// debug messages and event registering
	public void onEnable() {
		setupEconomy();
		new EventListener(this);
		chunkSavesFileConfiguration = new File(getDataFolder(), "chunk_saves.yml"); 
		chunkSavesFile = YamlConfiguration.loadConfiguration(chunkSavesFileConfiguration);
		saveChunkSavesFile();
		getSecondConfig();
		this.saveDefaultConfig();
		// Adding commands, it has to be defined in the CommandExec class for it
		// to work
		this.getCommand("test").setExecutor(new CommandExec(this));
		this.getCommand("gda").setExecutor(new CommandExec(this));
		getLogger()
		.info("papa, you might be looking at console rn, so I'd like to wish you a good day");
	}
	// A quick little message. It's not necessary but eh why not
	@Override
	public void onDisable() {
		getLogger().info("guerresD'antan has been disabled! HOW COULD YOU!");
	}
}
