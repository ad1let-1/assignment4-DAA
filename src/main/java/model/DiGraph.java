package model;

import java.util.ArrayList;
import java.util.List;

public class DiGraph {
    public final int n;                 // number of vertices
    public final List<List<Edge>> adj;  // adjacency list

    public DiGraph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
    }

    public List<Edge> edgesFrom(int u) {
        return adj.get(u);
    }
}
