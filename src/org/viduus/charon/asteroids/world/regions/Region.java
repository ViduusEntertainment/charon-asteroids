package org.viduus.charon.asteroids.world.regions;

import java.util.HashMap;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.GameConstants.EngineFlags;
import org.viduus.charon.asteroids.event.ObjectOutsideBoundsEvent;
import org.viduus.charon.asteroids.world.AsteroidWave;
import org.viduus.charon.asteroids.world.WorldEngine;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.Asteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.LargeAsteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.MediumAsteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.SmallAsteroid;
import org.viduus.charon.asteroids.world.objects.characters.playable.PlayerCharacter;
import org.viduus.charon.asteroids.world.weapon.bullets.DefaultBullet;
import org.viduus.charon.global.AbstractGameSystems;
import org.viduus.charon.global.GameConstants.GlobalEngineFlags;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.event.events.DeathEvent;
import org.viduus.charon.global.event.events.ObjectRemovalEvent;
import org.viduus.charon.global.event.events.TickEvent;
import org.viduus.charon.global.graphics.util.Size;
import org.viduus.charon.global.player.PlayerParty;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.world.objects.twodimensional.Object2D;
import org.viduus.charon.global.world.objects.twodimensional.weapon.Weapon2D;
import org.viduus.charon.global.world.regions.BaseRegion;

public class Region extends BaseRegion {
	
	private AbstractGameSystems game_systems;
	private HashMap<Object2D, Integer> times_outside_bounds = new HashMap<>();
	private int max_times_outside_bounds = 1;
	private AsteroidWave active_asteroid_wave;
	private AsteroidWave start_asteroid_wave;
	private final PlayerParty party;
	private final PlayerCharacter player;
	private int current_num_asteroids = 2;

	public Region(AbstractGameSystems game_systems, PlayerParty party) {
		super(game_systems.world_engine, "region", game_systems.graphics_engine.getScreenSize(), new Vector2(0, 0));
		this.game_systems = game_systems;
		this.party = party;
		this.player = (PlayerCharacter) party.get(0);
		registerEventCallback(ObjectOutsideBoundsEvent.class, this::onObjectOutsideBoundsEvent);
	}

	@Override
	protected void addObjectCallbacks(Object2D obj) {
		if (obj instanceof MediumAsteroid)
			active_asteroid_wave.addAsteroidFromSplit((Asteroid)obj);
		else if (obj instanceof SmallAsteroid)
			active_asteroid_wave.addAsteroidFromSplit((Asteroid)obj);
		world_engine.event_engine.registerEventCallback(obj, this, ObjectOutsideBoundsEvent.class);
	}

	@Override
	protected void removeObjectCallbacks(Object2D obj) {
		world_engine.event_engine.deregisterEventCallback(obj, this, ObjectOutsideBoundsEvent.class);
	}
	
	@Override
	protected void onObjectRemoval(ObjectRemovalEvent object_removal_event) {
		super.onObjectRemoval(object_removal_event);
		active_asteroid_wave.tryRemoveAsteroid(object_removal_event.object_to_remove);
		if (object_removal_event.object_to_remove instanceof Asteroid) {
			int score_before = player.getScore();
			if (object_removal_event.object_to_remove instanceof LargeAsteroid)
				player.setScore(player.getScore() + 20);
			else if (object_removal_event.object_to_remove instanceof MediumAsteroid)
				player.setScore(player.getScore() + 50);
			else if (object_removal_event.object_to_remove instanceof SmallAsteroid)
				player.setScore(player.getScore() + 100);
			int score_after = player.getScore();
			
			if (score_after % 1000 < score_before % 1000)
				player.set(Property.HEALTH, player.getFloat(Property.HEALTH) + 1.0f);
		}
	}
	
	@Override 
	protected void onObjectDeath(DeathEvent death_event) {
		current_num_asteroids = 2;
		active_asteroid_wave.removeAllAsteroidsLeft();
		world_engine.disable(EngineFlags.SPAWN_WAVES);
		game_systems.graphics_engine.disable(GlobalEngineFlags.RENDER_PLAYER_HUD);
		player.setScore(0);
		player.set(Property.HEALTH, 3.0f);
		player.setImmunityTime(Float.MAX_VALUE);
		translatePlayerToCenter();
		load();
	}
	
	protected void onObjectOutsideBoundsEvent(ObjectOutsideBoundsEvent event) {
		Object2D object = event.object;
		
		if (object instanceof DefaultBullet) {
			if (times_outside_bounds.containsKey(object))
				times_outside_bounds.put(object, times_outside_bounds.get(object) + 1);
			else
				times_outside_bounds.put(object, 1);
			
			if (object != null && times_outside_bounds.get(object) > max_times_outside_bounds) {
				queueEntityForRemoval(object);
				times_outside_bounds.remove(object);
			}
		}
		
		Vector2 location = object.getLocation().copy();
		
		if (location.x < 0)
			location.x = getWidth();
		else if (location.x > getWidth())
			location.x = 0;
		
		if (location.y < 0)
			location.y = getHeight();
		else if (location.y > getHeight())
			location.y = 0;
		
		Vector2 translation = location.subtract(object.getLocation());
		object.getBody().translate(translation);
		if (object instanceof PlayerCharacter) {
			PlayerCharacter player = (PlayerCharacter)object;
			for (Weapon2D weapon : player.getWeapons()) {
				weapon.getBody().translate(translation);
			}
		}
	}
	
	private void translatePlayerToCenter() {
		Size screen_size = ((WorldEngine)world_engine).getWorldSize();
		Vector2 translation = (new Vector2(screen_size.width / 2, screen_size.height / 2)).subtract(player.getLocation());
		player.getBody().translate(translation);
		for (Weapon2D weapon : player.getWeapons()) {
			weapon.getBody().translate(translation);
		}
	}

	@Override
	public void load() {
		start_asteroid_wave = active_asteroid_wave = new AsteroidWave(world_engine, this, 1);
		start_asteroid_wave.onTick(null);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTick(TickEvent tick_event) {
		if (start_asteroid_wave != null) {
			start_asteroid_wave.onTick(tick_event);
			
			if (start_asteroid_wave.isFinished()) {
				world_engine.enable(EngineFlags.SPAWN_WAVES);
				game_systems.graphics_engine.enable(GlobalEngineFlags.RENDER_PLAYER_HUD);
				player.setScore(0);
				player.setImmunityTime(0.5f);
				start_asteroid_wave = null;
			}
		}
		
		if (world_engine.enabled(EngineFlags.SPAWN_WAVES)) {
			PlayerCharacter player = (PlayerCharacter) party.get(0);
			
			boolean player_dead = player.getBoolean(Property.IS_DEAD);
			
			if (active_asteroid_wave != null)
				active_asteroid_wave.onTick(tick_event);
			if (!player_dead) {
				if ((active_asteroid_wave == null || active_asteroid_wave.isFinished())) {
					active_asteroid_wave = new AsteroidWave(world_engine, this, current_num_asteroids++);
				} 
			}
		}
	}

	@Override
	public void onReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttached(IdentifiedResource owner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDetached(IdentifiedResource owner) {
		// TODO Auto-generated method stub
		
	}

}
