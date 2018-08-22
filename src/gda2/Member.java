package gda2;

import java.util.UUID;

/**
 * 
 * @author Inivican
 * @author ZettaX
 *
 */
/* Defines the structure of a member of any given faction, their position
 * and ranking within the faction.
 */
public class Member {
	//string name of the given player
	private String name = "";
	private UUID uuid;
	private Rank rank;
	private String aestheticTitle = "";
	
	public Member() {
		//Empty constructor
	}
	
	/**
	 * Instantiates Member
	 * @param uuid
	 * @param rank
	 */
	public Member(UUID uuid, Rank rank) {
		this.uuid = uuid;
		this.rank = rank;
	}
	
	public void setAestheticTitle(String aestheticTitle) {
		this.aestheticTitle = aestheticTitle;
	}
	
	/**
	 * gets the aesthetic title of the member
	 * @return
	 */
	public String getAestheticTitle() {
		return this.aestheticTitle;
	}
	
	/**
	 * Sets the rank of the member
	 * @param rank the rank of the player as an enumeration
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	
	/**
	 * gets the rank of the member
	 * @return the rank as an enumerated type called Rank
	 */
	public Rank getRank() {
		return this.rank;
	}
	
}
