package com.github.kmacdonald222.asclepiasfw.test.scenes;

import java.util.ArrayList;
import java.util.List;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppScene;
import com.github.kmacdonald222.asclepiasfw.input.KeyboardKey;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;
import com.github.kmacdonald222.asclepiasfw.test.Test;

public class TestScene1 extends AppScene {
	
	private double timer = 0;
	private double frames = 0;
	private boolean timerStarted = false;
	private long realStartTime = 0;
	private long endTime = 60 * 30;
	private int realUpdates = 0;
	private List<Double> deltas = null;
	private List<Double> timeDiffs = null;

	@Override
	public boolean initialize() {
		App.Log.write(LogSource.Scene, LogPriority.Info, "Initializing test ",
				"scene 1");
		deltas = new ArrayList<Double>();
		timeDiffs = new ArrayList<Double>();
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
		if (System.currentTimeMillis() - realStartTime >= (endTime * 1000)
				&& timerStarted) {
			return false;
		}
		return true;
	}
	@Override
	public void timedUpdate(double delta) {
		if (!timerStarted) {
			realStartTime = System.currentTimeMillis();
			timerStarted = true;
		}
		realUpdates++;
		deltas.add(delta);
		timer += delta;
		frames += delta;
		if (timer >= (double)App.GetTargetUpdatesPerSecond()) {
			timer = 0.0d;
			double virtualSeconds = frames
					/ (double)App.GetTargetUpdatesPerSecond();
			double realSeconds = (double)(System.currentTimeMillis()
					- realStartTime) / 1000.0d;
			timeDiffs.add(virtualSeconds - realSeconds);
			double avg = 0.0d;
			for (double timeDiff : timeDiffs) {
				avg += timeDiff;
			}
			avg /= (double)timeDiffs.size();
			App.Log.write(LogSource.Scene, LogPriority.Info, virtualSeconds,
					" virtual seconds elapsed, ", realSeconds, " real seconds ",
					"elapsed, ", realUpdates, " update calls, average time ",
					"difference=", avg, ", deltas=", deltas);
			deltas.clear();
			realUpdates = 0;
		}
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
