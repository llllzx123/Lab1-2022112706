package basis;

import java.util.ArrayList;
import java.util.HashSet;

public class BridgeWordsTester {
    protected DirectedGraph graph;

    public BridgeWordsTester(DirectedGraph graph) {
        this.graph = graph;
    }

    // 改为 protected 或 public，便于测试调用
    public String queryBridgeWords(String word1, String word2) {
        ArrayList<Vertex> vertices = this.graph.getVertices();
        Vertex a = null;
        Vertex b = null;
        for (Vertex v : vertices) {
            if (v.name.equalsIgnoreCase(word1)) {
                a = v;
            }
            if (v.name.equalsIgnoreCase(word2)) {
                b = v;
            }
        }
        if (a == null && b != null) {
            return "No \"" + word1 + "\" in the graph!";
        } else if (a != null && b == null) {
            return "No \"" + word2 + "\" in the graph!";
        } else if (a == null && b == null) {
            return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
        }

        HashSet<Vertex> successors = a.successors;
        HashSet<Vertex> predecessors = b.predecessors;
        HashSet<Vertex> intersection = new HashSet<>(successors);
        intersection.retainAll(predecessors);

        if (intersection.isEmpty()) {
            return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
        }

        if (intersection.size() == 1) {
            String wordName = intersection.toString();
            return "The bridge word from \"" + word1 + "\" to \"" + word2 + "\" is: " +
                    wordName.substring(1, wordName.length() - 1) + ".";
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Vertex v : intersection) {
            if (count < intersection.size() - 1) {
                sb.append(v.name).append(", ");
            } else {
                sb.append("and ").append(v.name).append(".");
            }
            count++;
        }

        return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + sb;
    }
}
