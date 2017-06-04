package zetta;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * 
 * @author Inivican
 *
 */
public class ChunkManagement {
	static Main plugin;
	public int currentChunkX;
	public int currentChunkZ;
	public String uniqueIdentifierOfOwner;
	public static String playerName;
	 public static void saveChunkSavesFileConfiguration(FileConfiguration chunkSavesFile, File chunkSavesFileConfiguration) {
		 try {
		 chunkSavesFile.save(chunkSavesFileConfiguration);
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 }
		public void addToChunkSave(String _playerOwner, String factionName){
			plugin.chunkSavesFile.createSection(factionName);
			plugin.chunkSavesFile.getConfigurationSection(factionName).set("Owner", _playerOwner);
			saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
			
		}
		public static void addToChunkSave(String factionName){
			plugin.chunkSavesFile.createSection(factionName);
			plugin.chunkSavesFile.getConfigurationSection(factionName).set("Owner", playerName);
			saveChunkSavesFileConfiguration(plugin.chunkSavesFile, plugin.chunkSavesFileConfiguration);
		}
}