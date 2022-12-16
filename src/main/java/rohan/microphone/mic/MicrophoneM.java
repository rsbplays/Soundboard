package rohan.microphone.mic;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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

    public static IntBuffer intBuffer = IntBuffer.allocate(512);

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
                if (info.getName().contains("Real")){
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

        AudioInputStream audioInputStream = null;
        byte[] audioData;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("audio.wav"));
            audioData = new byte[audioInputStream.available()];
            audioInputStream.read(audioData, 0, audioData.length);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(audioData.length);
        // Read the audio data from the stream and store it in a buffer
        sourceDataLine.write(audioData,0,audioData.length);

        while (true){
            byte[] bytes = new byte[4];
            targetDataLine.read(bytes,0,bytes.length);
            //System.out.println(bytesToInt(bytes[0],bytes[1])+" "+bytesToInt(bytes[2],bytes[3]));
            if (intBuffer.remaining()<10) intBuffer.clear();
            intBuffer.put(bytesToInt(bytes[1],bytes[0]));

            int i = 0;
            for (byte b:bytes){
                bytes[i] = (byte) ((byte)  b+b);
                i++;
            }
            sourceDataLine.write(bytes, 0, bytes.length);
            // Create the AudioData object from the byte array
        }
    }
    public int bytesToInt(byte a, byte b){
        byte[] bytes = new byte[2];
        bytes[0]=a;
        bytes[1]=b;
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getShort();
    }
}
