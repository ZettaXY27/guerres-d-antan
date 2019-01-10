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

	@Override
	/**
	 * Returns formatted string containing information about a given MEMBER
	 */
	public String toString() {
		return "Member [uuid=" + uuid + ", rank=" + rank + ", aestheticTitle=" + aestheticTitle + "]";
	}

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
	public Member(UUID uuid, Rank rank) {//
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
	 * get player UUID
	 * @return player UUID
	 */
	public UUID getUUID() {
		return this.uuid;
	}
	
	/**
	 * Sets the UUID of the member
	 * @param uuid the unique user ID
	 */
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * gets the rank of the member
	 * @return the rank as an enumerated type called Rank
	 */
	public Rank getRank() {
		return this.rank;
	}
	
}
