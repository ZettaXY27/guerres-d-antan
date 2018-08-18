package gda2;

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
	private Rank rankPositionWithinFaction;
	
	public Member() {
		//Empty constructor
	}
	
	public Member(String name, Rank rankPositionWithinFaction) {
		this.name = name;
		this.rankPositionWithinFaction = rankPositionWithinFaction;
	}
	
}
