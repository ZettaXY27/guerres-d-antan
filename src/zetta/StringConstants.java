package zetta;

import org.bukkit.ChatColor;

/**
 * 
 * @author Inivican
 * @since  Tuesday 6 June, 2017
 * @version one billion
 * @example StringConstants.MESSAGE_PREFIX_MISTAKE + "..." etc
 * @note
 * We do need to make the rest of the code fit to this model.
 * The constants in this class are static.
 * 
 */
public class StringConstants extends Main {
	public final static String MESSAGE_PREFIX_MISTAKE = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.RED;
	public final static String MESSAGE_PREFIX_ERROR = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.DARK_RED;
	public final static String MESSAGE_PREFIX_OK = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.GREEN;
	public final static String MESSAGE_PREFIX_INFO = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.BLUE;
	public final static String MESSAGE_GENERIC_ERROR = ChatColor.GOLD + "["  + ChatColor.YELLOW + "GuerresD'Antan" + ChatColor.GOLD +"] " + ChatColor.RED+"Something went wrong :( I'll try to fix it, try it again maybe?";
	public final static String MESSAGE_GENERIC_LINE_GREEN = ChatColor.GREEN + "====================================================" + ChatColor.GREEN;
	public final static String MESSAGE_GENERIC_LINE_GOLDE = ChatColor.GOLD + "=====================================================" + ChatColor.GOLD;
	public final static String MESSAGE_ERROR_NOT_ENOUGH_ARGUMENTS = MESSAGE_PREFIX_ERROR + "Not enough arguments.";
	
	public final static String LOGBOOK_PAGE01 = ChatColor.BLACK+"In version 1.0 there are the following commands\nclaim\nstats\nunclaim\ncreate\ninvite\njoin\ncl\ncc\nhelp1\nleave";
	public final static String LOGBOOK_PAGE02 = ChatColor.BLACK+"help2\nvisastats\ndenyvisa\nsetvisa\noverclaim\nofficial\nkick\nfirstRun\nnations";
	public final static String LOGBOOK_PAGE03 = "capital\ntreasury\ndebugbanks";
	public final static String LOGBOOK_PAGE04 = ChatColor.BLACK+"capital\ntreasury\ndebugbanks"+ChatColor.DARK_GRAY+"To create a nation, use the"+ChatColor.BLUE+" /gda create (nation-name)"+ChatColor.BLACK+" command. On default, you will need $1000 in game cash to claim land by using"+ChatColor.BLUE+" /gda claim.";
	public final static String LOGBOOK_PAGE05 = ChatColor.BLACK+"To view your nation's stats or the stats or another nation, use /gda nationstats (nation-name)";
	public final static String LOGBOOK_PAGE06 = ChatColor.BLACK+"To join a nation, use"+ChatColor.BLUE+" /gda join (nation-name)"+ChatColor.BLACK+". To leave, use"+ChatColor.BLUE+ "/gda leave";
	public final static String LOGBOOK_PAGE07 = ChatColor.BLACK+"To unclaim land, use"+ChatColor.BLUE+" /gda unclaim"+ChatColor.BLACK + "on the plot you want to get rid of.";
	
	public final static String LOGBOOK_PAGE08 = ChatColor.BLACK+"";
	
	
}