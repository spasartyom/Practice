
import java.util.Collection;
import java.util.LinkedList;

public class MST {
    private final int V;
    private LinkedList<Edge> mst;
    private UnionField uf;
    private Graph g;
    private double weidht=0;
    public int V(){
        return V;
    }
    public MST(Graph G){
        g = G;
        V = G.V();
        uf = new UnionField(G.V());
        mst = new LinkedList<Edge>();
    }
    public void build(){
        for(int i=1; i<g.V() && mst.size()<g.V()-1; i=i+i){
            this.buildStep();
        }
    }
    public void buildStep(){
        Edge[] close=new Edge[g.V()];
        for(Edge e : g.edges()){
            int v= e.either(), w=e.other(v);
            int vuf=uf.find(v), wuf=uf.find(w);
            if(vuf==wuf) continue;
            if(close[vuf]==null || less(e, close[vuf])) close[vuf] = e;
            if(close[wuf]==null || less(e, close[wuf])) close[wuf] = e;
        }

        for(int j=0; j<g.V(); j++){
            Edge e = close[j];
            if(e!=null){
                int v =e.either(), w=e.other(v);
                if(!uf.connected(v, w)){
                    mst.add(e);
                    weidht += e.weight();
                    uf.unoin(v, w);
                }
            }
        }
    };
    public Graph MSTtoGraph(){
        Graph gr = new Graph(V());
        if(mst != null){
            for(Edge e : mst){
                gr.addEdge(e);
            }
        }
        return gr;
    }
    public boolean equal(MST x){
        if(mst.size() != x.edges().size()) return false;
        for(int i=0; i<mst.size() ; i++) {
            Edge e = mst.get(i);
            LinkedList<Edge> f =(LinkedList<Edge>) x.edges();
            Edge f1 = f.get(i);
            if (!e.equals(f1)) return false;
        }
        return true;
    }
    private  boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }
    public Collection<Edge> edges(){
        return mst;
    }
    public double weight(){
        return weidht;
    }
    public String toString(){
        String str = "";
        for(Edge e: mst){
            str += e.toString()+'\n';
        }
        str += weidht;
        return str;
    }
}
