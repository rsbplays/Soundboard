package rohan.microphone;

import rohan.microphone.mic.MicrophoneM;
import rohan.microphone.ui.GraphPlotter;
import rohan.microphone.ui.MainWindow;

public class Start {
    public static void main(String args[]){
        GraphPlotter graphPlotter = new GraphPlotter();
        graphPlotter.go();

        MicrophoneM m = new MicrophoneM();
        m.echotest();

        MainWindow mainWindow = new MainWindow();
    }
}
