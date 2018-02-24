package org.viduus.charon.asteroids.world.weapon.bullets;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.event.events.TickEvent;
import org.viduus.charon.global.physics.twodimensional.filters.Bullet2DFilter;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.util.identification.Uid;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.weapon.Weapon2D;

public class DefaultBullet extends Bullet{

	/**
	 * @param world_engine
	 * @param name
	 * @param location
	 * @param sprite_map
	 */
	public DefaultBullet(AbstractWorldEngine world_engine, Uid uid, String name, Weapon2D owner, Vector2 location, Vector2 linear_velocity, float damage) {
		super(world_engine, uid, name, owner, location, linear_velocity, "vid:animation:objects/bullets", "player_normal", damage);
		set(Property.COLLISION_FILTER, new Bullet2DFilter(this));
	}
	
	@Override
	public void onTick(TickEvent tick_event) {
		super.onTick(tick_event);
	}

	@Override
	protected void onHitByWeapon(HitByWeaponEvent hit_by_weapon_event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttached(IdentifiedResource owner) {
		getMonoTrack("basic_bullet").setVolume(1f).play();
	}            
}
