package dag;

import model.DiGraph;
import org.junit.jupiter.api.Test;
import util.SimpleMetrics;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DagTests {

    @Test
    void shortestAndLongest() {

        DiGraph g = new DiGraph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(0, 2, 5);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 1);

        var sp = DagShortest.run(g, 0, new SimpleMetrics());
        // самый короткий: 0-1-2-3-4 = 1 + 2 + 1 + 1 = 5
        assertEquals(5, sp.dist[4]);

        var lp = DagLongest.run(g, new SimpleMetrics());
        List<Integer> path = lp.path();
        assertFalse(path.isEmpty());
    }
}
