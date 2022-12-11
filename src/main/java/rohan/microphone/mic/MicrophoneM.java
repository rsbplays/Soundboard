package rohan.microphone.mic;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class MicrophoneM {

    private static final int bufferSize = 512;
    private static float fFrameRate = 44100.0F;
    Mixer inputMixer;
    Mixer outputMixer;
    private String inputLineName;
    private String outputLineName;
    private SourceDataLine sourceDataLine;
    private TargetDataLine targetDataLine;

    private static final AudioFormat signedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, fFrameRate, 16, 2, 4, fFrameRate, false);

    public TargetDataLine getTargetDataLine(){
        try {
            for (Mixer.Info info:AudioSystem.getMixerInfo()){
                if (info.getDescription().equals("Direct Audio Device: DirectSound Capture")) {
                    System.out.println(info.getName());
                    if (info.getName().equals("Microphone (Realtek(R) Audio)")){
                        System.out.println("found");
                        return AudioSystem.getTargetDataLine(signedFormat,info);
                    }
                }
            }
            return AudioSystem.getTargetDataLine(signedFormat);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public SourceDataLine getSourceDataLine(){
        for (Mixer.Info info:AudioSystem.getMixerInfo()){
            if (info.getDescription().equals("Direct Audio Device: DirectSound Playback")){
                if (info.getName().equals("Speakers (Realtek(R) Audio)")){
                    try {
                        System.out.println("found");
                        return AudioSystem.getSourceDataLine(signedFormat,info);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return null;
    }



    public void echotest(){
        sourceDataLine = getSourceDataLine();
        targetDataLine = getTargetDataLine();
        try {
            targetDataLine.open(signedFormat,targetDataLine.getBufferSize());
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            sourceDataLine.open();
            targetDataLine.open();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        sourceDataLine.start();
        targetDataLine.start();



        while (true){
            byte[] bytes = new byte[16];
            targetDataLine.read(bytes,0,bytes.length);

            int i = 0;
            for (byte b:bytes){
                System.out.println(b+" "+bytes[i+1]+" "+bytes[i+2]+" "+bytes[i+3]+" "+bytes[i+4]+" "+bytes[i+5]+" "+bytes[i+6]+" "+bytes[i+7]+" ");
                i+=8;
                if (i==16) {
                    break;
                }

            }

            System.out.println(bytes.length);
            sourceDataLine.write(bytes, 0, bytes.length);
            // Create the AudioData object from the byte array
        }
    }
    

}
