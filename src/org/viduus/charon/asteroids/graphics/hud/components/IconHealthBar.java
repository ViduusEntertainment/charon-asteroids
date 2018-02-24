/**
 * Copyright 2017-2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 21, 2018 Cody Barnes
 */
package org.viduus.charon.asteroids.graphics.hud.components;

import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.graphics.animation.sprite.Animation;
import org.viduus.charon.global.graphics.opengl.OpenGLGraphics;
import org.viduus.charon.global.player.PlayerParty;
import org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D;

public class IconHealthBar {
	
	private final Animation<?> anim;
	
	public IconHealthBar(AbstractGameSystems game_systems) {
		anim = (Animation<?>) game_systems.graphics_engine.resolve("vid:animation:hud/icon_health_bar.icon_health_bar-idle");
	}
	
	public void render(OpenGLGraphics graphics, int x, int y, float d_sec, PlayerParty players) {
		PlayableCharacter2D character = players.get(0);
		
		int full_icons = (int)character.getFloat(Property.HEALTH);
		
		for(int i = 0; i < full_icons; i++) {			
			anim.renderAnimation(graphics, d_sec, x + 20*i, y, 1);
		}
	}
	
}