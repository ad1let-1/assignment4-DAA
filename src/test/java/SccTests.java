package scc;

import model.DiGraph;
import org.junit.jupiter.api.Test;
import util.SimpleMetrics;

import static org.junit.jupiter.api.Assertions.*;

public class SccTests {

    @Test
    void cycleAndTail() {
        // SCC: (0,1,2)
        // потом 2 -> 3 -> 4
        DiGraph g = new DiGraph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 1);

        TarjanSCC scc = new TarjanSCC(g, new SimpleMetrics());
        int[] comp = scc.run();

        // 0,1,2 — в одной компоненте
        assertEquals(comp[0], comp[1]);
        assertEquals(comp[1], comp[2]);

        // 3 и 4 — в других
        assertNotEquals(comp[2], comp[3]);
        assertNotEquals(comp[3], comp[4]);
    }
}
