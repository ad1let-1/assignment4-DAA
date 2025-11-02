package topo;

import model.DiGraph;
import org.junit.jupiter.api.Test;
import util.SimpleMetrics;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopoTests {

    @Test
    void topoOnDag() {
        // 0 -> 1
        // 0 -> 2
        // 1 -> 3
        // 2 -> 3
        DiGraph g = new DiGraph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 1);

        List<Integer> order = TopoKahn.topo(g, new SimpleMetrics());

        // 0 должен быть раньше 1 и 2
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        // 1 и 2 раньше 3
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
    }
}
