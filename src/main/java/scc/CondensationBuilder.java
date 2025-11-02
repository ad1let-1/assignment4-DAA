package scc;

import model.DiGraph;
import model.Edge;

import java.util.HashSet;
import java.util.Set;

/**
 * Строит конденсацию (граф КОС → DAG)
 */
public class CondensationBuilder {

    public static DiGraph build(DiGraph g, int[] comp) {
        // сколько всего компонент
        int max = 0;
        for (int c : comp) if (c > max) max = c;
        int comps = max + 1;

        DiGraph dag = new DiGraph(comps);
        Set<Long> used = new HashSet<>();

        for (int u = 0; u < g.n; u++) {
            int cu = comp[u];
            for (Edge e : g.edgesFrom(u)) {
                int cv = comp[e.v];
                if (cu != cv) {
                    long key = (((long) cu) << 32) | (cv & 0xffffffffL);
                    if (used.add(key)) {
                        dag.addEdge(cu, cv, e.w);
                    }
                }
            }
        }
        return dag;
    }
}
