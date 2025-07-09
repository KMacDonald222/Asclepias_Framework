/*
 * File:		WindowManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.02
 * Purpose:		Defines the main class of the window management system for
 * 				Asclepias Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.graphics;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.data.Vec2D;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the window management system for Asclepias Framework
// applications
public class WindowManager {

	// Whether the window management system has been initialized
	private boolean initialized = false;
	// Java Swing handle for the application's window
	private JFrame windowHandle = null;
	// Java Swing handle for the content pane of the application's window
	private JPanel windowPanel = null;
	// Whether the window's close button has been clicked
	private boolean windowClosing = false;
	// The current title of the window
	private String title = "";
	// The current dimensions of the window
	private Vec2D dimensions = null;
	// The dimensions of the window for the next time it is set to windowed mode
	private Vec2D windowedDimensions = null;
	// Whether the window currently appears in fullscreen mode
	private boolean fullscreen = false;
	// The index of the monitor the window currently appears on
	private int monitorIndex = 0;
	
	/*
	 * Open a window and set its content pane and attributes
	 * @param String title - The initial title for the window
	 * @param Vec2D dimensions - The initial dimensions for the window
	 * @param boolean fullscreen - Whether the window should initially appear in
	 * fullscreen mode
	 * @param int monitorIndex - The index of the monitor the window should
	 * initially appear on
	 * @return boolean - Whether the window was successfully opened
	 */
	public boolean initialize(String title, Vec2D dimensions,
			boolean fullscreen, int monitorIndex) {
		if (initialized) {
			return false;
		}
		windowHandle = new JFrame();
		windowHandle.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		windowHandle.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.Log.write(LogSource.Window, LogPriority.Info, "Window ",
						"requested to close");
				windowClosing = true;
			}
		});
		windowHandle.setResizable(false);
		windowPanel = new JPanel();
		windowHandle.setContentPane(windowPanel);
		windowHandle.setVisible(true);
		App.Log.write(LogSource.Window, LogPriority.Info, "Opened new window");
		windowClosing = false;
		setTitle(title);
		setDimensions(dimensions);
		setFullscreen(fullscreen);
		if (!setMonitorIndex(monitorIndex)) {
			App.Log.write(LogSource.Window, LogPriority.Error, "Invalid ",
					"initial monitor index");
			return false;
		}
		App.Log.write(LogSource.Window, LogPriority.Info, "Set initial window ",
				"attributes");
		initialized = true;
		return initialized;
	}
	/*
	 * Close the window and free the window management system's memory
	 * @return boolean - Whether the window management system was successfully
	 * destroyed
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.Window, LogPriority.Info, "Freeing window ",
				"memory");
		windowPanel = null;
		windowHandle.dispose();
		windowHandle = null;
		windowClosing = false;
		title = "";
		dimensions = null;
		windowedDimensions = null;
		fullscreen = false;
		monitorIndex = 0;
		initialized = false;
		return success;
	}
	/*
	 * Center the window on its monitor
	 */
	private void center() {
		Vec2D mp = getMonitorPosition();
		Vec2D md = getMonitorDimensions();
		windowHandle.setLocation(
				(int)(mp.x + ((md.x - dimensions.x) / 2.0d)),
				(int)(mp.y + ((md.y - dimensions.y) / 2.0d)));
	}
	
	/*
	 * Test whether the window's close button has been clicked
	 * @return boolean - Whether the window's close button has been clicked
	 */
	public boolean isWindowClosing() {
		return windowClosing;
	}
	/*
	 * Get the Java handle (JFrame) for the window
	 * @return JFrame - The window handle
	 */
	public JFrame getWindowHandle() {
		return windowHandle;
	}
	/*
	 * Get the current title of the window
	 * @return String - The current title of the window
	 */
	public String getTitle() {
		return title;
	}
	/*
	 * Set the title of the window
	 * @param String title - The new title for the window
	 */
	public void setTitle(String title) {
		App.Log.write(LogSource.Window, LogPriority.Info, "Setting window ",
				"title \"", title, "\"");
		windowHandle.setTitle(title);
		this.title = title;
	}
	/*
	 * Get the current dimensions of the window
	 * @return Vec2D - The current dimensions of the window
	 */
	public Vec2D getDimensions() {
		return dimensions;
	}
	/*
	 * Set the dimensions of the window if in windowed mode
	 * @param Vec2D dimensions - The new dimensions for the window
	 */
	public void setDimensions(Vec2D dimensions) {
		App.Log.write(LogSource.Window, LogPriority.Info, "Setting window ",
				"dimensions ", dimensions);
		if (fullscreen) {
			App.Log.write(LogSource.Window, LogPriority.Warning, "Window is ",
					"in fullscreen mode");
			windowedDimensions = dimensions;
			return;
		}
		windowPanel.setPreferredSize(new Dimension(
				(int)dimensions.x, (int)dimensions.y));
		windowHandle.setVisible(false);
		windowHandle.pack();
		this.dimensions = dimensions;
		windowHandle.setVisible(true);
		center();
	}
	/*
	 * Test whether the window is currently in fullscreen mode
	 * @return boolean - Whether the window is currently in fullscreen mode
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}
	/*
	 * Set whether the window should appear in fullscreen mode
	 * @param boolean fullscreen - Whether the window should appear in
	 * fullscreen mode
	 */
	public void setFullscreen(boolean fullscreen) {
		GraphicsDevice monitor = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getScreenDevices()[monitorIndex];
		if (!this.fullscreen && fullscreen) {
			App.Log.write(LogSource.Window, LogPriority.Info, "Setting window ",
					"to fullscreen mode");
			windowedDimensions = dimensions;
			setDimensions(getMonitorDimensions());
			monitor.setFullScreenWindow(windowHandle);
			this.fullscreen = true;
		} else if (this.fullscreen && !fullscreen) {
			App.Log.write(LogSource.Window, LogPriority.Info, "Setting window ",
					"to windowed mode");
			this.fullscreen = false;
			monitor.setFullScreenWindow(null);
			setDimensions(windowedDimensions);
		}
	}
	/*
	 * Get the number of monitors available to the window
	 * @return int - The number of monitors available to the window
	 */
	public int getMonitorCount() {
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		return monitors.length;
	}
	/*
	 * Get the name of the window's current monitor
	 * @return String - The name of the window's current monitor
	 */
	public String getMonitorName() {
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		return monitors[monitorIndex].getIDstring();
	}
	/*
	 * Get the name of an available monitor
	 * @param int monitorIndex - The index of the monitor to test
	 * @return String - The name of the monitor
	 */
	public String getMonitorName(int monitorIndex) {
		if (monitorIndex < 0 || monitorIndex >= getMonitorCount()) {
			return "";
		}
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		return monitors[monitorIndex].getIDstring();
	}
	/*
	 * Get the position of the window's current monitor
	 * @return Vec2D - The position of the window's current monitor
	 */
	public Vec2D getMonitorPosition() {
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		Rectangle bounds
				= monitors[monitorIndex].getDefaultConfiguration().getBounds();
		return new Vec2D(bounds.x, bounds.y);
	}
	/*
	 * Get the position of an available monitor
	 * @param int monitorIndex - The index of the monitor to test
	 * @return Vec2D - The position of the monitor
	 */
	public Vec2D getMonitorPosition(int monitorIndex) {
		if (monitorIndex < 0 || monitorIndex >= getMonitorCount()) {
			return new Vec2D();
		}
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		Rectangle bounds
				= monitors[monitorIndex].getDefaultConfiguration().getBounds();
		return new Vec2D(bounds.x, bounds.y);
	}
	/*
	 * Get the dimensions of the window's current monitor
	 * @return Vec2D - The dimensions of the window's current monitor
	 */
	public Vec2D getMonitorDimensions() {
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		Rectangle bounds
				= monitors[monitorIndex].getDefaultConfiguration().getBounds();
		return new Vec2D(bounds.width, bounds.height);
	}
	/*
	 * Get the dimensions of an available monitor
	 * @param int monitorIndex - The index of the monitor to test
	 * @return vec2D - The dimensions of the monitor
	 */
	public Vec2D getMonitorDimensions(int monitorIndex) {
		if (monitorIndex < 0 || monitorIndex >= getMonitorCount()) {
			return new Vec2D();
		}
		GraphicsEnvironment environment
				= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] monitors = environment.getScreenDevices();
		Rectangle bounds
				= monitors[monitorIndex].getDefaultConfiguration().getBounds();
		return new Vec2D(bounds.width, bounds.height);
	}
	/*
	 * Get the index of the window's current monitor
	 * @return int - The index of the window's current monitor
	 */
	public int getMonitorIndex() {
		return monitorIndex;
	}
	/*
	 * Set the monitor the window should appear on
	 * @param int monitorIndex - The index of the monitor the window should
	 * appear on
	 * @return boolean - Whether the window's monitor could be set
	 */
	public boolean setMonitorIndex(int monitorIndex) {
		App.Log.write(LogSource.Window, LogPriority.Info, "Setting monitor ",
				"index ", monitorIndex);
		if (monitorIndex < 0 || monitorIndex >= getMonitorCount()) {
			App.Log.write(LogSource.Window, LogPriority.Warning, "Monitor ",
					"index out of bounds");
			return false;
		}
		this.monitorIndex = monitorIndex;
		if (fullscreen) {
			setFullscreen(false);
			setFullscreen(true);
		}
		center();
		return true;
	}
	
}
