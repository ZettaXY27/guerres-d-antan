package gda2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
/**
 * 
 * @author Inivi_000
 * Subcommands of setting relationship
 * -------------
 * gda setrelation <stuff> <stuff>
 * 
 */
public class RelationshipControlExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		
		if(validate(command)) {
			return processSubcommand(extraArguments, sender);
		}
		
		return false;
	}
	
	private boolean validate(Command command) {
		return command.getName().equalsIgnoreCase("gda");
	}
	
	private boolean processSubcommand(String[] args, CommandSender sender) {
		if(args.length == 0) {
			sender.sendMessage("Usage: /gda (subcommand)");
			return false;
		} else {
			if(args[1].equalsIgnoreCase("")) {
				
			}
			
			return true;
		}
		
	}
	
	private boolean validateArg1(String arg) {
		if(arg.equalsIgnoreCase("setrelationship")) {
			return true;
		}
		return false;
	}
}
