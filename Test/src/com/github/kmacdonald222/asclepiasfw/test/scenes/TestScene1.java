package com.github.kmacdonald222.asclepiasfw.test.scenes;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.input.KeyboardKey;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

import com.github.kmacdonald222.asclepiasfw.test.Test;

public class TestScene1 extends AppScene {

	@Override
	public boolean initialize() {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Initializing test ",
				"scene 1");
		return true;
	}
	@Override
	public void enter(AppScene previousScene) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Entering test scene ",
				"1");
	}
	@Override
	public boolean processInput() {
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.TWO)) {
			if (!App.SetCurrentScene(Test.Scene2)) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Changing to ",
						"test scene 2");
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.Q)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Playing music ",
					"track 1");
			if (!App.Audio.music.play("Assets/audio/music_1.wav")) {
				App.Log.write(LogSource.Scene, LogPriority.Warning, "Failed ",
						"to play music track 1");
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.W)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Playing music ",
					"track 2");
			if (!App.Audio.music.play("Assets/audio/music_2.wav")) {
				App.Log.write(LogSource.Scene, LogPriority.Warning, "Failed ",
						"to play music track 2");
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.SPACE)) {
			if (App.Audio.music.isPaused()) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Playing ",
						"current music track");
				if (!App.Audio.music.play()) {
					App.Log.write(LogSource.Scene, LogPriority.Warning,
							"Failed to play current music track");
				}
			} else {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Pausing ",
						"current music track");
				if (!App.Audio.music.pause()) {
					App.Log.write(LogSource.Scene, LogPriority.Warning,
							"Failed to pause current music track");
				}
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.S)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Stopping ",
					"current music track");
			App.Audio.music.stop();
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.UP)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Setting volume ",
					App.Audio.music.getVolume());
			App.Audio.music.setVolume(App.Audio.music.getVolume() + 0.1d);
		} else if (App.Input.keyboard.isKeyPressed(KeyboardKey.DOWN)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Setting volume ",
					App.Audio.music.getVolume());
			App.Audio.music.setVolume(App.Audio.music.getVolume() - 0.1d);
		}
		return true;
	}
	@Override
	public void leave(AppScene nextScene) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Leaving test scene ",
				"1");
	}
	@Override
	public boolean destroy() {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Destroying test ",
				"scene 1");
		return true;
	}

}
