package org.viduus.charon.asteroids.world;

import java.util.HashSet;

import org.viduus.charon.asteroids.world.objects.characters.nonplayable.Asteroid;
import org.viduus.charon.asteroids.world.regions.Region;
import org.viduus.charon.global.event.events.TickEvent;
import org.viduus.charon.global.util.logging.OutputHandler;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.Object2D;
import org.viduus.charon.global.world.regions.BaseRegion;

public class AsteroidWave {
	
	protected HashSet<Asteroid> asteroids = new HashSet<>();
	protected final Region region;
	protected final WorldEngine world_engine;
	protected boolean launched = false;
	
	public AsteroidWave(AbstractWorldEngine world_engine, BaseRegion region, int num_asteroids) {
		this.world_engine = (WorldEngine)world_engine;
		this.region = (Region)region;
		asteroids.addAll(Helper.createLargeAsteroids(this.world_engine, num_asteroids));
	}
	
	public void tryRemoveAsteroid(Object2D entity) {
		if (asteroids.contains(entity))
			asteroids.remove(entity);
	}
	
	public boolean isFinished() {
		return asteroids.size() == 0;
	}
	
	public void onTick(TickEvent tick_event) {
		if (!launched) {
			for (Asteroid asteroid : asteroids) {
				region.queueEntityForAddition(asteroid);
			}
			launched = true;
		}
	}
	
	public void addAsteroidFromSplit(Asteroid asteroid) {
		asteroids.add(asteroid);
	}
	
	public void removeAllAsteroidsLeft() {
		for(Asteroid asteroid : asteroids) {
			region.queueEntityForRemoval(asteroid);
		}
	}
}
