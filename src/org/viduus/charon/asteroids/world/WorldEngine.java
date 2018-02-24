package org.viduus.charon.asteroids.world;

import java.util.Arrays;

import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.GameSystems;
import org.viduus.charon.asteroids.physics.WorldBounds;
import org.viduus.charon.asteroids.world.objects.characters.playable.PlayerCharacter;
import org.viduus.charon.asteroids.world.regions.Region;
import org.viduus.charon.asteroids.world.weapon.range.DefaultGun;
import org.viduus.charon.global.GameConstants.GlobalEngineFlags;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.GameInfo;
import org.viduus.charon.global.graphics.util.Size;
import org.viduus.charon.global.physics.twodimensional.listeners.CollisionListener;
import org.viduus.charon.global.player.PlayerParty;
import org.viduus.charon.global.util.identification.Uid;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D;
import org.viduus.charon.global.world.regions.BaseRegion;

public class WorldEngine extends AbstractWorldEngine {
	
	private Size world_size;

	public WorldEngine(int fps) {
		super(fps, new WorldEventEngine());
	}

	@Override
	protected String getNpcResolverClassPath() {
		return "";
	}

	@Override
	protected String getRegionResolverClassPath() {
		return "";
	}

	@Override
	protected String getCharacterResolverClassPath() {
		return "";
	}

	@Override
	protected String getWeaponResolverClassPath() {
		return "";
	}

	@Override
	protected String getBulletResolverClassPath() {
		return "";
	}

	@Override
	protected String getEffectResolverClassPath() {
		return "";
	}
	
	public Size getWorldSize() {
		return world_size;
	}
	
	@Override
	protected void onLoadGame(GameInfo game_info) {
		super.onLoadGame(game_info);
		
		// Create all the regions
		BaseRegion[] regions = new BaseRegion[] {
			new Region(game_systems, game_info.party)
		};
		Arrays.stream(regions).forEach(region -> {
			insert(region);
			region.load();
		});
		
		// Load in demo Main Character information
		PlayableCharacter2D main_character = game_info.party.get(0);
		main_character.set(Property.CURRENT_REGION_ID, new Uid("vid:region:region"));
		
		// Load demo region and put player in region
		BaseRegion character_region = (BaseRegion) resolve((Uid) main_character.get(Property.CURRENT_REGION_ID));
		for (PlayableCharacter2D party_member : game_info.party) {
			character_region.queueEntityForAddition(party_member);
		}
	}

	@Override
	protected World createWorld() {
		float world_height = game_systems.graphics_engine.getScreenSize().height;
		float world_width = game_systems.graphics_engine.getScreenSize().width;
		
		World world = new World(new WorldBounds(new AABB(0, 0, world_width, world_height), this));
//		world.setGravity(new Vector2(0, org.viduus.charon.global.GameConstants.PIXELS_PER_METER * 9.8));
		world.addListener(new CollisionListener(this));
		
		Settings settings = new Settings();
		settings.setAutoSleepingEnabled(false);
		settings.setMaximumTranslation(Double.MAX_VALUE);
		
		world.setSettings(settings);
		
		world_size = new Size(world_width, world_height);
		
		return world;
	}

	@Override
	protected void onSaveAndDisposeEngine() {}

	@Override
	protected void onMainThreadStart() {
		Size screen_size = game_systems.graphics_engine.getScreenSize();
		PlayerParty party = new PlayerParty();
		
		// add player to party
		PlayerCharacter character_1 = new PlayerCharacter((GameSystems)game_systems, "Sauran", new Vector2(350, 200));
		DefaultGun character_1_primary = new DefaultGun(game_systems.world_engine, "Primary Weapon", character_1);
		game_systems.world_engine.insert(character_1_primary);
		game_systems.world_engine.insert(character_1);
		character_1.equipPrimaryWeapon(character_1_primary);
		party.add(character_1);
		
		// start the game
		GameInfo game_info = new GameInfo(GameSystems.GAME, party);
		game_systems.startGame(game_info);
		
		game_systems.world_engine.enable(GlobalEngineFlags.TICK_WORLD);
	}
}
