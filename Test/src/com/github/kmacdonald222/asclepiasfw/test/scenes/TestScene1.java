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
		if (App.Keyboard.isKeyPressed(KeyboardKey.TWO)) {
			if (!App.SetCurrentScene(Test.Scene2)) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Changing to ",
						"test scene 2");
			}
		}
		if (App.Keyboard.isKeyDown(KeyboardKey.A)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "A key is down");
		}
		if (App.Keyboard.wasKeyDown(KeyboardKey.A)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "A key was down");
		}
		if (App.Keyboard.isKeyPressed(KeyboardKey.B)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "B key pressed");
		}
		if (App.Keyboard.isKeyReleased(KeyboardKey.C)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "C key released");
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
