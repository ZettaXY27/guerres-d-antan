package gda2;

import org.bukkit.ChatColor;

public final class StringConstants {
	public static String MESSAGE_PREFIX_MISTAKE = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.RED;
	public static String MESSAGE_PREFIX_ERROR = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.DARK_RED;
	public static String MESSAGE_PREFIX_OK = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.GREEN;
	public static String MESSAGE_PREFIX_INFO = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.BLUE;
	public static String MESSAGE_GENERIC_ERROR = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.RED+"Something went wrong :( I'll try to fix it, try it again maybe?";
	public static String MESSAGE_GENERIC_LINE_GREEN = ChatColor.GREEN + "====================================================" + ChatColor.GREEN;
	public static String MESSAGE_GENERIC_LINE_GOLDE = ChatColor.GOLD + "=====================================================" + ChatColor.GOLD;
	public static String MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS = MESSAGE_PREFIX_ERROR + "Not enough arguments.";
	public static String MESSAGE_ERROR_FACTION_NAME_TAKEN = MESSAGE_PREFIX_ERROR + "Faction name is taken.";

	public static String MESSAGE_ERROR_TOO_LONG = MESSAGE_PREFIX_ERROR + "Name is too long. Cannot be greater than maximum amount of characters.";
	public static String LOGBOOK_PAGE01 = ChatColor.BLACK+"In version 1.0 there are the following commands\nclaim\nstats\nunclaim\ncreate\ninvite\njoin\ncl\ncc\nhelp1\nleave";
	public static String LOGBOOK_PAGE02 = ChatColor.BLACK+"help2\nvisastats\ndenyvisa\nsetvisa\noverclaim\nofficial\nkick\nfirstRun\nnations";
	public static String LOGBOOK_PAGE03 = "capital\ntreasury\ndebugbanks";
	public static String LOGBOOK_PAGE04 = ChatColor.BLACK+"capital\ntreasury\ndebugbanks"+ChatColor.DARK_GRAY+"To create a nation, use the"+ChatColor.BLUE+" /gda create (nation-name)"+ChatColor.BLACK+" command. On default, you will need $1000 in game cash to claim land by using"+ChatColor.BLUE+" /gda claim.";
	public static String LOGBOOK_PAGE05 = ChatColor.BLACK+"To view your nation's stats or the stats or another nation, use /gda nationstats (nation-name)";
	public static String LOGBOOK_PAGE06 = ChatColor.BLACK+"To join a nation, use"+ChatColor.BLUE+" /gda join (nation-name)"+ChatColor.BLACK+". To leave, use"+ChatColor.BLUE+ "/gda leave";
	public static String LOGBOOK_PAGE07 = ChatColor.BLACK+"To unclaim land, use"+ChatColor.BLUE+" /gda unclaim"+ChatColor.BLACK + "on the plot you want to get rid of.";	
	public static String LOGBOOK_PAGE08 = ChatColor.BLACK+"";
}
