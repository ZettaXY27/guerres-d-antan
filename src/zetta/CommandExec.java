package zetta;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class CommandExec implements CommandExecutor {
	Main plugin;


	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}

    //Actual command, this has to be registered in the main class
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg2.equalsIgnoreCase("test")) {
			arg0.sendMessage(ChatColor.AQUA + "Yeah, it's working, what a miracle. Now, get your head out of your ass");
		}
		return false;
	}


}