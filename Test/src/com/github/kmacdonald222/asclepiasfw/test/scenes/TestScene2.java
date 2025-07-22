package com.github.kmacdonald222.asclepiasfw.test.scenes;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.input.KeyboardKey;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

import com.github.kmacdonald222.asclepiasfw.test.Test;

public class TestScene2 extends AppScene {

	@Override
	public boolean initialize() {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Initializing test ",
				"scene 2");
		return true;
	}
	@Override
	public void enter(AppScene previousScene) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Entering test scene ",
				"2");
	}
	@Override
	public boolean processInput() {
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.ONE)) {
			if (!App.SetCurrentScene(Test.Scene1)) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Changing to ",
						"test scene 1");
			}
		}
		return true;
	}
	@Override
	public void leave(AppScene nextScene) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Leaving test scene ",
				"2");
	}
	@Override
	public boolean destroy() {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Destroying test ",
				"scene 2");
		return true;
	}
	
}
