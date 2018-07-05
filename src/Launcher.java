import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;

import java.io.*;

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

    //Graphics g;

    private InputOutput ios;
    private Graph gr;
    private MST mst;

    private JPanel rootPanel;

    // ключевое слово super, которое обозначает суперкласс, т.е. класс, производным от которого является текущий класс
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

        this.spinner = new JSpinner();
        this.spinner.setValue(7);
        this.spinner.setBounds(112,12,38,28);
        this.spinner.setVisible(true);

        this.labelWeight = new JLabel();
        this.labelWeight.setText("Num of vertex:");
        this.labelWeight.setFont(font);
        this.labelWeight.setBounds(12,18,108,14 );
        this.labelWeight.setVisible(true);

        this.label = new JLabel();
        this.label.setText("Graph data:");
        this.label.setFont(font);
        this.label.setBounds(12,44,85,14);
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

        this.buttonGraph.setBounds(12,260,66,24);
        this.buttonResult.setBounds(86,260,66,24);
        this.buttonRand.setBounds(12,292,66,24);
        this.buttonNext.setBounds(86,292,66,24);
        this.buttonCheck.setBounds(12,324,66,24);

        //убираем выделение текста кнопки

        this.buttonGraph.setFocusPainted(false);
        this.buttonResult.setFocusPainted(false);
        this.buttonNext.setFocusPainted(false);
        this.buttonRand.setFocusPainted(false);
        this.buttonCheck.setFocusPainted(false);


        //поясняющие надписи
        this.descLabel = new JLabel();
        this.descLabel.setBounds(166,450,418,48);
        this.descLabel.setText("Description: ");
        this.descLabel.setFont(font);

        this.resLabel = new JLabel();
        this.resLabel.setBounds(166,490,418,16);
        this.resLabel.setText("Result: -");
        this.resLabel.setFont(font);
        //строка для указания кол-ва вершин
        this.dataGraph = new JTextArea("");
        this.dataGraph.setBounds(12,64,140,1840);
        //this.dataGraph.setText("8 10\n1 2\n2 3\n2 4\n3 4\n4 3\n4 5\n4 6\n6 7\n7 8\n8 6");
        JScrollPane scroll = new JScrollPane(dataGraph);
        scroll.setBounds(12,64,140,184);
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
                    canvas.setContent(gr);
                    descLabel.setText("Description: Graph is constructed");
                    resLabel.setText("Result: -");
                    buttonResult.setEnabled(true);
                    buttonNext.setEnabled(true);
                    buttonGraph.setEnabled(false);
                    buttonCheck.setEnabled(true);
                } catch (Exception e) {
                    descLabel.setText("Description: exception! "+e.getClass().getName()+": "+e.getMessage());
                }
            }
        });

        buttonCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev) {
                if(buttonResult.isEnabled())
                    canvas.checkBox(gr);
                else
                    canvas.checkBox(new MST(gr));
            }
        });

        buttonResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mst = new MST(gr);
                canvas.setContent(mst);
                descLabel.setText("Description: MST");
                resLabel.setText("Result: "+ mst.weight());
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

/*
7
0 1 7
0 3 4
1 3 9
1 2 11
1 4 10
2 4 5
3 4 15
3 5 6
4 5 12
4 6 8
5 6 13

2
0 1 100
*/
