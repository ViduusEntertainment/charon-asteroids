package org.viduus.charon.asteroids.physics;

import org.dyn4j.collision.Filter;
import org.viduus.charon.asteroids.world.objects.characters.nonplayable.Asteroid;
import org.viduus.charon.global.physics.twodimensional.filters.Character2DFilter;

public class AsteroidFilter extends Character2DFilter<Asteroid> {

	public AsteroidFilter(Asteroid object) {
		super(object);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean collisionAllowed(Filter filter) {
		if (filter instanceof AsteroidFilter)
			return false;
		
		return super.collisionAllowed(filter);
	}
}
