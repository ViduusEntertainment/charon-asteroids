package org.viduus.charon.asteroids.world.objects.characters.nonplayable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.global.event.events.CollisionEvent;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.event.events.WeaponUseEvent;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.character.nonplayable.NonPlayableCharacter2D;

public class Spaceship extends NonPlayableCharacter2D {

	public Spaceship(AbstractWorldEngine world_engine, String name, Vector2 location, float speed, float health,
			float mana, float max_health, float max_mana, String animation_file, String sprite_id) {
		super(world_engine, name, location, speed, health, mana, max_health, max_mana, animation_file, sprite_id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onWeaponUse(WeaponUseEvent use_event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeBodyCreation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCollision(CollisionEvent collision_event) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
