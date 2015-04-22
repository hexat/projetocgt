package com.projetocgt;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import cgt.core.CGTGameObject;
import cgt.game.CGTGameWorld;
import cgt.util.CGTSound;

public class AudioManager {
	public HashMap<CGTGameObject, CGTSound> objectsSounds;
	private Music gameMusic;
	private Music winMusic;
	private Music loseMusic;
	private Sound soundActorDie;

	private CGTGameWorld world;

	public AudioManager(CGTGameWorld world) {
		this.world = world;
		objectsSounds=new HashMap<CGTGameObject, CGTSound>();

		if (world.getMusic() != null) {
			this.gameMusic = Gdx.audio.newMusic(world.getMusic().getFile().getFileHandle());
		} else {
			this.gameMusic = null;
		}
		if (world.getSoundWin() != null) {
			this.winMusic = Gdx.audio.newMusic(world.getSoundWin().getFile().getFileHandle());
		} else {
			this.winMusic = null;
		}

        this.loseMusic = null;

		soundActorDie = (Sound) world.getActor().getSoundDie();
	}

	public void playSound(final CGTGameObject object) {
		if (objectsSounds.get(object) == null) {
			if(!object.getSound().isPlaying()){
				objectsSounds.put(object, object.getSound());
				final long id = object.getSound().getSound().loop(object.getSound().getVolume());
				object.getSound().setIdSound(id);
				object.getSound().setPlaying(true);
			}
		}
	}


	public void setVolumeSound(CGTGameObject object, float volume) {
		CGTSound s = objectsSounds.get(object);
		if (s != null) {
			s.getSound().setVolume(s.getIdSound(), volume);
		}
	}


	public void stopSound(CGTGameObject object) {
		CGTSound s = objectsSounds.get(object);
		if (s != null) {
			s.getSound().stop();
			objectsSounds.remove(object);
			object.getSound().setPlaying(false);
		}
	}

	public void stopAll() {
		for (CGTSound s : objectsSounds.values()) {
			s.getSound().stop();
		}
	}


	public void stopAndDisposeAll() {
		for (CGTSound s : objectsSounds.values()) {
			if (s != null){
				s.getSound().stop();
				//s.dispose();
				s = null;
			}
		}
		if (gameMusic != null) {
			gameMusic.stop();
			gameMusic.dispose();
		}
		if (winMusic != null) {
			winMusic.stop();
			winMusic.dispose();
		}
		if (loseMusic != null) {
			loseMusic.stop();
			loseMusic.dispose();
		}

		objectsSounds.clear();
	}

	public void playGameMusic() {
		if (gameMusic != null && !gameMusic.isPlaying()) {
			gameMusic.setLooping(true);
			gameMusic.play();
		}
	}
	public void playWinMusic() {
		if (winMusic != null && !gameMusic.isPlaying()) {
			winMusic.play();
			winMusic.setLooping(false);
		}
	}
	public void playLoseMusic() {
        if (world.getSoundLose() != null) {
            this.loseMusic = Gdx.audio.newMusic(world.getSoundLose().getFile().getFileHandle());
            if (loseMusic != null && !gameMusic.isPlaying()) {
                loseMusic.play();
                loseMusic.setLooping(false);
            }
        }
	}
	
	public void stopGameMusic() {
		if (gameMusic != null) {
			gameMusic.stop();
		}
	}

	public void pauseGameMusic() {
		if (gameMusic != null) {
			gameMusic.stop();
		}
	}
	
	public void playSoundActorDie() {
		if (soundActorDie != null) {
			soundActorDie.play();
		}
	}
}
