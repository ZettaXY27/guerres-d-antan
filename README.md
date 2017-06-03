--Guerres D'antan--

Overview: A more engaging, long-term and large scale factions-type plugin to encourage
large nations and to make wars both more costly and more fun for flan's mod.

 -Features-
Large Territory: Factions (which claim land indefinitely for a certain sum of money per chunk) can now
claim entire country-sized portions of land assuming they have the financial power to do so.

Borders: Nations, once they reach a certain size (or maybe fulfill some command-related action that has 
yet to be decided) become Nation-States with borders impenetrable to outsiders unless they are allies
given acces permission, or they are enemies who've just invaded territory. Nation-State entry perms
should also be given to outsiders and revoked through a command, like /visa yes ______ and /visa no ____.
When outsiders without nation-wide or individual permission approach a claimed non-accessible chunk, there
should be a warning. ''You are nearing ______'s borders! Turn back!''. The proximity message is important
because--like a world border--the nation's border would teleport attempted infiltrators near their point
of attempted entry, just beyond the claimed chunk, when hit. The proximity warning would need to be far-
reaching enough to warn airborne players to turn back before they lose their planes, while also being
short enough to not cause a massive headache when flying over relatively populated land-areas.

Invasions: To enter enemy territory (you never really enter it, all you do is eventually take their land)
one must look at a chunk of enemy territory and perform a command action, like /invade or something. You
cannot place or use any items while in enemy territory, nor may you set home or teleport. Claiming enemy
chunks takes lots of time. This time increases if you're claiming a chunk unadjacent to anything you own.
Also, the chunks can only be overtaken there are online members of the Nation whose chunks you are invading.
The claiming process is a slow 1% to 100% process in chat. It is undone when the claimer dies, and reset to
zero. The claimer must also be straight up looking at the chunk he is trying to claim or something of the
sort.

Domestics: Nations may have up an infinite, server-configurable amount of internal subfactions whose 
permissions are variable and configured by the nation's leader. These can also be enforced on a chunk-by-
chunk basis. Example: Country X has a city called Prime City. It could set up a sub-faction for Prime City,
and make it so PrimeCitians can access chests and interactive blocks within Prime City, while foreigners
and Country X citizens from outside the city subfaction cannot. The Nation head can also set taxes, and I'm
not sure as to whether the taxes should be in item form (I can make items for flan's mod relatively easily,
like the illuminati currencies and other aesthetic items I textured for Labjac's monorisu server) to be 
traded in with buyable bank NPCs or if they should be automated and tied in with fé economy.

National Pride/Morale: Morale is important because, if lost en-masse, it takes longer to claim, to respawn,
perhaps even to teleport (but hopefully there won't be any teleports). Morale is lost when players die, and
it regenerates slightly over time. Initially, I thought of making it regen through kills, land claims and
time, but I preffered the latter since there's no way to scam time, but it is possible to kill alts or claim
empty plots of land until morale is restored if that was the case. I'm not sure how morale should be stocked,
but it shouldn't motivate people to bring in alts.

International Relations: There are different types of international relationships. Alliances (traditional
best-friend), Trade-Agreements (Show the world you trade?), Defense-Groups (NATO, MRC), Non-
Aggression Pacts (formal neutrality) and Vassals-Protectorates (Subordinate Allies). These relationships--
granted they don't overlap--should stack. Some are multilateral, meaning more than one nation could be a 
part of the relationship (Like NATO for defense groups or the EU for trade groups) ww2 Ex: GERMANY allies
JAPAN and ITALY and ROMANIA. It then vassals ROMANIA, AUSTRIA, and FRANCE (so if you do /gda n germany or 
something, you would see Germany's profile as the following)

--GERMANY--
Motto: (Player-set)
Established: xxxxx
Government: (Player-Set through command, purely aesthetic)
Economy: ('' '')
Population: ####
Total Area: (# of chunks)
Morale: (1-100 or some shit, no idea.)

and if you do /gda ir germany or something (ir for international relations)

--GERMANY--
Motto: (.........)
Allies: JAPAN, ITALY
Truced: AZAD-HIND
Trade Partners: ITALY
Suzerains/Protectors: NONE
Vassals/Protectorates: ROMANIA, FRANCE, AUSTRIA
Non-Aggression: USSR

and if you do /gda ir romania

--ROMANIA--
Motto: (.........)
Allies: NONE
Truced: ITALY
Trade Partners: NONE
Suzerains/Protectors: GERMANY
Vassals/Protectorates: NONE
Non-Aggression: USSR

After further reflection, I realize that an EU-like or NATO-like group initiative could just be a faction
allied or truced to all of its respective memberstates. It does not require further coding or anything.

Final Overview: War can only be declared and can only take place when both sides are online. This allows
for greater creativity when paired up with increased security from the borders aspect, sincep players are
less prone to random savage attack. It also enhances gameplay quality, adding both enjoyable simplicity
and meaningful depth.

Final Messages:
-You can't insta-leave a nation, there needs to be some kind of timer to prevent combat /homing.
-How should Morale be counted?
-Internal taxation seems like an issue, if too complicated I could do it server-side with NPCs.
-I plan on pairing this mod with two different enhanced village mods that add player-made towns/npcs
and naturally-spawning cultural NPCs.
-There needs to be an autoclaim land feature like in Factions, solely for the claiming of fresh
wilderness.
-No jetpacks, so take that into consideration if you want to rework the border warning thing and how it
detects illegal borderhoppers
-Nation names need a configurable length and they cannot use numbers.
-The subfactions thing is hard, but if it works you could have like police forces or some shit.
-The money it costs to buy land could be configured to use the same item id as the money obtained
from taxes if any. Taxes are VERY important.
-The Suzerains/Protectors & Vassals/Protectorates thing seems complicated, but it's also important.
-Civil Wars need to be possible, any ideas? 
-Big countries like Canada and Russia could be really hard to invade, which is the point.
-Should Nations have a ''Capital Chunk'' with some block in it that must be busted by enemies to undo
the faction? That would be a very cool element, a hard shut down to any fac if nukes don't work. 
  
   
