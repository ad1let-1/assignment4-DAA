package dag;

import model.DiGraph;
import model.Edge;
import topo.TopoKahn;
import util.Metrics;

import java.util.*;

public class DagLongest {

    public static class Result {
        public final int[] best;
        public final int[] parent;
        public final int end;

        public Result(int[] best, int[] parent, int end) {
            this.best = best;
            this.parent = parent;
            this.end = end;
        }

        public List<Integer> path() {
            List<Integer> p = new ArrayList<>();
            for (int v = end; v != -1; v = parent[v]) p.add(v);
            Collections.reverse(p);
            return p;
        }
    }

    public static Result run(DiGraph dag, Metrics m) {
        List<Integer> order = TopoKahn.topo(dag, m);
        int n = dag.n;
        int[] best = new int[n];
        int[] parent = new int[n];
        Arrays.fill(best, Integer.MIN_VALUE / 4);
        Arrays.fill(parent, -1);

        // найдём вершины-источники (indegree==0) — от них старт
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Edge e : dag.edgesFrom(u)) {
                indeg[e.v]++;
            }
        }
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) best[i] = 0;
        }

        m.start("dag_long_time");
        for (int u : order) {
            if (best[u] == Integer.MIN_VALUE / 4) continue;
            for (Edge e : dag.edgesFrom(u)) {
                int cand = best[u] + e.w;
                m.inc("dag_relax_long");
                if (cand > best[e.v]) {
                    best[e.v] = cand;
                    parent[e.v] = u;
                }
            }
        }
        m.stop("dag_long_time");

        int end = 0;
        for (int i = 1; i < n; i++) {
            if (best[i] > best[end]) end = i;
        }

        return new Result(best, parent, end);
    }
}
