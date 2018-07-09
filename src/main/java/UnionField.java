public class UnionField {
    private int[] id;
    private int count;
    public UnionField(int N){
        count = N;
        id = new int[N];
        for(int i=0; i<N; i++)id[i]=i;
    }
    public int count(){ return count;}
    public boolean connected(int p, int q){
        return find(p)==find(q);
    }
    public int find(int p){
        return id[p];
    }
    public void unoin(int p, int q){
        int ptr=find(p), qtr=find(q);
        if(find(p)== find(q)) return;
        for(int i=0; i<id.length; i++){
            if(id[i] == ptr) id[i] = qtr;
        }
        count--;
    }
}
