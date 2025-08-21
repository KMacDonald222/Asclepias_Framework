package com.github.kmacdonald222.asclepiasfw.testclient.scenes;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.data.Vector2D;
import com.github.kmacdonald222.asclepiasfw.input.KeyboardKey;
import com.github.kmacdonald222.asclepiasfw.input.MouseButton;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;
import com.github.kmacdonald222.asclepiasfw.testclient.TestClient;
import com.github.kmacdonald222.asclepiasfw.testnetworking.PositionMessage;

public class TestClientScene1 extends AppScene {
	
	private int nextMessageID = 0;

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
			if (!App.SetCurrentScene(TestClient.Scene2)) {
				App.Log.write(LogSource.Scene, LogPriority.Info, "Changing to ",
						"test scene 2");
			}
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.C)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Connecting");
			App.Network.connect("localhost", 2773, 10000);
		}
		if (App.Input.keyboard.isKeyPressed(KeyboardKey.D)) {
			App.Log.write(LogSource.Scene, LogPriority.Info, "Disconnecting");
			App.Network.disconnect();
		}
		if (App.Network.isConnected()) {
			if (App.Input.mouse.isButtonPressed(MouseButton.LEFT)) {
				Vector2D mp = App.Input.mouse.getCursorPosition();
				PositionMessage message = new PositionMessage(nextMessageID++,
						mp);
				if (App.Network.send(message)) {
					App.Log.write(LogSource.Scene, LogPriority.Info, "Sending ",
							"position message");
				} else {
					App.Log.write(LogSource.Scene, LogPriority.Warning,
							"Failed to send position message");
				}
			}
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
	@Override
	public boolean netConnected(int ID) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Network connected");
		return true;
	}
	@Override
	public void netMessageReceived(NetMessage message) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Network message ",
				"received: ", message);
	}
	@Override
	public void netDisconnected(int ID) {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Network ",
				"disconnected");
	}

}
