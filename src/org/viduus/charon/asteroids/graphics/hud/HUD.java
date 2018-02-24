package org.viduus.charon.asteroids.graphics.hud;

import org.viduus.charon.asteroids.graphics.hud.components.IconHealthBar;
import org.viduus.charon.asteroids.world.objects.characters.playable.PlayerCharacter;
import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.graphics.opengl.OpenGLGraphics;
import org.viduus.charon.global.graphics.opengl.font.OpenGLFont;
import org.viduus.charon.global.graphics.ui.HeadsUpDisplay;
import org.viduus.charon.global.player.PlayerParty;

public class HUD extends HeadsUpDisplay{

	private IconHealthBar health_bar;
	
	public HUD(AbstractGameSystems game_systems) {
		super(game_systems);
		
		health_bar = new IconHealthBar(game_systems);
	}

	@Override
	public void render(OpenGLGraphics graphics, float d_sec, PlayerParty players) {
		PlayerCharacter player = (PlayerCharacter)players.get(0);
		String score = "" + player.getScore();
		float scale = 2.0f;
		OpenGLFont.setFontColor(1, 1, 1, 1);
		OpenGLFont.drawString2D(graphics, score, 0, 20, scale);
		
		health_bar.render(graphics, 10, 40, d_sec, players);
	}

}
