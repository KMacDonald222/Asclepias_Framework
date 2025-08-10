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
		if (App.Input.keyboard.isKeyDown(KeyboardKey.A)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "A key down");
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.B)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "B key pressed");
		}
		if (App.Input.keyboard.isKeyReleased(KeyboardKey.C)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "C key released");
		}
		if (App.Input.mouse.isButtonDown(MouseButton.LEFT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Left mouse ",
					"button down");
		}
		if (App.Input.mouse.isButtonPressed(MouseButton.MIDDLE)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Middle mouse ",
					"button pressed");
		}
		if (App.Input.mouse.isButtonReleased(MouseButton.RIGHT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Right mouse ",
					"button released");
		}
		if (App.Input.mouse.isScrollWheelMoved()) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Mouse scrolled ",
					App.Input.mouse.getPreviousScrollDistance(), " -> ",
					App.Input.mouse.getScrollDistance());
		}
		if (App.Input.mouse.isCursorMoved()) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Mouse cursor ",
					"moved ", App.Input.mouse.getPreviousCursorPosition(),
					" -> ", App.Input.mouse.getCursorPosition());
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
