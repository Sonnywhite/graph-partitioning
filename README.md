# graph-partitioning

<b>Usage</b> (auf anubis.informatik.uni-halle.de):

* in den <code>src</code>-Ordner navigieren
* <b>kompilieren</b> mit <code>javac -classpath commons-cli-1.3.1.jar:. main/Main.java</code> <br><i>(die Bibliothek muss mit angegeben werden, sie dient aber lediglich für das verbesserte Parameter Handling beim Starten des Programms, außerdem ist darauf zu achten, dass nach der Angabe der Bibliothek auch das eigentliche Verzeichnis mit <code>:.</code> nicht fehlt)</i>
* <b>ausführen</b> mit <code>java -cp commons-cli-1.3.1.jar:. main.Main</code><i>(lässt das Programm mit einem Testgraph laufen und gibt die gemessenen Zeiten aus)</i>
 * um mehr beim Ausführen zu erfahren kann man das Programm wie folgt aufrufen: <br><code>java -cp commons-cli-1.3.1.jar:. main.Main -v</code> 
 * um einen eigenen Testgraphen anzugeben, kann man das Programm wie folgt auffrufen: <br><code>java -cp commons-cli-1.3.1.jar:. main.Main -i \<Pfad-zum-Testgraph.graph\></code> <i>(dabei ist es wichtig, dass der Graph im <code>.graph</code>-Format ist und sich auch an den Standard hält)</i>
 * mit den Eingabe-Parametern ist noch viel mehr möglich, um eine Übersicht über alle Parameter zu erhalten genügt es das Programm mit <code>java -cp commons-cli-1.3.1.jar:. main.Main -h</code> zu starten

<b>Raw</b> (für die Abgabe der Übungsserie:)
Algorithm Engineering - Graph Partitioning

Usage (auf anubis.informatik.uni-halle.de):

* in den src-Ordner navigieren
* kompilieren mit "javac -classpath commons-cli-1.3.1.jar:. main/Main.java" 
  (die Bibliothek muss mit angegeben werden, sie dient aber lediglich für das verbesserte Parameter Handling beim Starten des Programms, 
  außerdem ist darauf zu achten, dass nach der Angabe der Bibliothek auch das eigentliche Verzeichnis mit ":." nicht fehlt)
* ausführen mit "java -cp commons-cli-1.3.1.jar:. main.Main"(lässt das Programm mit einem Testgraph laufen und gibt die gemessenen Zeiten aus)
 * um mehr beim Ausführen zu erfahren kann man das Programm wie folgt aufrufen: 
   "java -cp commons-cli-1.3.1.jar:. main.Main -v" 
 * um einen eigenen Testgraphen anzugeben, kann man das Programm wie folgt auffrufen: 
   "java -cp commons-cli-1.3.1.jar:. main.Main -i <Pfad-zum-Testgraph.graph>" 
   (dabei ist es wichtig, dass der Graph im ".graph"-Format ist und sich auch an den Standard hält)
 * mit den Eingabe-Parametern ist noch viel mehr möglich, um eine Übersicht über alle Parameter zu erhalten genügt es das Programm mit 
   "java -cp commons-cli-1.3.1.jar:. main.Main -h" zu starten
