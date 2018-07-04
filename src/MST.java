
import java.util.Collection;
import java.util.LinkedList;

public class MST {
    private final int V;
    private LinkedList<Edge> mst;
    private double weidht=0;
    public int V(){
        return V;
    }
    public MST(Graph G){
        V = G.V();
        UnionField uf = new UnionField(G.V());
        mst = new LinkedList<Edge>();
        for(int i=1; i<G.V() && mst.size()<G.V()-1; i=i+i){

            Edge[] close=new Edge[G.V()];
            for(Edge e : G.edges()){
                int v= e.either(), w=e.other(v);
                int vuf=uf.find(v), wuf=uf.find(w);
                if(vuf==wuf) continue;
                if(close[vuf]==null || less(e, close[vuf])) close[vuf] = e;
                if(close[wuf]==null || less(e, close[wuf])) close[wuf] = e;
            }

            for(int j=0; j<G.V(); j++){
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
        }
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

