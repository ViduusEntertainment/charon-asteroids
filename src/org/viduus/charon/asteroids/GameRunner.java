/**
 * Copyright 2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 21, 2018 by Cody Barnes
 */
package org.viduus.charon.asteroids;

import org.viduus.charon.asteroids.audio.AudioEngine;
import org.viduus.charon.asteroids.event.EventEngine;
import org.viduus.charon.asteroids.graphics.GraphicsEngine;
import org.viduus.charon.asteroids.world.WorldEngine;
import org.viduus.charon.global.input.InputEngine;
import org.viduus.charon.global.input.controller.ControllerState;
import org.viduus.charon.global.util.systems.SystemsEngine;

/**
 * @author ethan
 *
 */
public class GameRunner {
	
	private GameSystems game_systems;

	/**
	 * TODO                                                                                                                                                                                                                                                                                                                                                                                                   
	 */
	public GameRunner() {
		game_systems = new GameSystems(
				new AudioEngine(),
				new SystemsEngine(),
				new WorldEngine(60),
				new InputEngine(),
				new EventEngine(),
				new GraphicsEngine()
		);
		
		game_systems.input_engine.registerListener(0, "default-controls", (ControllerState e) -> {
			if( e.getKeyState(ControllerState.EXIT_GAME_KEY) == ControllerState.PRESSED_STATE ){
				game_systems.closeApplication();
			}
		});
		
		// Will enter a semi-infinite loop here until the game is stopped
		game_systems.enterGameLoop();
		
		// FIXME: Force the game to close
		System.exit(0);
	}

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "true");
		
		new GameRunner();
	}

}
