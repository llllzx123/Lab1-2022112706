package ui;

import basis.DirectedGraph;
import basis.GraphProcessor;
import basis.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class GraphProcessorTest {

    private GraphProcessor graphProcessor;
    private DirectedGraph graph;
    private BaseWindowController bwc;

    @Before
    public void setUp() {
        graphProcessor = new GraphProcessor();
        graph = new DirectedGraph();
        bwc = new BaseWindowController();

        File tempFile;
        try {
            tempFile = File.createTempFile("testGraph", ".txt");
            FileWriter writer = new FileWriter(tempFile);
            // 设置测试用文本
            writer.write("To explore strange new worlds, To seek out new life and new civilizations");
            writer.close();

            // 生成图
            graph = GraphProcessor.generateGraph(tempFile.getAbsolutePath());
            bwc.graph = graph;

        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to create temporary file for testing.");
        }
    }

    // T1: 起点和终点都不存在
    @Test
    public void testShortestPath_BothNodesMissing() {
        String result = bwc.calcShortestPath("a", "b");
        assertEquals("No a or b in the graph!", result);
        System.out.println("测试用例1通过");
    }

    // T2: 起点存在，终点不存在
    @Test
    public void testShortestPath_EndNodeMissing() {
        String result = bwc.calcShortestPath("to", "b");
        assertEquals("No to or b in the graph!", result);
        System.out.println("测试用例2通过");
    }

    // T3: 起点不存在，终点存在
    @Test
    public void testShortestPath_StartNodeMissing() {
        String result = bwc.calcShortestPath("a", "worlds");
        assertEquals("No a or worlds in the graph!", result);
        System.out.println("测试用例3通过");
    }

    // T4: 起点终点存在，但无路径
    @Test
    public void testShortestPath_NoPath() {
        // 构造无路径情况，例如“civilizations” 到 “To”
        String result = bwc.calcShortestPath("civilizations", "to");
        assertEquals("No path from civilizations to to!", result);
        System.out.println("测试用例4通过");
    }

    // T5: 起点终点存在，有路径
    @Test
    public void testShortestPath_PathExists() {
        String result = bwc.calcShortestPath("to", "new");
        assertTrue(result.startsWith("The length of the shortest path is "));
        System.out.println("测试用例5通过");
    }
}
