package ui;

import basis.DirectedGraph;
import basis.GraphProcessor;
import basis.BridgeWordsTester;
import basis.Vertex;


import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;

public class BaseWindowControllerTest {

    @Test
    public void testQueryBridgeWords() {
        File tempFile = null;
        try {
            // 创建临时文件并写入内容
            tempFile = File.createTempFile("bridgeWordsTest", ".txt");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("To explore strange new worlds,To seek out new life and new civilizations");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail("创建临时文件失败！");
        }

        // 构建图
        DirectedGraph graph = GraphProcessor.generateGraph(tempFile.getAbsolutePath());
        BridgeWordsTester tester = new BridgeWordsTester(graph);

        // 测试用例
        assertEquals("The bridge word from \"explore\" to \"new\" is: strange.",
                tester.queryBridgeWords("explore", "new"));
        System.out.println("测试用例1通过！");

        assertEquals("The bridge word from \"new\" to \"and\" is: life.",
                tester.queryBridgeWords("new", "and"));
        System.out.println("测试用例2通过！");

        assertEquals("No bridge words from \"seek\" to \"to\"!",
                tester.queryBridgeWords("seek", "to"));
        System.out.println("测试用例3通过！");

        assertEquals("No \"exciting\" in the graph!",
                tester.queryBridgeWords("exciting", "and"));
        System.out.println("测试用例4通过！");

        assertEquals("No \"exciting\" and \"synergies\" in the graph!",
                tester.queryBridgeWords("exciting", "synergies"));
        System.out.println("测试用例5通过！");

        assertEquals("No bridge words from \"new\" to \"new\"!",
                tester.queryBridgeWords("new", "new"));
        System.out.println("测试用例6通过！");

        System.out.println("所有桥接词测试用例通过！");

        // 删除临时文件
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
}
