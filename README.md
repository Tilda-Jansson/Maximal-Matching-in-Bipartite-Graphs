# Maximal Matching in Bipartite Graphs

This repository contains a program that takes a bipartite graph as input and produces a maximum size matching as output by reducing the matching problem to the flow problem. The solution has been implemented in three steps, as described below.

## Steps

1. [**Reduce the problem to the flow problem**](reducetoflow/BipRed.java): A program has been implemented that solves the matching problem using a black box function that solves the flow problem. [Kattis](oldkattis.adkreducetoflow)

2. [**Solve the flow problem**](maxflow/Flow2.java): A program has been implemented that solves the flow problem. The program reads input data from standard input and writes the solution to standard output. [Kattis](https://kth.kattis.com/problems/oldkattis.adkmaxflow)

3. [**Combined steps 1 & 2**](bipmatch/Combine.java): The solutions from steps 1 and 2 have been combined into a single program by replacing the communication of the flow instance over standard input and standard output with a function call. The program still reads input data from standard input and writes the solution to standard output. [Kattis](https://kth.kattis.com/problems/oldkattis.adkbipmatch)


The program has been designed to solve the problem efficiently. It has been tested on bipartite graphs with up to (5000+5000) vertices and up to 10,000 edges.


## Matching Problem

**Goal**: Given a bipartite graph G = (X,Y,E), find a maximum matching.

### Input:

* The first line consists of two integers indicating the number of vertices in X and Y, respectively.
* The second line consists of a number indicating |E|, i.e., the number of edges in the graph.
* The following |E| lines each consist of two integers corresponding to an edge.

Vertices are numbered from 1 and upwards. If there are a vertices in X and b vertices in Y, we let X = {1, 2,..., a} and Y = {a+1, a+2,..., a+b}. An edge is specified by its endpoints (first the X vertex and then the Y vertex).

### Output:

* The first line is the same as the first line in the input.
* The second line contains an integer indicating the number of edges in the found matching.
* After that, write one line for each edge that is part of the matching. The edge is described by a pair of numbers in the same way as in the input.



## Flow Problem

**Goal**: Given a flow graph G = (V,E), find a maximum flow. Solve the flow problem using Edmonds-Karp algorithm, i.e., Ford-Fulkerson's algorithm where the shortest path is found using breadth-first search.

### Input:

* The first line consists of an integer indicating the number of vertices in V.
* The second line consists of two integers, s and t, indicating which vertices are the source and sink, respectively.
* The third line consists of a number indicating |E|, i.e., the number of edges in the graph.
* The following |E| lines each consist of three integers corresponding to an edge.

Vertices are numbered from 1 and upwards. If there are a vertices in V, we let V = {1, 2,..., a}. An edge is specified by its endpoints (first the from-vertex and then the to-vertex) followed by its capacity.

### Output:

* The first line consists of an integer indicating the number of vertices in V.
* The second line consists of three integers, s, t, and the flow from s to t.
* The third line consists of an integer indicating the number of edges with positive flow.
* After that, write one line for each such edge. The edge is described by three numbers in a similar way as in the input, but instead of capacity, we now have flow.
