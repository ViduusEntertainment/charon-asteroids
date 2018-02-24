package org.viduus.charon.asteroids.physics;

import org.dyn4j.collision.AbstractBounds;
import org.dyn4j.collision.Collidable;
import org.dyn4j.geometry.AABB;
import org.viduus.charon.asteroids.event.ObjectOutsideBoundsEvent;
import org.viduus.charon.global.world.AbstractWorldEngine;
import org.viduus.charon.global.world.objects.twodimensional.Object2D;

public class WorldBounds extends AbstractBounds {

	private final AABB aabb;
	
	private final AbstractWorldEngine world_engine;
	
	public WorldBounds(AABB aabb, AbstractWorldEngine world_engine) {
		this.aabb = aabb;
		this.world_engine = world_engine;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.collision.Bounds#isOutside(org.dyn4j.collision.Collidable)
	 */
	@Override
	public boolean isOutside(Collidable<?> collidable) {
		AABB aabbBounds = this.aabb;
		AABB aabbBody = collidable.createAABB(collidable.getTransform());
		
		if (aabbBounds.overlaps(aabbBody)) {
			return false;
		}
		
		Object2D object = (Object2D) collidable.getUserData();
		world_engine.event_engine.queueEvent(object, new ObjectOutsideBoundsEvent(object), ObjectOutsideBoundsEvent.class);
		
		return false;
	}

}