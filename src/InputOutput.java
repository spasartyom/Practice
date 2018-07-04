import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class InputOutput {
    private PrintWriter cout=new PrintWriter(System.out);

    //ввод данных с читателя cin
    public Graph getData(Graph g, BufferedReader cin, BufferedReader cinV) throws IOException {//передаем ссылку на граф, в который надо считать данные,
        StringTokenizer tokenizer = new StringTokenizer(cinV.readLine());
        g = new Graph(Integer.parseInt(tokenizer.nextToken())); //считываем количество вершин графа
        //g.numE = Integer.parseInt(tokenizer.nextToken()); //считываем количество ребер графа
        String s;
        while((s=cin.readLine())!=null){
            tokenizer = new StringTokenizer(s);
            int v = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            double weight = Double.parseDouble(tokenizer.nextToken());
            g.addEdge(new Edge(v, w, weight));
        }
        return g;
    }

}
