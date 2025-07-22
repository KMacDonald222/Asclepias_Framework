package com.github.kmacdonald222.asclepiasfw.test.scenes;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.input.KeyboardKey;
import com.github.kmacdonald222.asclepiasfw.input.MouseButton;
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
		if (App.Input.mouse.isButtonPressed(MouseButton.LEFT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Playing sound ",
					"effect 1");
			if (!App.SoundEffects.playEffect("Assets/audio/effect_1.wav")) {
				App.Log.write(LogSource.Scene, LogPriority.Warning, "Failed ",
						"to play sound effect 1");
			}
		}
		if (App.Input.mouse.isButtonPressed(MouseButton.MIDDLE)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Playing sound ",
					"effect 2");
			if (!App.SoundEffects.playEffect("Assets/audio/effect_2.wav")) {
				App.Log.write(LogSource.Scene, LogPriority.Warning, "Failed ",
						"to play sound effect 2");
			}
		}
		if (App.Input.mouse.isButtonPressed(MouseButton.RIGHT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Playing sound ",
					"effect 3");
			if (!App.SoundEffects.playEffect("Assets/audio/effect_3.wav")) {
				App.Log.write(LogSource.Scene, LogPriority.Warning, "Failed ",
						"to play sound effect 3");
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.UP)) {
			App.SoundEffects.setVolume(App.SoundEffects.getVolume() + 0.1d);
			App.Log.write(LogSource.Scene, LogPriority.Info, "Set volume ",
					App.SoundEffects.getVolume());
		} else if (App.Input.keyboard.isKeyPressed(KeyboardKey.DOWN)) {
			App.SoundEffects.setVolume(App.SoundEffects.getVolume() - 0.1d);
			App.Log.write(LogSource.Scene, LogPriority.Info, "Set volume ",
					App.SoundEffects.getVolume());
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
