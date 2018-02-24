/**
 * Copyright 2017-2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 21, 2018 by Cody Barnes
 */
package org.viduus.charon.asteroids.graphics;

import org.viduus.charon.asteroids.GameSystems;
import org.viduus.charon.asteroids.graphics.frames.GameScreen;
import org.viduus.charon.global.graphics.opengl.OpenGLFrame;
import org.viduus.charon.global.graphics.screens.AbstractScreenManager;

/**
 * 
 *
 * @author Ethan Toney
 */
public class ScreenManager extends AbstractScreenManager {

	/**
	 * TODO
	 * @param game_systems
	 */
	public ScreenManager(GameSystems game_systems, OpenGLFrame graphics_frame) {
		super(new GameScreen(graphics_frame));
	}

}