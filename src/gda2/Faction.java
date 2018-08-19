package gda2;

import java.util.ArrayList;

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
	
	
	/**
	 * Default constructor
	 */
	public Faction() {
		
	}
	
	/**
	 * Creates the faction
	 * @param factionName the name of the faction
	 * @param userName the user's name of the player that created the faction.
	 */
	public Faction(String factionName, String userName) {
		this.memberList = new ArrayList<Member>();
		addMemberToMemberList(userName, Rank.EXECUTIVE);
	}
	
	public void addMemberToMemberList(String userName) {
		this.memberList.add(new Member(userName, Rank.CITIZEN));
	}
	
	public void addMemberToMemberList(String userName, Rank rank) {
		this.memberList.add(new Member(userName, rank));
	}
	
	/**
	 * Adds an entire list of members to the faction list. Maybe useful if there
	 * is a case where a faction annexes another faction.
	 * @param memberList
	 */
	public void addMembersToList(ArrayList<Member> memberList) {
		this.memberList.addAll(memberList);
	}
}
