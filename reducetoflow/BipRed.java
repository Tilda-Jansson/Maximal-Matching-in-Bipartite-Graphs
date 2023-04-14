
import java.awt.Point;

public class BipRed {
    Kattio io;
    Point[] edges;
	Point[] matchedEdges;
	int maxMatch;
	int x, y, e;
	
    void readBipartiteGraph() {
	    // Läs antal hörn och kanter
	    x = io.getInt(); //antal punkter i X mängden
	    y = io.getInt();  // antal i Y mängden
	    e = io.getInt();
		edges = new Point[e]; // KantLista (från X, till Y)
	
	    // Läs in kanterna
	    for (int i = 0; i < e; ++i) {
	        int a = io.getInt();
	        int b = io.getInt();
			edges[i] = new Point(a,b);
	    }
    }
    
    
    void writeFlowGraph() {
	    //int v = 23, e = 0, s = 1, t = 2;
		int s = x+y+1; // source
		int t =  s+ 1; //sink
	    
		// Skriv ut antal hörn och kanter samt källa och sänka
	    io.println(x + y + 2);
	    io.println(s + " " + t);
	    io.println(e + x + y); //Totala antalet kanter

	    for (int i = 0; i < e; ++i) {
	        // Kant från a till b med kapacitet 1
	        io.println(edges[i].x + " " + edges[i].y + " " + 1);
	    }
        // alla kanter från källan
		for (int i = 0; i < x; ++i) {
			io.println(s + " " + (i+1) + " " + 1);
		}
		// alla kanter till sänkan
		for (int i = 0; i < y; ++i) {
			io.println( (x+1+i) + " " + t + " " + 1);
		}
	    // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
	    io.flush();
	
	    // Debugutskrift
	    System.err.println("Skickade iväg flödesgrafen");
    }
    
    
    void readMaxFlowSolution() {
	    // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
	    // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
	    // skickade iväg)
	    int v = io.getInt();
	    int s = io.getInt();
	    int t = io.getInt();
	    int totflow = io.getInt();
	    int e = io.getInt();

	    maxMatch = 0;
	    matchedEdges = new Point[e];
	
	    for (int i = 0; i < e; ++i) {
	        // Flöde f från a till b
	        int a = io.getInt();
	        int b = io.getInt();
	        int f = io.getInt();

		    if(!((a==s || a == t) || (b == s || b == t)) && f > 0){ // f>0 not necessary
			    matchedEdges[maxMatch] = new Point(a,b);
			    maxMatch++;
	    	}
	    }
    }
    
    
    void writeBipMatchSolution() {
    	// Skriv ut antal hörn och storleken på matchningen
	    io.println(x + " " + y);
	    io.println(maxMatch);
	
	    for (int i = 0; i < maxMatch; ++i) {
	        // Kant mellan a och b ingår i vår matchningslösning
	        io.println(matchedEdges[i].x + " " + matchedEdges[i].y);
	    }
	
    }
    
    BipRed() {
	    io = new Kattio(System.in, System.out);
	
	    readBipartiteGraph();
	
	    writeFlowGraph();
	
	    readMaxFlowSolution();
	
	    writeBipMatchSolution();

	    // debugutskrift
	    System.err.println("Bipred avslutar\n");

	    // Kom ihåg att stänga ner Kattio-klassen
	    io.close();
    }
    
    public static void main(String args[]) {
	    new BipRed();
    }
}

