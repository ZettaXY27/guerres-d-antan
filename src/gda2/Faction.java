package gda2;

import java.util.ArrayList;
import java.util.UUID;

public class Faction {
	// the name of the faction
	private String name = "";
	private String description = "";
	//private ArrayList<Member> memberArray;
	
	// The default rank in the faction
	private Rank defaultRank = Rank.CITIZEN;
	
	
	/*
	 * Alternatively implement diplomacy in the form of 
	 * hashsets or w/e
	 */
	private ArrayList<Faction> listOfEnemies;
	
	private ArrayList<Faction> listOfAllies;
	
	/** @deprecated */
	private ArrayList<Member> memberList;
	/** @deprecated List of individuals who are NOT members but have visas in the faction */
	private ArrayList<Member> visaList;
	
	private Main mainClass;
	
	/** Proper member list of type UUID. Convert to string when saving */
	private ArrayList<UUID> peopleWhoAreMembersList;
	private ArrayList<UUID> peopleWithVisasList;
	/**
	 * Default constructor that actually does nothing
	 */
	public Faction() {
		
	}
	
	/**
	 * Creates a faction of the specified name and then 
	 * @param factionName the name of the faction as a string
	 * @param uuid the UUID of the creator of the faction
	 * 
	 */
	public Faction(String factionName, UUID uuid) {
		if(this.memberList == null) {
			this.memberList = new ArrayList<Member>();
		}
		
		this.memberList.add(new Member(uuid, Rank.LEADER));
		
		this.name = factionName;
	}
	
	
	
	/**
	 * Sets the default rank within the faction
	 * @param defaultRank the desired default rank within the faction
	 */
	public void setDefaultRank(Rank defaultRank) {
		this.defaultRank = defaultRank;
	}
	
	/**
	 * Gets the default rank within the faction
	 * @return Rank enumerated representative of the default rank in the faction
	 */
	public Rank getDefaultRank() {
		return this.defaultRank;
	}
	
	/**
	 * Adds a new member to the faction
	 * @param uuid unique user id
	 * @param rank Enumerated Rank
	 */
	public void addMemberToMemberList(UUID uuid, Rank rank) {
		this.memberList.add(new Member(uuid,rank));
	}
	
	/**
	 * Adds a new member to the member list and sets rank as default
	 * @param uuid
	 */
	public void addMemberToMemberList(UUID uuid) {
		this.memberList.add(new Member(uuid,this.defaultRank));
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
	
	/**
	 * Retrieves the description of the faction
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the description of the faction
	 * @param description the description of the faction
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
