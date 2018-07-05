import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.lang.Math;

//объект, превращающий абстрактный граф в его плоскостное представление
public class VGraph extends JPanel {
    public ArrayList<HashMap<String,Object>> vertices;
    public ArrayList<HashMap<String,Object>> edges;
    public ArrayList<HashMap<String,Object>> mstEdges;
    public boolean wght = false;
    public HashMap<String, Object> current;

    //public ArrayList<HashMap<String, Object>> weightes;

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        for (HashMap<String,Object> e: edges) {
            if(attend(e)){
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.RED);
            }
            g2d.draw((Line2D) e.get("component"));
            g2d.setStroke(new BasicStroke());
            g2d.setColor(Color.BLACK);
            if(wght) {
                Coord From = (Coord) e.get("posFrom");
                Coord To = (Coord) e.get("posTo");
                g2d.drawString(e.get("weight").toString(), (To.x - From.x) / 2 + From.x, (To.y - From.y) / 2 + From.y);
            }
        }

        //Работа с цветом линии/фигуры
        // Запоминаем исходный цвет;
        Color oldColor = g.getColor();

        //рисование кружочков для вершин графа
        for (int i = 0; i<vertices.size(); ++i) {       //проходим по всем вершинам
            Coord vertC = (Coord) vertices.get(i).get("pos");    //координата вершины
            g2d.draw((Ellipse2D) vertices.get(i).get("component"));
            //если надо закрасить кружочки:
            Color newColor = (Color) vertices.get(i).get("color");
            // Устанавливаем новый цвет;
            g2d.setColor(newColor);
            g2d.fill((Ellipse2D) vertices.get(i).get("component"));

            // Восстанавливаем исходный цвет;
            g2d.setColor(oldColor);

            g2d.drawString((String) vertices.get(i).get("name"), vertC.x-10, vertC.y+10);
        }
    }
    boolean attend(HashMap<String, Object> e){
        if(mstEdges==null) return false;
        for(HashMap<String, Object> er : mstEdges){
            if(e.get("from").toString().equals(er.get("from").toString()) &&
                    e.get("to").toString().equals(er.get("to").toString())) return true;
        }
        return false;
    }

    private void addVertex(String name) {
        HashMap<String, Object> vertex = new HashMap<>(5);
        vertex.put("name", name);
        vertex.put("color",Color.blue);
        vertex.put("pos", new Coord(0,0));
        vertex.put("component", new Ellipse2D.Double(0, 0, 30, 30));
        vertices.add(vertex);
    }

    private void addEdge(String fromName, String toName, double weight) {
        HashMap<String, Object> edge = new HashMap<>(6);
        edge.put("from", fromName);
        edge.put("to", toName);
        edge.put("posFrom", new Coord(0,0));
        edge.put("posTo", new Coord(0,0));
        edge.put("component", new Line2D.Double(0,0,0,0));
        edge.put("weight", weight);
        edges.add(edge);
    }
    private void addMstEdge(String fromName, String toName, double weight){
        HashMap<String, Object> edge = new HashMap<>(6);
        edge.put("from", fromName);
        edge.put("to", toName);
        edge.put("weight", weight);
        mstEdges.add(edge);
    }

    private HashMap<String,Object> vertexLookup(String name) {
        for (int i = 0; i<vertices.size(); ++i) {
            HashMap<String,Object> cur = vertices.get(i);
            if (cur.get("name").equals(name)) return cur;
        }

        return null;
    }

    private void reposition() {
        for (int i = 0; i<vertices.size(); ++i) {
            HashMap<String,Object> cur = vertices.get(i);
            Coord place = new Coord(314+(int)(150*Math.cos(6.28/vertices.size()*i)),220+(int)(150*Math.sin(6.28/vertices.size()*i)));
            cur.replace("pos", place);
            Ellipse2D.Double v = (Ellipse2D.Double) cur.get("component");
            v.setFrame(place.x-20, place.y-10, 30, 30);
        }

        for (int i = 0; i<edges.size(); ++i) {
            HashMap<String,Object> cur = edges.get(i);
            HashMap<String,Object> v = vertexLookup((String) cur.get("from"));
            Coord fromC = new Coord((Coord) v.get("pos"));
            v = vertexLookup((String) cur.get("to"));
            Coord toC = new Coord((Coord) v.get("pos"));

            Line2D line = (Line2D) cur.get("component");
            line.setLine(fromC.x, fromC.y, toC.x, toC.y);
            cur.replace("posFrom",fromC);
            cur.replace("posTo",toC);
        }
    }

    public void recolor(int id, Color c) {
        vertices.get(id).replace("color",c);
    }
    public void SetMST(MST original){
        original.build();
        LinkedList<Edge> tmp = new LinkedList<>(original.edges()) ;
        mstEdges = new ArrayList<>(original.edges().size());
        for(Edge e : tmp){
            this.addMstEdge(String.valueOf(e.either()), String.valueOf(e.other(e.either())), e.weight());
        }
    }

    public VGraph(Graph original) {
        this.setBounds(0,0,634, 460);
        this.setLayout(null);

        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());

        vertices = new ArrayList<>(original.V());
        edges = new ArrayList<>(original.edges().size());
        LinkedList<Edge> tmp = new LinkedList<>(original.edges()) ;
        for (int i = 0; i<original.V(); ++i) {
            this.addVertex( String.valueOf(i));
        }

        for(Edge e : tmp){
            this.addEdge(String.valueOf(e.either()), String.valueOf(e.other(e.either())), e.weight());
        }
        reposition();

        this.revalidate();
        this.repaint();
    }
    /*public VGraph(MST original) {
        this.setBounds(0,0,634, 460);
        this.setLayout(null);

        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());

        vertices = new ArrayList<>(original.V());
        edges = new ArrayList<>(original.edges().size());

        LinkedList<Edge> tmp = new LinkedList<>(original.edges()) ;
        for (int i = 0; i<original.V(); ++i) {
            this.addVertex( String.valueOf(i));
        }

        for(Edge e : tmp){
            this.addEdge(String.valueOf(e.either()), String.valueOf(e.other(e.either())), e.weight());
        }
        reposition();

        this.revalidate();
        this.repaint();
    }*/

    public HashMap<String, Object> find(Point2D p){
        for(HashMap<String,Object> v: vertices){
            Ellipse2D tmp = (Ellipse2D) v.get("component");
            if(tmp.contains(p)) return v;
        }
        return null;
    }

    private class MyMouse extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            current = find(e.getPoint());
        }
    }

    private class MyMove implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent e) {
            if(find(e.getPoint()) == null)
                setCursor(Cursor.getDefaultCursor());
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(current != null){
                Ellipse2D tmp = (Ellipse2D) current.get("component");
                Coord crd = new Coord(e.getX()+10,e.getY());
                if(crd.x<20) crd.x = 20;
                if(crd.y<20) crd.y = 20;
                if(crd.x>610) crd.x = 610;
                if(crd.y>440) crd.y = 440;
                tmp.setFrame(crd.x-20, crd.y-10,30,30);
                current.replace("pos", crd);

                for(HashMap<String, Object> ed : edges){
                    if(ed.get("from").toString().equals(current.get("name").toString())){
                        Coord f = (Coord) current.get("pos");
                        Coord t = (Coord) ed.get("posTo");
                        ed.replace("posFrom", f);
                        Line2D line = (Line2D) ed.get("component");
                        line.setLine(f.x, f.y, t.x, t.y);
                    }
                    if(ed.get("to").toString().equals(current.get("name").toString())){
                        Coord f = (Coord) ed.get("posFrom");
                        Coord t = (Coord) current.get("pos");
                        ed.replace("posTo", t);
                        Line2D line = (Line2D) ed.get("component");
                        line.setLine(f.x, f.y, t.x, t.y);
                    }
                }
                repaint();
            }
        }
    }
}
