package org.viduus.charon.asteroids.event;

import org.viduus.charon.global.event.events.Event;
import org.viduus.charon.global.world.objects.twodimensional.Object2D;

public class ObjectOutsideBoundsEvent extends Event {
	
	public final Object2D object;
	
	public ObjectOutsideBoundsEvent(Object2D object) {
		super(null);
		this.object = object;
	}
}