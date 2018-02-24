package org.viduus.charon.asteroids.world.weapon.range;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.world.weapon.bullets.DefaultBullet;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.util.identification.Uid;
import org.viduus.charon.global.util.math.Constants;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.Object2D;
import org.viduus.charon.global.world.objects.twodimensional.character.Character2D;
import org.viduus.charon.global.world.objects.twodimensional.weapon.range.bullets.Bullet2D;

public class DefaultGun extends Gun {

	private static float BULLET_SPEED = 800f;
	
	public DefaultGun(AbstractWorldEngine world_engine, String name, Character2D owner) {
		super(world_engine, name, owner, .3f, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	protected Bullet2D createBullet() {
		double rotation = getOwner().getDouble(Property.ROTATION);
		
		Vector2 velocity = new Vector2(BULLET_SPEED * Math.cos(rotation), BULLET_SPEED * Math.sin(rotation));
		Vector2 location = getOwner().getLocation().copy();
		
		if (velocity.x != 0 && velocity.y != 0) {
			velocity.x *= Constants.SQRT2OVER2;
			velocity.y *= Constants.SQRT2OVER2;
		}
		
		return new DefaultBullet(world_engine, Uid.generateUid("vid:bullet", "DefaultBullet"), "DefaultBullet", this, location, velocity, get(Property.DAMAGE));
	}

	@Override
	protected void onHitByWeapon(HitByWeaponEvent hit_by_weapon_event) {
		// TODO Auto-generated method stub
		
	}

	@Override 
	protected void beforeBodyCreation() {
		set(Property.MASS, 0.0);
		set(Property.LINEAR_DAMPING, 0.0);
		Object2D owner = getOwner();
		Vector2 location = owner.getVector2(Property.LOCATION).copy().add(40, 8);
		set(Property.LOCATION, location);
	}
}