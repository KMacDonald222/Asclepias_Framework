package com.github.kmacdonald222.asclepiasfw.test.scenes;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.data.Vec2D;
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
		if (App.Keyboard.isKeyPressed(KeyboardKey.TWO)) {
			if (!App.SetCurrentScene(Test.Scene2)) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Changing to ",
						"test scene 2");
			}
		}
		if (App.Mouse.isButtonPressed(MouseButton.LEFT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Left mouse ",
					"button pressed");
		}
		if (App.Mouse.isButtonReleased(MouseButton.RIGHT)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Right mouse ",
					"button released");
		}
		if (App.Mouse.isScrollWheelMoved()) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Mouse scrool ",
					"wheel moved ", App.Mouse.getScrollDistance());
		}
		if (App.Mouse.isCursorMoved()
				&& App.Keyboard.isKeyDown(KeyboardKey.M)) {
			Vec2D cpos = App.Mouse.getCursorPosition();
			Vec2D ppos = App.Mouse.getPreviousCursorPosition();
			App.Log.write(LogSource.Scene, LogPriority.Info, "Mouse cursor ",
					"moved ", ppos, " -> ", cpos);
		}
		if (App.Keyboard.isKeyPressed(KeyboardKey.F)) {
			App.Window.setFullscreen(!App.Window.isFullscreen());
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
