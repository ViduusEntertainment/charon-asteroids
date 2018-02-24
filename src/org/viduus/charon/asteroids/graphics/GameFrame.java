/**
 * Copyright 2017-2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 21, 2018 by Cody Barnes
 */
package org.viduus.charon.asteroids.graphics;

import org.viduus.charon.global.graphics.GraphicsSettings;
import org.viduus.charon.global.graphics.opengl.OpenGLFrame;
import org.viduus.charon.global.graphics.util.IntDimension;

public class GameFrame extends OpenGLFrame {

	private static final int CANVAS_SIZE = 400;

	public GameFrame() {
		super("", true, false, true);
		
		GraphicsSettings settings = getGraphicsSettings();
		double scale = settings.getSettingAsDouble("magnification");
		setCanvasSize(true, new IntDimension(0, 0, (int) (CANVAS_SIZE*scale), (int) (CANVAS_SIZE*scale)));
		
		setLocationRelativeTo(null);
		setVisible(true);
		setFPSVisible(false);
		setDefaultCloseOperation(OpenGLFrame.KILL_ON_CLOSE);
	}
	
}