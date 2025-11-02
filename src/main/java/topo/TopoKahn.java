package topo;

import model.DiGraph;
import model.Edge;
import util.Metrics;

import java.util.*;

/**
 * Топологическая сортировка (алгоритм Кана)
 */
public class TopoKahn {

    public static List<Integer> topo(DiGraph dag, Metrics m) {
        int n = dag.n;
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Edge e : dag.edgesFrom(u)) {
                indeg[e.v]++;
            }
        }

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) q.add(i);
        }

        List<Integer> order = new ArrayList<>();
        m.start("topo_time");
        while (!q.isEmpty()) {
            int u = q.remove();
            order.add(u);
            m.inc("topo_pop");
            for (Edge e : dag.edgesFrom(u)) {
                if (--indeg[e.v] == 0) {
                    q.add(e.v);
                    m.inc("topo_push");
                }
            }
        }
        m.stop("topo_time");

        if (order.size() != n) {
            throw new IllegalStateException("Graph is not DAG (in condensation shouldn't happen)");
        }

        return order;
    }
}
