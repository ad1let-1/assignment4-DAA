package scc;

import model.DiGraph;
import model.Edge;
import util.Metrics;

import java.util.*;

/**
 * Классический Tarjan SCC.
 * comp[v] = номер компоненты
 */
public class TarjanSCC {

    private final DiGraph g;
    private final Metrics m;

    private final int[] disc;
    private final int[] low;
    private final int[] comp;
    private final boolean[] inStack;
    private final Deque<Integer> stack = new ArrayDeque<>();

    private int time = 0;
    private int compCount = 0;

    public TarjanSCC(DiGraph g, Metrics m) {
        this.g = g;
        this.m = m;
        int n = g.n;
        disc = new int[n];
        low = new int[n];
        comp = new int[n];
        inStack = new boolean[n];
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        Arrays.fill(comp, -1);
    }

    public int[] run() {
        m.start("scc_time");
        for (int v = 0; v < g.n; v++) {
            if (disc[v] == -1) {
                dfs(v);
            }
        }
        m.stop("scc_time");
        return comp;
    }

    private void dfs(int u) {
        disc[u] = low[u] = time++;
        stack.push(u);
        inStack[u] = true;
        m.inc("scc_dfs");

        for (Edge e : g.edgesFrom(u)) {
            int v = e.v;
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        // u — корень компоненты
        if (low[u] == disc[u]) {
            while (true) {
                int x = stack.pop();
                inStack[x] = false;
                comp[x] = compCount;
                if (x == u) break;
            }
            compCount++;
        }
    }
}
