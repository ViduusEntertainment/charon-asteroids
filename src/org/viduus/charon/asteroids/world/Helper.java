package org.viduus.charon.asteroids.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.Asteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.LargeAsteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.MediumAsteroid;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.SmallAsteroid;
import org.viduus.charon.global.util.math.Constants;

public class Helper {
	
	private static Random RN_CODY = new Random();
	
	public static List<Asteroid> createLargeAsteroids(WorldEngine world_engine, int num_asteroids)
	{
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		
		int width = (int) world_engine.getWorldSize().width;
		int height = (int) world_engine.getWorldSize().height;
		
		for (int i = 0; i < num_asteroids; i++) {
			
			Vector2 location = new Vector2(RN_CODY.nextInt(width), RN_CODY.nextInt(height));
			
			Vector2 velocity = getRandomVelocity(150);
			
			LargeAsteroid asteroid = new LargeAsteroid(world_engine, "Asteroid", location);
			asteroid.getBody().setLinearVelocity(velocity);
			world_engine.insert(asteroid);
			asteroids.add(asteroid);
		}
		
		return asteroids;
	}
	
	public static List<Asteroid> createMediumAsteroids(WorldEngine world_engine, int num_asteroids, Vector2 location)
	{
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		
		for (int i = 0; i < num_asteroids; i++) {
			Vector2 velocity = getRandomVelocity(250);
			
			MediumAsteroid asteroid = new MediumAsteroid(world_engine, "Asteroid", location);
			asteroid.getBody().setLinearVelocity(velocity);
			world_engine.insert(asteroid);
			asteroids.add(asteroid);
		}
		
		return asteroids;
	}
	
	public static List<Asteroid> createSmallAsteroids(WorldEngine world_engine, int num_asteroids, Vector2 location)
	{
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		
		for (int i = 0; i < num_asteroids; i++) {
			Vector2 velocity = getRandomVelocity(300);
			
			SmallAsteroid asteroid = new SmallAsteroid(world_engine, "Asteroid", location);
			asteroid.getBody().setLinearVelocity(velocity);
			world_engine.insert(asteroid);
			asteroids.add(asteroid);
		}
		
		return asteroids;
	}
	
	private static Vector2 getRandomVelocity(float speed) {
		double rotation = RN_CODY.nextInt(360) * Math.PI / 180;
		
		Vector2 velocity = new Vector2(speed * Math.cos(rotation), speed * Math.sin(rotation));
		
		if (velocity.x != 0 && velocity.y != 0) {
			velocity.x *= Constants.SQRT2OVER2;
			velocity.y *= Constants.SQRT2OVER2;
		}
		
		return velocity;
	}
}
