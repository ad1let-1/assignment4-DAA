import dag.DagLongest;
import dag.DagShortest;
import model.DiGraph;
import scc.CondensationBuilder;
import scc.TarjanSCC;
import topo.TopoKahn;
import util.JsonLoader;
import util.Metrics;
import util.SimpleMetrics;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = (args.length > 0) ? args[0] : "tasks.json";

        List<JsonLoader.InputData> inputs = JsonLoader.loadAll(path);

        for (int gi = 0; gi < inputs.size(); gi++) {
            System.out.println("==== GRAPH " + (gi + 1) + " ====");
            JsonLoader.InputData data = inputs.get(gi);
            DiGraph g = data.graph;

            Metrics m = new SimpleMetrics();

            // 1) SCC
            TarjanSCC scc = new TarjanSCC(g, m);
            int[] comp = scc.run();
            System.out.println("SCC (component per vertex): " + Arrays.toString(comp));

            // 2) condensation DAG
            DiGraph dag = CondensationBuilder.build(g, comp);
            System.out.println("Condensation DAG size: " + dag.n);

            // 3) topo (по компонентам)
            var topo = TopoKahn.topo(dag, m);
            System.out.println("Topological order (components): " + topo);

            // 4) shortest on DAG — начнем с компоненты исходной вершины
            int src = data.source;
            int srcComp = comp[src];
            DagShortest.Result sp = DagShortest.run(dag, srcComp, m);
            System.out.println("Shortest distances from comp " + srcComp + ": " + Arrays.toString(sp.dist));

            // 5) longest (critical) path on DAG
            DagLongest.Result lp = DagLongest.run(dag, m);
            System.out.println("Critical path (on components): " + lp.path());
            System.out.println("Critical path length: " + lp.best[lp.end]);

            // 6) metrics
            System.out.println("metrics:");
            System.out.println("  scc_dfs           = " + m.get("scc_dfs"));
            System.out.println("  topo_pop          = " + m.get("topo_pop"));
            System.out.println("  topo_push         = " + m.get("topo_push"));
            System.out.println("  dag_relax         = " + m.get("dag_relax"));
            System.out.println("  dag_relax_long    = " + m.get("dag_relax_long"));
            System.out.println();
        }
    }
}
