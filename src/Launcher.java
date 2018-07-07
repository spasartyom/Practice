import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Launcher extends JFrame{

    private JLabel labelWeight;
    private JSpinner spinner;
    private JLabel labelEdges;
    private JSpinner spinnerEdges;
    private JLabel descLabel;
    private JLabel resLabel;
    private JLabel label;
    private JTextArea dataGraph;
    private JButton buttonGraph;
    private JButton buttonResult;
    private JButton buttonNext;
    private JButton buttonRand;
    private JButton buttonCheck;
    private Canvas canvas;
    private InputOutput ios;
    private Graph gr;
    private MST mst;
    private JPanel rootPanel;

    public Launcher() {
        this.setSize(800,550);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(800,550));
        this.setTitle("BoruvkaMST");
        this.rootPanel = new JPanel();
        rootPanel.setLayout(null);      //абсолютное позиционирование
        rootPanel.setBounds(0,0,800,550);

        //this.setBounds(100,100,450,400);
        setContentPane(rootPanel);


        Font font = new Font("Consolas", 0, 13);

        SpinnerModel modelVertex = new SpinnerNumberModel(7,2,100,1);
        this.spinner = new JSpinner(modelVertex);
        this.spinner.setBounds(112,12,38,28);
        this.spinner.setVisible(true);

        this.labelWeight = new JLabel();
        this.labelWeight.setText("Num of vertex:");
        this.labelWeight.setFont(font);
        this.labelWeight.setBounds(12,18,108,14 );
        this.labelWeight.setVisible(true);

        SpinnerModel modelEdges = new SpinnerNumberModel(1,1,4950,1);
        this.spinnerEdges = new JSpinner(modelEdges);
        this.spinnerEdges.setBounds(112,52,38,28);
        this.spinnerEdges.setVisible(true);

        this.labelEdges = new JLabel();
        this.labelEdges.setText("Num of edges:");
        this.labelEdges.setFont(font);
        this.labelEdges.setBounds(12,58,108,14 );
        this.labelEdges.setVisible(true);

        this.label = new JLabel();
        this.label.setText("Graph data:");
        this.label.setFont(font);
        this.label.setBounds(12,84,85,14);
        this.label.setVisible(true);

        //кнопки управления
        this.buttonGraph = new JButton("Graph");
        this.buttonResult = new JButton("Result");
        this.buttonNext = new JButton("Next");
        this.buttonRand = new JButton("Rand");
        this.buttonCheck = new JButton("Check");

        Font fontButton = new Font(null, 0, 11);
        this.buttonGraph.setFont(fontButton);
        this.buttonResult.setFont(fontButton);
        this.buttonNext.setFont(fontButton);
        this.buttonRand.setFont(fontButton);
        this.buttonCheck.setFont(fontButton);

        //устанавливаем размеры кнопок

        this.buttonGraph.setBounds(12,300,66,24);
        this.buttonResult.setBounds(86,300,66,24);
        this.buttonRand.setBounds(12,332,66,24);
        this.buttonNext.setBounds(86,332,66,24);
        this.buttonCheck.setBounds(12,364,66,24);

        //убираем выделение текста кнопки

        this.buttonGraph.setFocusPainted(false);
        this.buttonResult.setFocusPainted(false);
        this.buttonNext.setFocusPainted(false);
        this.buttonRand.setFocusPainted(false);
        this.buttonCheck.setFocusPainted(false);


        //поясняющие надписи
        this.descLabel = new JLabel();
        this.descLabel.setBounds(166,450,618,48);
        this.descLabel.setText("Description: ");
        this.descLabel.setFont(font);

        this.resLabel = new JLabel();
        this.resLabel.setBounds(166,490,418,16);
        this.resLabel.setText("Result: -");
        this.resLabel.setFont(font);
        //строка для указания кол-ва вершин
        this.dataGraph = new JTextArea("");
        this.dataGraph.setBounds(12,104,140,1840);
        this.dataGraph.setText("0 1 7\n" +
                "0 3 4\n" +
                "1 3 9\n" +
                "1 2 11\n" +
                "1 4 10\n" +
                "2 4 5\n" +
                "3 4 15\n" +
                "3 5 6\n" +
                "4 5 12\n" +
                "4 6 8\n" +
                "5 6 13");
        JScrollPane scroll = new JScrollPane(dataGraph);
        scroll.setBounds(12,104,140,184);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.canvas = new Canvas();
        this.canvas.setBounds(166,0,634,460);
        this.canvas.setVisible(true);

        this.rootPanel.add(this.canvas);

        //устанавливаем виимость всех объектов
        this.buttonGraph.setVisible(true);
        this.buttonResult.setVisible(true);
        this.buttonNext.setVisible(true);
        this.buttonRand.setVisible(true);
        this.buttonCheck.setVisible(true);
        this.descLabel.setVisible(true);
        this.resLabel.setVisible(true);

        scroll.setVisible(true);

        //добавляем объекты на панель
        this.rootPanel.add(this.buttonGraph);
        this.rootPanel.add(this.buttonResult);
        this.rootPanel.add(this.buttonNext);
        this.rootPanel.add(this.buttonRand);
        this.rootPanel.add(this.buttonCheck);
        this.rootPanel.add(label);
        this.rootPanel.add(scroll);
        this.rootPanel.add(this.descLabel);
        this.rootPanel.add(this.resLabel);
        this.rootPanel.add(this.spinner);
        this.rootPanel.add(this.labelWeight);
        this.rootPanel.add(this.labelEdges);
        this.rootPanel.add(this.spinnerEdges);

        buttonResult.setEnabled(false);
        buttonNext.setEnabled(false);
        buttonCheck.setEnabled(false);

        rootPanel.setVisible(true);
        ios = new InputOutput();

        //инициализация графа
        buttonGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    gr = ios.getData(gr, new BufferedReader(new StringReader(dataGraph.getText())),
                            new BufferedReader(new StringReader(spinner.getValue().toString())));
                    mst = new MST(gr);
                    canvas.setContent(gr);
                    descLabel.setText("Description: Graph is constructed");
                    resLabel.setText("Result: -");
                    buttonResult.setEnabled(true);
                    buttonNext.setEnabled(true);
                    buttonGraph.setEnabled(true);
                    buttonCheck.setEnabled(true);
                } catch (Exception e) {
                    descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
                }
            }
        });

        buttonCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev) {
                canvas.checkBox();
            }
        });
        buttonNext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev) {
                try {
                    MST mstEnd = new MST(gr);
                    mstEnd.build();
                    if(mstEnd.equal(mst)){
                        buttonResult.setEnabled(false);
                        buttonNext.setEnabled(false);
                        buttonGraph.setEnabled(true);
                        resLabel.setText("Result: "+ mst.weight());
                    }
                    else{
                        resLabel.setText("Result: step by step...");
                    }
                    canvas.setMST(mst.MSTtoGraph());
                    mst.buildStep();
                    descLabel.setText("Description: MST");
                } catch (Exception e) {
                    descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
                }
            }
        });

        buttonResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mst.build();
                canvas.setMST(gr);
                descLabel.setText("Description: MST");
                resLabel.setText("Result: "+ mst.weight());
                buttonResult.setEnabled(false);
                buttonNext.setEnabled(false);
                buttonGraph.setEnabled(true);
            }
        });


        buttonRand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //int num = (int) (2 + Math.random()*9);//spinner.setValue(num);
                int num = (int) spinner.getValue();
                dataGraph.setText("");
                int numEdg1 = 0;
                for(int i = 0; i < num; ++i){
                    numEdg1 += i;
                }
                int numEdg = (int) spinnerEdges.getValue();
                if (numEdg > numEdg1) numEdg = numEdg1;
                int[] v1 = new int[numEdg1];
                int[] v2 = new int[numEdg1];
                double[] w = new double[numEdg1];

                for(int i = 0; i < numEdg; i++){
                    w[i] = Math.random()*1000;
                    do{
                        v1[i] = (int) (0 + Math.random() * num);
                        v2[i] = (int) (0 + Math.random() * num);
                    }while((v1[i] == v2[i]));
                    for (int j = 0; j < i; j++) {
                        if ((v1[i] == v1[j] && v2[i] == v2[j]) || (v1[i] == v2[j] && v2[i] == v1[j])) {
                            do {
                                v1[i] = (int) (0 + Math.random() * num);
                                v2[i] = (int) (0 + Math.random() * num);
                            }
                            while ((v1[i] == v2[i])
                                    || ((v1[i] == v1[j] && v2[i] == v2[j]) || (v1[i] == v2[j] && v2[i] == v1[j])));
                            j = -1;
                        }
                    }
                    int tmp = (int) w[i];
                    w[i] = 1 + ((double)tmp) / 20;
                    dataGraph.append("" + v1[i] + " ");
                    dataGraph.append("" + v2[i] + " ");
                    dataGraph.append("" + w[i] + "\n");
                }
                buttonResult.setEnabled(false);
                buttonNext.setEnabled(false);
                buttonGraph.setEnabled(true);
            }

        });
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Launcher();
    }
}
