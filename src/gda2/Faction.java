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
	
	
	/**
	 * Default constructor
	 */
	public Faction() {
		
	}
	
	public Faction(String factionName, UUID uuid) {
		if(this.memberList == null) {
			this.memberList = new ArrayList<Member>();
		}
		this.memberList.add(new Member(uuid, Rank.EXECUTIVE));
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
}
