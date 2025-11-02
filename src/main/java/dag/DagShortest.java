package dag;

import model.DiGraph;
import model.Edge;
import topo.TopoKahn;
import util.Metrics;

import java.util.*;

public class DagShortest {

    public static class Result {
        public final int src;
        public final int[] dist;
        public final int[] parent;

        public Result(int src, int[] dist, int[] parent) {
            this.src = src;
            this.dist = dist;
            this.parent = parent;
        }

        public List<Integer> pathTo(int t) {
            List<Integer> p = new ArrayList<>();
            if (dist[t] == Integer.MAX_VALUE) return p;
            for (int v = t; v != -1; v = parent[v]) p.add(v);
            Collections.reverse(p);
            return p;
        }
    }

    public static Result run(DiGraph dag, int src, Metrics m) {
        List<Integer> order = TopoKahn.topo(dag, m);
        int n = dag.n;
        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        m.start("dag_short_time");
        for (int u : order) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (Edge e : dag.edgesFrom(u)) {
                int nd = dist[u] + e.w;
                m.inc("dag_relax");
                if (nd < dist[e.v]) {
                    dist[e.v] = nd;
                    parent[e.v] = u;
                }
            }
        }
        m.stop("dag_short_time");

        return new Result(src, dist, parent);
    }
}
