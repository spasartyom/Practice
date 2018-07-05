import java.awt.*;


import javax.swing.*;

public class Canvas extends  JPanel {

    private VGraph content;
    private boolean wght = false;
    private static final Color[] compColor = {Color.white,  Color.blue, Color.green, Color.yellow,   Color.magenta,
            Color.cyan,   Color.gray, Color.pink,  Color.darkGray, Color.red};

    //конструктор
    public Canvas() {
        content = null;
        this.setLayout(null);
    }

    //обновление содержимого
    public void setContent(Graph data) {
        if (content != null) {
            content.setVisible(false);
        }
        content = new VGraph(data);
        this.add(content);
        this.revalidate();
        this.repaint();
    }
    public void setMST(Graph data){
        content.SetMST(new MST(data));
        this.add(content);
        this.revalidate();
        this.repaint();
    }
    public void checkBox(){
        wght = wght ? false : true;
        content.wght = wght;
        this.add(content);
        this.revalidate();
        this.repaint();
    }


}
