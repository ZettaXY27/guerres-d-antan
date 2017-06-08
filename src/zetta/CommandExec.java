
package zetta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.milkbowl.vault.economy.Economy;;

@SuppressWarnings("unused")
public class CommandExec implements CommandExecutor {
	// Variable declaration field
	Main plugin;
	Economy economy = null;
	ChunkManagement chunkManagement;
	HashMap<String, String> inviteStore = new HashMap<String, String>();

	public CommandExec(Main plugin) {
		this.plugin = plugin;
	}

	// Invite check, will return NPE if the HashMap is null.
	boolean playerHasBeenInvited(String player1, String player2) {
		// Value is normally assigned to player1, which is the key
		boolean isInvited = inviteStore.containsKey(player1) && inviteStore.containsValue(player2);
		return isInvited;
	}

	boolean playerHasEnoughBalance(String playerName, int amount) {
		boolean hasEnoughMoney = economy.has(playerName, amount);
		return hasEnoughMoney;

	}

	boolean playerIsInAFaction(String playerName) {
		boolean isInFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").contains(playerName);
		return isInFaction;
	}

	String getPlayerFaction(String playerName) {
		String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens").getString(playerName);
		return playerFaction;
	}

	boolean playerIsMember(String playerName, String factionName) {
		boolean playerIsMember = plugin.getSecondConfig().getConfigurationSection(factionName).getStringList("Members")
				.contains(playerName);
		return playerIsMember;
	}

