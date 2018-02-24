package org.viduus.charon.asteroids.graphics.frames;

import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.GameConstants.GlobalEngineFlags;
import org.viduus.charon.global.graphics.opengl.OpenGLFrame;
import org.viduus.charon.global.graphics.opengl.OpenGLGraphics;
import org.viduus.charon.global.graphics.opengl.font.OpenGLFont;
import org.viduus.charon.global.graphics.screens.AbstractGameScreen;
import org.viduus.charon.global.graphics.util.IntDimension;

public class GameScreen extends AbstractGameScreen {

	public GameScreen(OpenGLFrame graphics_frame) {
		super(graphics_frame);
		graphics_frame.setOpaqueBackground(false);
	}

	@Override
	protected void onActivate(int previous_screen_id, AbstractGameSystems game_systems) {

	}

	@Override
	protected void onDeactivate(AbstractGameSystems game_systems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateLayout(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(OpenGLGraphics graphics, float d_sec) {
		if (game_systems.graphics_engine.enabled(GlobalEngineFlags.RENDER_PLAYER_HUD))
			return;
		
		IntDimension canvas_dimension = graphics.getCanvasDimension();
		int screen_width = canvas_dimension.width;
		int screen_height = canvas_dimension.height;
		
		String menu_message = "1 COIN  |  PLAY";
		float menu_message_scale = 2.0f;
		OpenGLFont.setFontColor(1, 1, 1, 1);
		OpenGLFont.drawString2D(graphics, menu_message, screen_width / 2 - (int)OpenGLFont.getStringWidth(menu_message, menu_message_scale) / 2, screen_height - 100, menu_message_scale);
		
//		String copyright_message = "© 1979 ATARI INC";
//		float copyright_message_scale = 1.0f;
//		OpenGLFont.drawString2D(graphics, copyright_message, screen_width / 2 - (int)OpenGLFont.getStringWidth(copyright_message, copyright_message_scale) / 2, screen_height - 30, copyright_message_scale);
	}
}
