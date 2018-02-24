package org.viduus.charon.asteroids.world.objects.characters.nonplayable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.physics.AsteroidFilter;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.event.events.CollisionEvent;
import org.viduus.charon.global.event.events.ObjectRemovalEvent;
import org.viduus.charon.global.event.events.WeaponUseEvent;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.character.nonplayable.NonPlayableCharacter2D;

public abstract class Asteroid extends NonPlayableCharacter2D {

	public Asteroid(AbstractWorldEngine world_engine, String name, Vector2 location, String animation_file, String sprite_id) {
		super(world_engine, name, location, 0, 100, 0, 100, 0, animation_file, sprite_id, "idle");
		set(Property.COLLISION_FILTER, new AsteroidFilter(this));
	}

	@Override
	public void onWeaponUse(WeaponUseEvent use_event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeBodyCreation() {
		set(Property.LINEAR_DAMPING, 0.0);
		set(Property.MASS, Double.MAX_VALUE);
	}

	@Override
	protected void onCollision(CollisionEvent collision_event) {
		world_engine.event_engine.queueEvent(this, new ObjectRemovalEvent(this), ObjectRemovalEvent.class);
	}
}
