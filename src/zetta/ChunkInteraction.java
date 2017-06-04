package zetta;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChunkInteraction implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		int blockAPlayerInteractedWithinChunkCoordinateX = event.getClickedBlock().getLocation().getChunk().getX();
		int blockAPlayerInteractedWithinChunkCoordinateZ = event.getClickedBlock().getLocation().getChunk().getZ();
		
		
		Material materialThatIsInHand = player.getItemInHand().getType();
		
//		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
//			if(materialThatIsInHand == Material.STICK){
//				player.chat("I have been forced to say this against my will.");
//			}
//		}
		
//		if(blockAPlayerInteractedWithinChunkCoordinateX == ){
//			
//		}
		
		
		
	}
	
	
}
