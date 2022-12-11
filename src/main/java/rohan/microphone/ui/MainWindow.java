package rohan.microphone.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame;
    ScrollPane mediaLibary;
    JPanel mediaPane;

    public MainWindow(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,820);
        frame.setVisible(true);
        frame.setTitle("RBoard");
        frame.setLayout(new GridLayout(3,1));

        mediaLibary = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        frame.add(mediaLibary);

        mediaPane = new JPanel();
        mediaPane.setMinimumSize(new Dimension(350,500));
        mediaPane.setMaximumSize(new Dimension(350,500));
        mediaPane.setLayout(new GridLayout(25,1));
        mediaLibary.add(mediaPane);

        mediaPane.add(new MediaLibaryButton("x"));

        frame.update(frame.getGraphics());
        frame.validate();
        frame.repaint();
    }

}

