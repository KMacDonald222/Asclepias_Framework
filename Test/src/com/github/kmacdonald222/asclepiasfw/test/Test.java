package com.github.kmacdonald222.asclepiasfw.test;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppConfig;

import com.github.kmacdonald222.asclepiasfw.test.scenes.TestScene1;
import com.github.kmacdonald222.asclepiasfw.test.scenes.TestScene2;

public class Test {
	
	public static TestScene1 Scene1 = null;
	public static TestScene2 Scene2 = null;
	
	public static void main(String[] args) {
		Scene1 = new TestScene1();
		Scene2 = new TestScene2();
		AppConfig config = new AppConfig(Scene1);
		if (!App.Initialize(config)) {
			return;
		}
		App.Run();
		if (!App.Destroy()) {
			return;
		}
	}
	
}