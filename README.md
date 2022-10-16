# Project Databases
## Deel 1: Domein model
Als eerste beginnen we met de uitleg over de blokken van entiteiten die we gebruikt hebben met hun eigenschappen. Daarna volgen de relaties die we gelegd hebben. Bij beide worden ook de gemaakte keuzes uitgelegd. 

Voor dit project beginnen we vanaf het blok "Wedstrijd" om zo het domein model van de database te ontwerpen. Een wedstrijd heeft een bepaalde ID (WedstrijdId), een TotaleAfstand (som van de afstanden van de etappes waaruit de wedstrijd bestaat) en een AantalEtappes (zodat we weten uit hoeveel etappes deze wedstrijd bestaat). Voor de wedstrijdId kiezen we een integer, omdat we er mogelijks bewerkingen op gaan uitvoeren, zoals bijvoorbeeld een autoincrement, waarbij de ID met 1 verhoogd wordt. Voor TotaleAfstand gebruiken we ook een integer, omdat we er waarschijnlijk berekeningen mee zullen uitvoeren. Indien een loper bijvoorbeeld zijn gemiddelde snelheid wilt opvragen, zullen we TotaleAfstand moeten delen door zijn totale tijd. Het evenement organiseren kost geld maar levert ook geld op. Voor InschrijvingsGeld gebruiken we een integer, omdat we er mogelijks mee gaan rekenen. De kosten gaan we hier niet vermelden, maar wel het loon binnen "Personeel".

Een loopwedstrijd bevat natuurlijk lopers dus dit is een logische volgende blok van entiteiten. Een loper heeft een rugnummer (integer voor als we de volgende loper willen opvragen en rugnummer dus met 1 vermeerderen). Dit rugnummer gebruiken we ook als uniek ID. De loper heeft ook nog een leeftijd en gewicht. Voor beide kiezen we een string, omdat we niet verwachten dat we er berekeningen mee zullen uitvoeren. Voor elke loper wordt de TotaleLooptijd en TotaleAfstand bijgehouden, beide als integer omdat er berekeningen mee gemaakt zullen worden. De WedstrijdId is opgeslagen als integer en verwijst naar een specifieke wedstrijd.

Bij het organiseren van één of meerdere loopwedstrijden zijn er heel wat mensen die deze evenementen mogelijk maken. We hebben besloten deze mensen allemaal de bundelen onder "Personeel". Bij het personeel wordt een PersoneelId bijgehouden als integer. De naam en functie worden beide opgeslagen als string. Als laatste wordt ook nog de WedstrijdId bijgehouden waar ze actief zijn. Daarbij wordt er wel nog onderscheid gemaakt tussen het personeel indien iemand werkt als vrijwilliger, en wat de betaalde mensen verdienen (dus wat de kosten zijn voor het evenement). Hiervoor hebben we een boolean "Vrijwilliger" gemaakt. Indien de boolean gelijk is aan 1, is de persoon een vrijwilliger. Daarbij is er ook nog een loon opgeslagen onder de vorm van een integer, omdat er mogelijks berekeningen mee zullen uitgevoerd worden. 

Een etappe heeft een EtappeId (als integer) en een EtappeNummer (als string). De BeginLocatie en EindLocatie worden opgeslagen als string. De EtappeAfstand wordt bijgehouden als integer, omdat er berekeningen mee uitgevoerd zullen worden. Een etappe hoort bij een bepaalde wedstrijd, dus heeft ook een WedstrijdId. Daarnaast worden ook nog het Rugnummer en Etappelooptijd bijgehouden als integer. We hebben ervoor gekozen om in "Etappe" zowel de loopgegevens van de loper bij te houden in deze specifieke etappe, als algemene gegevens over de gelopen etappe. 

We maken een onderscheid tussen het algemeen klassement en het klassement per wedstrijd. In "Algemeen klassement" worden enkel de rugnummers van de lopers opgeslagen en hadden we het idee om zo de TotaleLooptijd van de lopers te vergelijken en te sorteren. De lopers met de laagste TotaleLooptijd hebben het snelste gelopen (indien ze dezelfde afstand gelopen hebben) en komen dus hoger in het klassement te staan. De voorwaarde om als loper in het algemeen klassement te komen, is dat de loper aan alle wedstrijden heeft deelgenomen. 

Het eerste verband dat we gelegd hebben is tussen de wedstrijd en loper. Een wedstrijd bevat 1 of meerdere lopers (we gaan ervan uit dat als er geen lopers mee zouden doen, dat de wedstrijd ook niet door zou gaan). Elke loper loopt 1 of meerdere wedstrijden (hier gaan we er ook vanuit dat een loper niet 0 wedstrijden loopt). 

Een wedstrijd kan bestaan uit meerdere delen en kan 1 of meerdere etappes bevatten. Omgekeerd hoort elke etappe bij 1 wedstrijd. 

Voor de loper hebben we de keuze gemaakt om zijn loopgegevens voor een specifieke etappe op te slaan in "Etappe". Een loper kan 1 of meerder etappes lopen en dus meerdere keren zijn unieke gegevens opslaan in "Etappe". Omgekeerd is "Etappe" specifiek per loper dus hoort bij 1 loper. 

Een loper heeft 1 plaats in zowel het algemeen klassement als het klassement per wedstrijd. Omgekeerd bevatten beide 1 of meerdere lopers. 

Een wedstrijd bevat 1 klassement (per wedstrijd) en dat klassement (per wedstrijd) heeft ook maar 1 wedstrijd. 

Een wedstrijd bevat 1 of meerdere medewerkers, vrijwilligers of personeel. We gaan ervan uit dat de wedstrijden niet kunnen plaatsvinden indien er geen personeel of vrijwilligers zijn. Omgekeerd kan het personeel werken bij 1 of meer wedstrijden. 

De minimumvereisten zijn minstens 2 veel-op-veel relaties, minstens 2 1-op-veel of 0-op-veel relaties en minstens 4 blokken van entiteiten/modellen. 

Voorbeelden van veel-op-veel relaties zijn de relaties tussen de loper en de wedstrijden en tussen het personeel en de wedstrijden. 

Voorbeelden van 1-op-veel relaties zijn de relaties tussen het algemeen klassement en de lopers en tussen de wedstrijden en de etappes.

Aan de laatste voorwaarde voor minstens 4 blokken van entiteiten is ook voldaan, aangezien er 6 blokken van entiteiten zijn.