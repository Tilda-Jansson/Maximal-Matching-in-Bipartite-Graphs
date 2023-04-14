import static java.lang.Math.min;
import java.util.*;

public class Combine {
	int s, t, maxFlow, v, x, y, e, maxMatch;
    int visitedToken = 1;
    int[] visited;
    static final long INF = Long.MAX_VALUE;
	public static int cap = 1; 
								
								
	Kattio io;
    List<Edge>[] graph;

	public Combine() throws Exception {
		io = new Kattio(System.in, System.out);
		read();
		getMaxFlow();
		write();
		io.close();
	}

	private void read() {
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();
		
		s = x + y + 1;
		t = s + 1;
		v = t;
		
        initializeEmptyFlowGraph();
        visited = new int[v+1];

		// Läs in kanterna
		for (int i = 0; i < e; i++) {
            int a = io.getInt(); // Från 
			int b = io.getInt(); // Till 
            addEdge(a, b, cap);
        } 

		// Lägger till kanter från källan till alla element i X
		for (int i = 0; i < x; i++) {
            addEdge(s, (i+1), cap);
		}
		// Lägger till kanter från alla element i Y till sänkan.
		for (int i = 0; i < y; i++) {
            addEdge(x+1+i, t, cap);
		}

	}

    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() { 
        graph = new List[v+1];
        for (int i = 0; i < v+1; i++){
            graph[i] = new ArrayList<Edge>();
        }
      }

    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

    public void addEdge(int from, int to, long capacity) {
        assert capacity >= 0;
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        graph[from].add(e1);
        graph[to].add(e2);
    }

    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }
    public void visit(int i) {
        visited[i] = visitedToken;
    }

	private void write() {
		io.println(x + " " + y);
        LinkedList<String> y = new LinkedList<String>(); // kanter i funna matchningen

        for (List<Edge> edges : graph){
            for (Edge e : edges){ 
                if(!((e.from == s || e.from == t) || (e.to == s || e.to == t)) && e.flow > 0){
                    y.add(e.from + " " + e.to);
                }

            }
        }
        io.println(y.size()); // antal kanter i matchning
		for (String s : y) {
			io.println(s);
		}

		io.flush();
	}


	public void getMaxFlow() {
        bfs();
        maxFlow = 0;
        for (Edge edge : graph[s]){
            maxFlow += edge.flow;
        }
    }  


	
	public void bfs() {
        while (true) {
            Edge[] prev = new Edge[v+1];
            Queue<Integer> Q = new ArrayDeque<>(v+1);
            Q.add(s);

            markAllNodesAsUnvisited();
            visit(s);

            boolean cantAugmentToSink = true;
            int current;

            while(!Q.isEmpty()) { 
                current = Q.peek();
                if (current == t) { //We have reached the sink and found an augmenting path
                    cantAugmentToSink = false;
                    break;
                }
                Q.remove();

                for (Edge edge : graph[current]) {
                    long cap = edge.remainingCapacity();
                    if (cap > 0 && !visited(edge.to)) {
                      visit(edge.to);
                      prev[edge.to] = edge;
                      Q.add(edge.to);
                    }
                }
                
            }

            if (cantAugmentToSink) {// If we can't find an augmenting path to the sink, we have found maxflow and cant further update flow
                return;
            }


            long bottleNeck = INF;
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]){
              bottleNeck = min(bottleNeck, edge.remainingCapacity());
            }

            // Retrace augmented path and update flow values.
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]){
                edge.augment(bottleNeck);
              }
        }

    } 

	public static void main(String[] args) throws Exception {
		new Combine();
	}

    private static class Edge {
        public int from, to;
        public Edge residual;
        public long flow; 
        public final long capacity;
    
        public Edge(int from, int to, long capacity) {
          this.from = from;
          this.to = to;
          this.capacity = capacity;
        }
    
        public boolean isResidual() {
          return capacity == 0;
        }
    
        public long remainingCapacity() {
          return capacity - flow;
        }
    
        public void augment(long bottleNeck) {
          flow += bottleNeck;
          residual.flow -= bottleNeck;
        }
    
    }

	
		
}