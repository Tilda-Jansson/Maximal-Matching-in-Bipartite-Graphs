import static java.lang.Math.min;
import java.util.LinkedList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Hashtable;
import java.util.*;

public class Flow2 {
    Kattio io;
    int v, s, t, e;
    long maxFlow;
    static final long INF = Long.MAX_VALUE;

    int visitedToken = 1;
    int[] visited;

    // The adjacency list representing the flow graph.
    List<Edge>[] graph;

    public Flow2() throws Exception {
        io = new Kattio(System.in, System.out);
        
        readFlow(); 
        
        getMaxFlow(); 
        
        writeMaxFlow();
        
        io.close();
    }

 
    private void readFlow() {
        v = io.getInt(); // number of vertices in V
		s = io.getInt(); // source s
		t = io.getInt(); // sink t
		e = io.getInt(); // number of edges |E|

        initializeEmptyFlowGraph();
        visited = new int[v+1];
        
        
        for (int i = 0; i < e; i++) {
            int a = io.getInt(); // Från 
			int b = io.getInt(); // Till 
			int cap = io.getInt(); // Kapacitet


            addEdge(a, b, cap);
            } 

    }



    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() { 
        graph = new List[v+1];
        for (int i = 0; i < v+1; i++){
            graph[i] = new ArrayList<Edge>();
        }
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
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]){ // find bottleneck value
                bottleNeck = min(bottleNeck, edge.remainingCapacity());
            }

            // Retrace augmented path and update flow values.
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]){
                edge.augment(bottleNeck);
              }
        }

    } 


    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

    private void writeMaxFlow() throws Exception {
        io.println(v);
        io.println(s + " " + t + " " + maxFlow);
        LinkedList<String> y = new LinkedList<String>();
        

        for (List<Edge> edges : graph){
            for (Edge e : edges){ 
                if(e.flow > 0){
                    y.add(e.from + " " + e.to + " " + e.flow);
                }

            }
        } 
        io.println(y.size());
		for (String s : y) {
			io.println(s);
		}
		io.flush();

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


    public void visit(int i) {
        visited[i] = visitedToken;
    }

    public boolean visited(int i) {
        return visited[i] == visitedToken;
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
    
        public long remainingCapacity() {
          return capacity - flow;
        }
    
        public void augment(long bottleNeck) {
          flow += bottleNeck;
          residual.flow -= bottleNeck;
        }
    
    }

    public static void main(String[] args) throws Exception {
        new Flow2();
    }
}
