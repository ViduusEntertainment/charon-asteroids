/**
 * Copyright 2017-2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 23, 2018 by Cody Barnes
 */
package org.viduus.charon.asteroids;

import org.viduus.charon.global.util.properties.templates.PropertyTemplate;

/**
 * 
 *
 * @author Ethan Toney
 */
public class GameConstants {
	
	public static interface EngineFlags {
		
		public static final PropertyTemplate
				// WorldEngine
				SPAWN_WAVES = new PropertyTemplate("spawn_waves", Boolean.class);
	}

}