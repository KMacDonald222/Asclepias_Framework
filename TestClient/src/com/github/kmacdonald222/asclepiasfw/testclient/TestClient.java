package com.github.kmacdonald222.asclepiasfw.testclient;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppConfig;
import com.github.kmacdonald222.asclepiasfw.testclient.scenes.TestClientScene1;
import com.github.kmacdonald222.asclepiasfw.testclient.scenes.TestClientScene2;

public class TestClient {
	
	public static TestClientScene1 Scene1 = null;
	public static TestClientScene2 Scene2 = null;
	
	public static void main(String[] args) {
		Scene1 = new TestClientScene1();
		Scene2 = new TestClientScene2();
		AppConfig config = new AppConfig(Scene1);
		if (!App.Initialize(config)) {
			System.exit(1);
		}
		App.Run();
		if (!App.Destroy()) {
			System.exit(2);
		}
		System.exit(0);
	}
	
}