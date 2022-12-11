package rohan.microphone.ui;

import javax.swing.*;
import java.awt.*;

public class MediaLibaryButton extends JButton {
    public MediaLibaryButton(String text) {
        super(text);
        setMaximumSize(new Dimension(350,50));
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(Color.WHITE);
        setFont(Font.getFont(Font.SANS_SERIF));
        setHorizontalAlignment(LEFT);
        repaint();
        validate();

    }
}
