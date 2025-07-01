package com.github.kmacdonald222.asclepiasfw.test;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppConfig;
import com.github.kmacdonald222.asclepiasfw.data.Vec2D;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

public class Test {
	
	public static void main(String[] args) {
		AppConfig config = new AppConfig();
		if (!App.Initialize(config)) {
			return;
		}
		Vec2D v1 = new Vec2D(3.0d, 5.0d);
		Vec2D v2 = new Vec2D(7.0d, 11.0d);
		App.Log.write(LogSource.App, LogPriority.Info,
				"v1 = ", v1, ", v2 = ", v2);
		App.Log.write(LogSource.App, LogPriority.Info,
				"v1 + v2 = ", v1.add(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"v1 - v2 = ", v1.subtract(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"3 * v1 = ", v1.scale(3.0d));
		App.Log.write(LogSource.App, LogPriority.Info,
				"sum(v1) = ", v1.sum());
		App.Log.write(LogSource.App, LogPriority.Info,
				"v1 * v2 = ", v1.multiply(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"v1 dot v2 = ", v1.dot(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"magnitude(v1) = ", v1.magnitude());
		App.Log.write(LogSource.App, LogPriority.Info,
				"distance(v1, v2) = ", v1.distance(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"normalize(v1) = ", v1.normalize());
		App.Log.write(LogSource.App, LogPriority.Info,
				"angle(v1, v2) = ", v1.angle(v2));
		App.Log.write(LogSource.App, LogPriority.Info,
				"rotate(v1, pi/4) = ", v1.rotate(Math.PI / 4.0d));
		App.Log.write(LogSource.App, LogPriority.Info,
				"rotate(v1, pi/4, v2) = ", v1.rotate(Math.PI / 4.0d, v2));
		if (!App.Destroy()) {
			return;
		}
	}
	
}