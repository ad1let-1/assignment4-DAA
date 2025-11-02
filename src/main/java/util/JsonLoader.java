package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.DiGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Читает твой tasks.json.
 * Поддерживает 2 варианта:
 * 1) один граф
 * 2) "graphs": [ ... ]  (если решишь сделать несколько)
 */
public class JsonLoader {

    public static class InputData {
        public boolean directed = true;
        public int n;
        public int source = 0;
        public String weightModel = "edge";
        public DiGraph graph;
    }

    public static List<InputData> loadAll(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(path));
            List<InputData> list = new ArrayList<>();

            // если tasks.json в формате { "graphs": [ ... ] }
            if (root.has("graphs")) {
                for (JsonNode gNode : root.get("graphs")) {
                    list.add(parseSingle(gNode));
                }
            } else {
                // обычный формат — один граф
                list.add(parseSingle(root));
            }

            return list;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read json: " + path, e);
        }
    }

    private static InputData parseSingle(JsonNode root) {
        InputData data = new InputData();
        data.directed = root.path("directed").asBoolean(true);
        data.n = root.path("n").asInt();
        data.source = root.path("source").asInt(0);
        data.weightModel = root.path("weight_model").asText("edge");

        DiGraph g = new DiGraph(data.n);
        for (JsonNode e : root.withArray("edges")) {
            int u = e.get("u").asInt();
            int v = e.get("v").asInt();
            int w = e.has("w") ? e.get("w").asInt() : 1;
            g.addEdge(u, v, w);
            if (!data.directed) {
                g.addEdge(v, u, w);
            }
        }

        data.graph = g;
        return data;
    }
}
