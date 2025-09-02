package com.oop.gameengine.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class OutputSound {
	protected String fileLoc;
	protected String type; // to determine: is it background music or one time sound?
	protected Music music;
	protected Sound sound;
	protected long id;

	public OutputSound() {
		this.fileLoc = "audio/background.mp3";
		this.type = "music";
		this.music = Gdx.audio.newMusic(Gdx.files.internal(this.fileLoc));
//		System.out.println("I/O == created DEFAULT WAY: " + this.fileLoc);
	}

	public OutputSound(String fileLocation, String inputType) {
		this.fileLoc = fileLocation;
		this.type = inputType;

		if (this.type == "music") {
			try {
				this.music = Gdx.audio.newMusic(Gdx.files.internal(this.fileLoc));
				System.out.println("I/O == created MUSIC!!!" + this.fileLoc);

			} catch (Exception e) {
				System.out.println("I/O == Failed to load music file: " + this.fileLoc);
				e.printStackTrace();
			}

		} else if (this.type == "sound") {
			try {
				this.sound = Gdx.audio.newSound(Gdx.files.internal(this.fileLoc));
				System.out.println("I/O == created SOUND!!" + this.fileLoc);

			} catch (Exception e) {
				System.out.println("I/O == Failed to load sound file: " + this.fileLoc);
				e.printStackTrace();
			}

		}
	}


	// features
	public void play() {
		if (this.type == "music") {
			this.music.setLooping(true);
			this.music.play();
		} else if (this.type == "sound") {
			this.sound.play(0.2f);
		}
	}
	public void play(float vol) {
		if (this.type == "music") {
			this.music.setLooping(true);
			this.music.setVolume(vol);
			this.music.play();
		} else if (this.type == "sound") {
			this.sound.play(vol);
		}
	}
	public void stop() {
		if (this.type == "music") {
			this.music.stop();
		} else if (this.type == "sound") {
			this.sound.stop();
		}
	}
	public void pause() {
		if (this.type == "music") {
			this.music.pause();
		} else if (this.type == "sound") {
			this.sound.pause();
		}
	}

	// getter and setter
	public Object getAudio() {
		if (this.type == "sound") {
			return this.sound;
		} else {
			return this.music;
		}
	}

	public String getFileLoc() {
		return this.fileLoc;
	}

	public void setFileLoc(String fileLoc) {
		this.fileLoc = fileLoc;
		if (type == "music") {
			this.music = Gdx.audio.newMusic(Gdx.files.internal(this.fileLoc));
		} else if (type == "sound") {
			this.sound = Gdx.audio.newSound(Gdx.files.internal(this.fileLoc));
		}
	}

}
