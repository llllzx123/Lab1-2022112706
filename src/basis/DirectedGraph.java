package basis;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class DirectedGraph {
	private ArrayList<Vertex> vertices;		//顶点表
	
	public DirectedGraph() {
		this.vertices = new ArrayList<>();
	}
	
	public ArrayList<Vertex> getVertices() {
		return this.vertices;
	}
	
	public int getVertexNumber() {
		return this.vertices.size();
	}
	
	public int getEdgeNumber() {
		int number = 0;
		for (Vertex v : vertices) {
			number += v.successors.size();
		}
		return number;
	}

	/**
	 *
	 * @param name 向有向图中添加的节点名称
	 */
	public void addVertex(String name) {
		for (Vertex v : vertices) {
			if (v.name.equals(name)) {
				return;
			}
		}
		vertices.add(new Vertex(name));
	}

	/**
	 *
	 * @param head 向有向图中添加的边的起点名称
	 * @param tail 向有向图中添加的边的终点名称
	 */
	public void addEdge(String head, String tail) {
		Vertex a = new Vertex();
		Vertex b = new Vertex();
        for (Vertex v : vertices) {
            if (v.name.equals(head)) {
            	a = v;
            }
            if (v.name.equals(tail)) {
            	b = v;
            }
        }
        if (a.successors.contains(b)) {
        	int weight = a.weights.get(b);
        	a.weights.replace(b, weight + 1);
        } else {
        	a.successors.add(b);
        	b.predecessors.add(a);
        	a.weights.put(b, 1);
        }
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Vertex v : vertices) {
			result += v.toString() + ": " + v.successors.toString() + "\n";
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		int code = 0;
		for (Vertex v : vertices) {
			code += v.hashCode();
		}
		return code;
	}

//	public Map<String, Double> calculatePageRank(double d, int iterations) {
//		Map<String, Double> prValues = new HashMap<>();
//		double numVertices = vertices.size();
//		double initialRank = 1.0 / numVertices;
//
//		// 初始化每个节点的PR值
//		for (Vertex v : vertices) {
//			prValues.put(v.name, initialRank);
//		}
//
//		// 开始迭代过程
//		for (int i = 0; i < iterations; i++) {
//			Map<String, Double> newPRValues = new HashMap<>();
//			for (Vertex v : vertices) {
//				double incomingRank = 0.0;
//				for (Vertex u : v.predecessors) {
//					incomingRank += prValues.get(u.name) / u.successors.size();
//				}
//				double newRank = (1 - d) / numVertices + d * incomingRank;
//				newPRValues.put(v.name, newRank);
//			}
//			prValues = newPRValues;
//		}
//
//		return prValues;
//	}
public Map<String, Double> calculatePageRank(double d, int iterations) {
	Map<String, Double> prValues = new HashMap<>();
	double numVertices = vertices.size();
	double initialRank = 1.0 / numVertices;

	// 初始化每个节点的PR值
	for (Vertex v : vertices) {
		prValues.put(v.name, initialRank);
	}

	// 开始迭代过程
	for (int i = 0; i < iterations; i++) {
		Map<String, Double> newPRValues = new HashMap<>();
		double totalDanglingRank = 0.0;

		// 计算Dangling Nodes的总PR值
		for (Vertex v : vertices) {
			if (v.successors.isEmpty()) { // 如果没有后继节点，就是Dangling Node
				totalDanglingRank += prValues.get(v.name);
			}
		}

		for (Vertex v : vertices) {
			double incomingRank = 0.0;

			// 处理来自前驱节点的PageRank值
			for (Vertex u : v.predecessors) {
				incomingRank += prValues.get(u.name) / u.successors.size();
			}

			// 计算新PageRank值，加入Dangling Node的贡献
			// totalDanglingRank/numVertices 是每个节点从Dangling Node中获得的PageRank
			double newRank = (1 - d) / numVertices + d * (incomingRank + totalDanglingRank / numVertices);
			newPRValues.put(v.name, newRank);
		}

		prValues = newPRValues; // 更新PR值
	}

	return prValues;
}
}
