import java.util.Collection;
import java.util.LinkedList;

public class Graph {
    private final int V;
    private int E;
    public boolean error;
    private LinkedList<Edge> [] adj;
    public Graph(int V){
        if(V >= 2 && V <=100) {
            this.V = V;
            this.E = 0;
            adj = (LinkedList<Edge>[]) new LinkedList[V];
            for (int i = 0; i < V; i++)
                adj[i] = new LinkedList<>();
            error = false;
        }
        else {
            this.V = 0;
            error = true;
        }
    }
    public int V(){return V;}
    public int E(){return E;}
    public void addEdge(Edge e){
        adj[e.either()].add(e);
        adj[e.other(e.either())].add(e);
    }
    public Collection<Edge> adj(int v){
        return adj[v];
    }
    public Collection<Edge> edges(){
        LinkedList<Edge> a= new LinkedList<Edge>();
        for(int v=0; v<V; v++){
            for(Edge e : adj[v]) {
                if (e.other(v) > v) {
                    a.add(e);
                }
            }
        }
        return a;
    }
    public String toString(){
        String str = "";
        for(Edge e: edges()){
            str += e.toString()+'\n';
        }
        return str;
    }
}