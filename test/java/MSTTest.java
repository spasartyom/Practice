import org.junit.Test;

import static org.junit.Assert.*;

public class MSTTest {

    @Test
    public void testMIN(){
        Graph graph = new Graph(0);
        boolean actual = true;
        assertEquals(actual, graph.error);
    }
    @Test
    public void testMAX(){
        Graph graph = new Graph(101);
        boolean actual = true;
        assertEquals(actual, graph.error);
    }
    @Test
    public void testMINE(){
        Graph graph = new Graph(2);
        Edge edge = new Edge(0,1,2);
        graph.addEdge(edge);
        Graph actual = new Graph(2);
        actual.addEdge(edge);
        MST mst = new MST(graph);
        mst.build();
        assertEquals(actual.toString(), graph.toString());
    }

    @Test
    public void testMST(){
        Graph graph = new Graph(4);
        Edge edge0 = new Edge(0, 3, 13.4);
        Edge edge1 = new Edge(2, 3, 45.3);
        Edge edge2 = new Edge(1, 0, 21.3);
        Edge edge3 = new Edge(0, 2, 35.55);
        Edge edge4 = new Edge(1, 3, 16.65);
        graph.addEdge(edge0);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);

        Graph actual = new Graph(4);
        actual.addEdge(edge0);
        actual.addEdge(edge3);
        actual.addEdge(edge4);

        MST mst = new MST(graph);
        mst.build();

        assertEquals(actual.toString(), mst.MSTtoGraph().toString());
    }
}