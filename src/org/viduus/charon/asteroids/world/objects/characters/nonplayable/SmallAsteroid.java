package org.viduus.charon.asteroids.world.objects.characters.nonplayable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.world.AbstractWorldEngine;

public class SmallAsteroid extends Asteroid {

	public SmallAsteroid(AbstractWorldEngine world_engine, String name, Vector2 location) {
		super(world_engine, name, location, "vid:animation:objects/small_asteroid", "small_asteroid");
		// TODO Auto-generated constructor stub
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
		getMonoTrack("small_explosion").play();
	}

}
