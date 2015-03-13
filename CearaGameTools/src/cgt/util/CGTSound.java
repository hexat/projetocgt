package cgt.util;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class CGTSound implements Serializable
{
	private CGTFile file;
	private float volume;
	
	private Music musicGDX;
	
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
		musicGDX = null;
		volume = 1f;
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
}
