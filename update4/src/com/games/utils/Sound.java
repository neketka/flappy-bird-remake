package com.games.utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Created by Nikita on 3/11/14.
 */
public class Sound // Holds one audio file
        {
private AudioClip song; // Sound player
private URL songPath; // Sound path
        public Sound(String filename)
        {
        try
        {
        songPath = new URL(filename); // Get the Sound URL
        song = Applet.newAudioClip(songPath); // Load the Sound
        }
        catch(Exception e){} // Satisfy the catch
        }
            public Sound(URL url)
            {
                try
                {
                    songPath = url; // Get the Sound URL
                    song = Applet.newAudioClip(songPath); // Load the Sound
                }
                catch(Exception e){} // Satisfy the catch
            }
public void playSound()
        {
        song.loop(); // Play
        }
public void stopSound()
        {
        song.stop(); // Stop
        }
public void playSoundOnce()
        {
        song.play(); // Play only once
        }
        }

