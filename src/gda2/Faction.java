package gda2;

import java.util.ArrayList;
import java.util.UUID;

public class Faction {
	private String name = "";
	//private ArrayList<Member> memberArray;
	
	
	/*
	 * Alternatively implement diplomacy in the form of 
	 * hashsets or w/e
	 */
	private ArrayList<Faction> listOfEnemies;
	private ArrayList<Faction> listOfAllies;
	
	private ArrayList<Member> memberList;
	
	private Main mainClass;
	
	
	/**
	 * Default constructor that actually does nothing
	 */
	public Faction() {
		
	}
	
	/**
	 * Creates a faction of the specified name and then 
	 * @param factionName the name of the faction as a string
	 * @param uuid the UUID of the creator of the faction
	 */
	public Faction(String factionName, UUID uuid) {
		if(this.memberList == null) {
			this.memberList = new ArrayList<Member>();
		}
		this.memberList.add(new Member(uuid, Rank.LEADER));
		this.name = factionName;
	}
	
	
	
	
	public void addMemberToMemberList(UUID uuid, Rank rank) {
		this.memberList.add(new Member(uuid,rank));
	}
	
	/**
	 * Adds an entire list of members to the faction list. Maybe useful if there
	 * is a case where a faction annexes another faction.
	 * @param memberList
	 */
	public void addMembersToList(ArrayList<Member> memberList) {
		this.memberList.addAll(memberList);
	}
	
	public String getFactionName() {
		return this.name;
	}
	
	public ArrayList<Member> getMemberList() {
		return this.memberList;
	}
	
	/**
	 * Gets all the members of a specific rank
	 * @param rank the rank of your choice
	 * @return an ArrayList of type rank containing members of that rank
	 */
	public ArrayList<Member> getMembersByRank(Rank rank) {
		ArrayList<Member> membersOfSpecificRank = new ArrayList<Member>();
		for(int i = 0 ; i < this.memberList.size(); i++) {
			if(this.memberList.get(i).getRank() == rank) {
				membersOfSpecificRank.add(this.memberList.get(i));
			}
		}
		return membersOfSpecificRank;
	}
	
	/**
	 * Gets all the members that have the same aestheticTitle
	 * @param aestheticTitle a string title that is fancy
	 * @return an ArrayList of type rank containing members of that aesthetic/vanity title
	 */
	public ArrayList<Member> getMembersByAestheticTitle(String aestheticTitle) {
		ArrayList<Member> membersOfSpecificTitle = new ArrayList<Member>();
		for(int i = 0; i < this.memberList.size(); i++) {
			if(this.memberList.get(i).getAestheticTitle() == aestheticTitle) {
				membersOfSpecificTitle.add(this.memberList.get(i));
			}
		}
		return membersOfSpecificTitle;
	}
}
