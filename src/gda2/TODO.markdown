# TODO LIST:

* Implement playerStrorage
	
* Fix isPlayerInFaction() in FactionControlExecutor
	Remove ArrayList of Members in Faction class
	Implement /gda desc command


* A player/member has the following characteristics

	
* Unique User ID (UUID) (This is what each section starts with in playerStorage)
	Rank (of Enumerated type Rank)
	Aesthetic Title


* Information about the player should be stored in playerStorage.yml


* The only information about players that should be stored in factionStorage.yml will be UUIDs under the Members section. Please DO NOT create a separate leaders section. 


* Implement visa system.


* Within factionStorage, there is to be a section called 'Visas'
	Under that section the UUIDs of all the players who have visas in that faction should be stored


* Implement declare war/peace functionality	


* Implement vassals


* Implement Subfactions/landgroups


- dunclecake was really big on this.
	 It is essentially a means of organizing claimed chunks into easier-to-organize groups.

