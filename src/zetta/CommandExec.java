package zetta;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExec implements CommandExecutor {
	Main plugin;


	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("untag")) {
	        if (args.length != 1) {
	            return false;
	        }
	        //Get the player to untag
	        @SuppressWarnings("deprecation")
			Player target = Bukkit.getServer().getPlayer(args[0]);

	        // Make sure the player is online
	        if (target == null) {
	            sender.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + args[0] + " Has to be online to perform this command.");
	            return true;
	        }

	        // Untags the player
	        UUID uid = target.getUniqueId();
	        if(plugin.getPlayerTags().getBoolean(uid + ".tagged") == true) {
	        	plugin.getPlayerTags().set(uid + ".tagged", false);
	        	plugin.savePlayerTags();
	        	sender.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + args[0] + " has been untagged.");	
	        	target.sendMessage(ChatColor.YELLOW + "[CombatTag++] " + ChatColor.GOLD + "You have been untagged");
	        	return true;
	        }
	        return true;
	    }
	    return false;
	}
}