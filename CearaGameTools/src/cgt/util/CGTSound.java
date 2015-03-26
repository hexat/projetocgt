package cgt.util;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class CGTSound implements Serializable
{
	private CGTFile file;
	private float volume;
	
	private Sound soundGDX;
	private long idSound;
	private Music musicGDX;
	private boolean playing;
	
	private CGTSound(){}
	public CGTSound(String filename) {
		this(new CGTFile(filename));
	}
	
	public CGTSound(String filename, float volume){
		this.file = new CGTFile(filename);
		this.volume = volume;
	}
	
	public CGTSound(CGTFile file) {
		this.file = file;
		soundGDX = null;
		musicGDX = null;
		volume = 1f;
		idSound = -1;
	}

	public CGTSound(CGTFile file, float volume) {
		this.file = file;
		this.volume = volume;
	}
	
 	public Music getMusic() {
 		if (musicGDX == null) {
 			musicGDX = Gdx.audio.newMusic(file.getFileHandle());
 			musicGDX.setVolume(volume);
 		}
		return musicGDX;
	}
 	
 	public Sound getSound() {
 		if (soundGDX == null) {
 			soundGDX = Gdx.audio.newSound(file.getFileHandle());
 		}
		return soundGDX;
	}
 	
 	public float getVolume() {
 		return volume;
 	}
 	
 	public void setVolume(float volume){
 		this.volume = volume;
 	}
 	
 	public CGTSound clone() {
 		CGTSound res = new CGTSound(file);
 		res.setVolume(volume);
 		return res;
 	}

	public CGTFile getFile() {
		return file;
	}
	
	public long getIdSound() {
 		return idSound;
 	}
 	
 	public void setIdSound(long id) {
 		idSound = id;
 	}
	public boolean isPlaying() {
		return playing;
	}
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
 	
 	
 	
}
