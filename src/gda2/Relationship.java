package gda2;

/**
 * 
 * @author Inivican
 *
 */
public enum Relationship {
	NEUTRAL,
	ENEMY,
	ALLY,
	TERRITORY,//UNMANAGED TERRITORY, IS NOT A FACTION OF ITS OWN
	VASSAL	  //MANAGED TERRITORY, IS A FACTION OF ITS OWN BUT IS BOUND
}
