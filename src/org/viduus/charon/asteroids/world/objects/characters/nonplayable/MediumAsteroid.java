package org.viduus.charon.asteroids.world.objects.characters.nonplayable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.world.Helper;
import org.viduus.charon.asteroids.world.WorldEngine;
import org.viduus.charon.global.event.events.CollisionEvent;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.world.AbstractWorldEngine;

public class MediumAsteroid extends Asteroid {

	public MediumAsteroid(AbstractWorldEngine world_engine, String name, Vector2 location) {
		super(world_engine, name, location, "vid:animation:objects/medium_asteroid", "medium_asteroid");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCollision(CollisionEvent collision_event) {
		super.onCollision(collision_event);
		
		for (Asteroid asteroid : Helper.createSmallAsteroids((WorldEngine)world_engine, 2, getLocation().copy())) {
			getCurrentRegion().queueEntityForAddition(asteroid);
		}
	}
	
	@Override
	protected void onHitByWeapon(HitByWeaponEvent hit_by_weapon_event) {
		// TODO Auto-generated method stub
		
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
		getMonoTrack("medium_explosion").play();
	}

}
