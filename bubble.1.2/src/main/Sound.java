package main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.omg.CORBA._IDLTypeStub;


public class Sound {
	Boolean _isStillPlaying = false;
	
	
	String _file;

	public Sound (String fileName){
		_file = fileName;
	}
	
	
	public synchronized void play() 
    {
		_isStillPlaying = true;
        // Note: use .wav files             
        new Thread(new Runnable() { 
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(_file));
                    clip.open(inputStream);
                    clip.start(); 
                } catch (Exception e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + _file);
                }
            }
        }).start();
        
    }
	
	
	public boolean isPlaying() {
		return _isStillPlaying;
	}
	
	
}