	// Actual command, this has to be registered in the main class
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] extraArguments) {
		Player player = (Player) sender;
		UUID ID = player.getUniqueId();
		ChunkManagement.playerName = sender.getName();
		if (command.getName().equalsIgnoreCase("test")) {
			sender.sendMessage(
					ChatColor.AQUA + "Yeah, it's working, what a miracle. Now, get your head out of your ass");
			return true;
		} else if (command.getName().equalsIgnoreCase("gda")) {
			if (extraArguments.length == 0) {
				sender.sendMessage("Usage: /gda (subcommand)");
			} else {
				if (extraArguments[0].equalsIgnoreCase("claim")) {
					economy = plugin.getEconomy();
					int playerChunkX = player.getLocation().getChunk().getX();
					int playerChunkZ = player.getLocation().getChunk().getZ();
					// String playerFaction =
					// plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
					if (plugin.getSecondConfig().getConfigurationSection("Citizens").contains(sender.getName())) {
						// TODO: Add faction check for player
						if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks") == null) {
							plugin.getChunkSavesFile().createSection("ClaimedChunks");
							plugin.saveChunkSavesFile();
							player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
							return true;
						}
						if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
								.contains(playerChunkX + "," + playerChunkZ)) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "This land is already claimed by "
									+ ChatColor.GOLD + plugin.chunkSavesFile.getConfigurationSection("ClaimedChunks")
											.getString(playerChunkX + "," + playerChunkZ));
							return true;
						} else {
							if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
									.contains("Chunks") == true) {
								if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
										.getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == false) {
									if (plugin.chunkSavesFile.contains("ClaimedChunks")) {
										List<String> chunkList = plugin.chunkSavesFile
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getStringList("Chunks");
										List<String> officerList = plugin.getSecondConfig()
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getStringList("Officers");
										plugin.saveSecondConfig();
										Boolean playerIsOfficer = officerList.contains(player.getName());
										Boolean playerIsLeader = plugin.getSecondConfig()
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.getString("Owner").equals(sender.getName());
										if (playerIsOfficer == true || playerIsLeader == true) {
											int claimCost = plugin.getConfig().getInt("ClaimCost");
											if (playerHasEnoughBalance(player.getName(), claimCost) == true) {
												economy.withdrawPlayer(player.getName(), claimCost);
												chunkList.add(playerChunkX + "," + playerChunkZ);
												plugin.chunkSavesFile
														.getConfigurationSection(getPlayerFaction(player.getName()))
														.set("Chunks", chunkList);
												plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks").set(
														playerChunkX + "," + playerChunkZ,
														getPlayerFaction(player.getName()));
												plugin.saveChunkSavesFile();
												player.sendMessage(StringConstants.MESSAGE_PREFIX_OK
														+ " You just claimed " + ChatColor.DARK_AQUA + playerChunkX
														+ " " + playerChunkZ);
												player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " " + claimCost
														+ " has been withdrawn from your account!");
												return true;
											} else {
												player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
														+ " You do not have enough money!");
												return true;
											}

										} else {
											player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
													+ " You are not allowed to do this!");
											return true;
										}
									} else {
										plugin.chunkSavesFile.createSection("ClaimedChunks");
										player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
										return true;

									}
								} else {
									player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
											+ " Your faction already owns this land!");
									return true;
								}
							} else {
								plugin.getChunkSavesFile().getConfigurationSection(getPlayerFaction(player.getName()))
										.createSection("Chunks");
								plugin.saveChunkSavesFile();
								player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
								return true;

							}
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + " You are not a citizen of any country!");
						return false;
					}
					//////////////
				} else if (extraArguments[0].equalsIgnoreCase("unclaim")) {
					int playerChunkX = player.getLocation().getChunk().getX();
					int playerChunkZ = player.getLocation().getChunk().getZ();
					if (playerIsInAFaction(player.getName()) == true && getPlayerFaction(player.getName())
							.equals(plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
									.get(playerChunkX + "," + playerChunkZ)) == true) {
						if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
								.contains("Chunks") == true) {
							if (plugin.chunkSavesFile.getConfigurationSection(getPlayerFaction(player.getName()))
									.getStringList("Chunks").contains(playerChunkX + "," + playerChunkZ) == true) {
								if (plugin.chunkSavesFile.contains("ClaimedChunks")) {
									List<String> chunkList = plugin.chunkSavesFile
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getStringList("Chunks");
									List<String> officerList = plugin.getSecondConfig()
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getStringList("Officers");
									plugin.saveSecondConfig();
									plugin.saveChunkSavesFile();
									Boolean playerIsOfficer = officerList.contains(player.getName());
									Boolean playerIsLeader = plugin.getSecondConfig()
											.getConfigurationSection(getPlayerFaction(player.getName()))
											.getString("Owner").equals(sender.getName());
									if (playerIsOfficer == true || playerIsLeader == true) {
										chunkList.remove(playerChunkX + "," + playerChunkZ);
										plugin.chunkSavesFile
												.getConfigurationSection(getPlayerFaction(player.getName()))
												.set("Chunks", chunkList);
										plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
												.set(playerChunkX + "," + playerChunkZ, null);
										plugin.saveChunkSavesFile();
										player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You just unclaimed "
												+ ChatColor.DARK_AQUA + playerChunkX + " " + playerChunkZ);
										return true;

									} else {
										player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
												+ " You need to be officer/leader to do this!");
										return true;
									}
								} else {
									plugin.chunkSavesFile.createSection("ClaimedChunks");
									player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
									return true;
								}
							} else {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE
										+ " Your country does not own this land!");
								return true;
							}
						} else {
							player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_MISTAKE + " Your country does not own this land!");
						return true;
					}

				}

				else if (extraArguments[0].equalsIgnoreCase("create")) {
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						String Name = extraArguments[1];
						if (plugin.getSecondConfig().contains(Name)) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + " There is a country called "
									+ Name + " already!");
							return true;
						} else {
							if (plugin.getSecondConfig().contains("Citizens") == false) {
								plugin.getSecondConfig().createSection("Citizens");
							} else if (plugin.getSecondConfig().getConfigurationSection("Citizens")
									.contains(player.getName())) {
								player.sendMessage(
										StringConstants.MESSAGE_PREFIX_ERROR + " You are already a citizen of a country!");
								return true;
							}
							plugin.getSecondConfig().createSection(Name);
							plugin.getSecondConfig().getConfigurationSection(Name).set("Owner", player.getName());
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(), Name);
							plugin.getChunkSavesFile().createSection(Name).createSection("Chunks");
							ChunkManagement.saveChunkSavesFileConfiguration(plugin.chunkSavesFile,
									plugin.chunkSavesFileConfiguration);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK
									+ "You established a new country called " + ChatColor.GOLD + Name);
							return true;
						}

					}

				} else if (extraArguments[0].equalsIgnoreCase("invite")) {
					Player invitedPlayer = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String invitedPlayerName = invitedPlayer.getName();
					// String playerFaction =
					// plugin.getSecondConfig().getConfigurationSection("Citizens").getString(sender.getName());
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						if (playerHasBeenInvited(player.getName(), invitedPlayerName) == true) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + " That player has already been invited!");
							return true;
						} else if (playerIsInAFaction(invitedPlayerName) == true) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + " That player is already a citizen of a country!");
							return true;
						}
						inviteStore.put(player.getName(), invitedPlayerName);
						player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have invited " + invitedPlayerName
								+ " to " + getPlayerFaction(player.getName()));
						invitedPlayer.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have been invited to "
								+ getPlayerFaction(player.getName()) + "!");
						return true;
					}
				} else if (extraArguments[0].equalsIgnoreCase("join")) {
					if (extraArguments.length == 0) {
						sender.sendMessage("Not enough arguments");
						return false;
					} else {
						Player playerThatInvites = (Bukkit.getServer().getPlayer(extraArguments[1]));
						if (playerHasBeenInvited(playerThatInvites.getName(), player.getName()) == false) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR
									+ "You have not been invited to any countries!");
							return true;
						} else if (playerIsInAFaction(player.getName()) == true) {
							player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + " You are already in a country!");
							return true;
						} else if (playerHasBeenInvited(playerThatInvites.getName(), player.getName()) == true) {
							String factionPlayerHasBeenInvitedTo = plugin.getSecondConfig()
									.getConfigurationSection("Citizens").getString(playerThatInvites.getName());
							List<String> memberList = plugin.getSecondConfig()
									.getConfigurationSection(factionPlayerHasBeenInvitedTo).getStringList("Members");
							plugin.saveSecondConfig();
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(player.getName(),
									factionPlayerHasBeenInvitedTo);
							memberList.add(player.getName());
							plugin.getSecondConfig().getConfigurationSection(factionPlayerHasBeenInvitedTo)
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You have joined "
									+ factionPlayerHasBeenInvitedTo);
							inviteStore.remove(playerThatInvites.getName());
							return true;

						} else {
							return false;
						}

					}
				} else if (extraArguments[0].equalsIgnoreCase("cl")) {
					String playerFaction = plugin.getSecondConfig().getConfigurationSection("Citizens")
							.getString(sender.getName());
					List<String> chunkList = plugin.chunkSavesFile.getConfigurationSection(playerFaction)
							.getStringList("Chunks");
					player.sendMessage(ChatColor.GREEN + "=======================================");
					for (String s : chunkList)
						player.sendMessage(s);
					player.sendMessage(ChatColor.GREEN + "=======================================");
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("cc")) {
					int playerChunkX = player.getLocation().getChunk().getX();
					int playerChunkZ = player.getLocation().getChunk().getZ();
					player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + " You are standing on chunk " + playerChunkX
							+ " " + playerChunkZ);
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.GREEN + "=====================================================");
					player.sendMessage(ChatColor.GOLD + "cc: Short for check chunk. Usage: /gda cc");
					player.sendMessage(ChatColor.GOLD + "cl: Short for claim list. Usage: /gda cl");
					player.sendMessage(
							ChatColor.GOLD + "invite: Invites a player to your country. Usage: /gda invite (player)");
					player.sendMessage(
							ChatColor.GOLD + "create: Establishes a new country. Usage: /gda create (countryName)");
					player.sendMessage(ChatColor.GOLD
							+ "claim: Claims a chunk for your country. It costs 1000$ for one chunk. Usage: /gda claim");
					player.sendMessage(ChatColor.GOLD
							+ "leave: Use this command to leave your country. If you're the owner, the country is deleted. Usage: /gda leave");
					player.sendMessage(ChatColor.GOLD + "help2: Shows the next page. Usage: /gda help2");
					player.sendMessage(ChatColor.GOLD + "join: Accepts an invite. Usage: /gda join (player)");
					player.sendMessage(ChatColor.GREEN + "=====================================================");
					return true;
				} else if (extraArguments[0].equalsIgnoreCase("leave")) {
					if (playerIsInAFaction(player.getName()) == true) {
						String playerName = player.getName();
						List<String> memberList = plugin.getSecondConfig()
								.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Members");
						plugin.saveSecondConfig();
						List<String> chunkList = plugin.getChunkSavesFile()
								.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Chunks");
						if (plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
								.getString("Owner").equals(playerName)) {
							for (int i = 0; i < chunkList.size(); i++) {
								if (plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
										.getString(chunkList.get(i)).contains(getPlayerFaction(player.getName()))) {
									plugin.getChunkSavesFile().getConfigurationSection("ClaimedChunks")
											.set(chunkList.get(i), null);
									plugin.getChunkSavesFile().set(getPlayerFaction(player.getName()), null);
									plugin.saveChunkSavesFile();
									plugin.saveSecondConfig();
								} else {
									player.sendMessage(StringConstants.MESSAGE_GENERIC_ERROR);
								}
							}
							for (int i = 0; i < memberList.size(); i++) {
								plugin.getSecondConfig().getConfigurationSection("Citizens").set(memberList.get(i),
										null);
								plugin.saveSecondConfig();
							}
							if(getPlayerFaction(player.getName()) == null) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_MISTAKE + " You are not a citizen of any country!");
								return true;
							}
							plugin.getSecondConfig().set(getPlayerFaction(player.getName()), null);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
							plugin.saveSecondConfig();
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO
									+ getPlayerFaction(player.getName()) + " has been disbanded!");
							return true;

							// plugin.saveSecondConfig();
							// plugin.saveChunkSavesFile();
						} else {
							memberList.remove(playerName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerName, null);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You left "
									+ getPlayerFaction(player.getName()));
							return true;
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + "You are not a citizen of any country!");
						return true;
					}
				} else if (extraArguments[0].equalsIgnoreCase("help2")) {
					// TODO: Add help2 command
				} else if (extraArguments[0].equalsIgnoreCase("officer")) {
					String playerName = player.getName();
					Player playerToBeGrantedOfficerStatus = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String playerToBeGrantedOfficerStatusName = playerToBeGrantedOfficerStatus.getName();
					Boolean playerIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
							.equals(sender.getName());
					Boolean playersAreInSameFaction = getPlayerFaction(player.getName())
							.equals(getPlayerFaction(playerToBeGrantedOfficerStatusName));
					List<String> memberList = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Members");
					plugin.saveSecondConfig();
					if (playerIsLeader == true && playersAreInSameFaction == true) {
						List<String> officerList = plugin.getSecondConfig()
								.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Officers");
						plugin.saveSecondConfig();
						Boolean playerIsAlreadyOfficer = officerList.contains(playerToBeGrantedOfficerStatusName);
						if (playerIsAlreadyOfficer == true && playersAreInSameFaction == true) {
							officerList.remove(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							memberList.add(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You have unset "
									+ playerToBeGrantedOfficerStatusName + " as officer!");
							playerToBeGrantedOfficerStatus.sendMessage(StringConstants.MESSAGE_PREFIX_OK
									+ "You are no longer an officer of " + getPlayerFaction(player.getName()));
							return true;
						} else {
							officerList.add(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							memberList.remove(playerToBeGrantedOfficerStatusName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Members", memberList);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You have set "
									+ playerToBeGrantedOfficerStatusName + " as officer!");
							playerToBeGrantedOfficerStatus.sendMessage(StringConstants.MESSAGE_PREFIX_OK
									+ "You are now an officer of " + getPlayerFaction(player.getName()));
							return true;
						}
					} else {
						if (playerIsLeader == false) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + "You need to be the leader to do this!");
							return true;
						}
						if (playersAreInSameFaction == false) {
							player.sendMessage(
									StringConstants.MESSAGE_PREFIX_ERROR + "You are not in the same faction!");
							return true;
						}
					}

				} else if (extraArguments[0].equalsIgnoreCase("kick")) {
					String playerName = player.getName();
					Player playerBeingKicked = (Bukkit.getServer().getPlayer(extraArguments[1]));
					String playerBeingKickedName = playerBeingKicked.getName();
					List<String> officerList = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getStringList("Officers");
					plugin.saveSecondConfig();
					Boolean playerIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(player.getName())).getString("Owner")
							.equals(sender.getName());
					Boolean playersAreInSameFaction = getPlayerFaction(player.getName())
							.equals(getPlayerFaction(playerBeingKickedName));
					Boolean playerIsOfficer = officerList.contains(player.getName());
					Boolean playerBeingKickedIsOfficer = officerList.contains(playerBeingKickedName);
					Boolean playerBeingKickedIsLeader = plugin.getSecondConfig()
							.getConfigurationSection(getPlayerFaction(playerBeingKickedName)).getString("Owner")
							.equals(playerBeingKickedName);
					if (playerIsLeader == true || playerIsOfficer == true) {
						if (playerBeingKickedIsOfficer == true) {
							if (playerBeingKickedIsLeader == true) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
								return true;
							}
							officerList.remove(playerBeingKickedName);
							plugin.getSecondConfig().getConfigurationSection(getPlayerFaction(player.getName()))
									.set("Officers", officerList);
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName,
									null);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName
									+ " from the faction!");
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + playerName + " kicked "
									+ playerBeingKickedName + " from " + getPlayerFaction(player.getName())
									+ "! How evil!");
							return true;
						} else {
							if (playerBeingKickedIsLeader == true) {
								player.sendMessage(StringConstants.MESSAGE_PREFIX_ERROR + "You can't kick the leader!");
								return true;
							}
							plugin.getSecondConfig().getConfigurationSection("Citizens").set(playerBeingKickedName,
									null);
							plugin.saveSecondConfig();
							player.sendMessage(StringConstants.MESSAGE_PREFIX_OK + "You kicked " + playerBeingKickedName
									+ " from the faction!");
							Bukkit.broadcastMessage(StringConstants.MESSAGE_PREFIX_INFO + playerName + " kicked "
									+ playerBeingKickedName + " from " + getPlayerFaction(player.getName())
									+ "! How evil!");
							return true;
						}
					} else {
						player.sendMessage(
								StringConstants.MESSAGE_PREFIX_ERROR + "You need to be officer/leader to do this!");
					}
				} else if (extraArguments[0].equalsIgnoreCase("firstRun")) {
					// TODO: Add first run parameters
				}
			}
		} else {
			sender.sendMessage("That's invalid you inbred fuck!");
		}
		return false;
	}

}