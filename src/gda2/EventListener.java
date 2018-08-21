package gda2;


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

public class EventListener implements Listener {
	
	private Main mainClass;
	
	final static HashMap<String, BukkitTask> taskIDs = new HashMap<String, BukkitTask>();
	
	public EventListener(Main mainClass) {
		mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
		this.mainClass = mainClass;
	}
	
	
	public void setMainClass(Main mainClass) {
		this.mainClass = mainClass;
	}
	
	public Main getMainClass() {
		return this.mainClass;
	}
	
	/*
	 * Event Handlers Past This Point
	 */
	
	/**
	 * 
	 * @param event
	 */
	public void onBlockBreak(BlockBreakEvent event) {
		
	}
	
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		
	}
	
	public void onEntityExplosion(EntityExplodeEvent event) {
		
	}
	
	public void onPlayerDeath(PlayerDeathEvent event) {
		
	}
	
	public void onPlayerMovement(PlayerMoveEvent event) {
		
	}
}
