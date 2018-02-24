/**
 * Copyright 2017-2018, Viduus Entertainment LLC, All rights reserved.
 * 
 * Created on Feb 22, 2018 by Cody Barnes
 */
package org.viduus.charon.asteroids.world.objects.characters.playable;

import org.dyn4j.geometry.Vector2;
import org.viduus.charon.asteroids.GameSystems;
import org.viduus.charon.asteroids.input.PlayerControls;
import org.viduus.charon.global.AbstractGameSystems.PauseType;
import org.viduus.charon.global.GameConstants.Property;
import org.viduus.charon.global.event.events.CollisionEvent;
import org.viduus.charon.global.event.events.HitByWeaponEvent;
import org.viduus.charon.global.event.events.TickEvent;
import org.viduus.charon.global.event.events.WeaponUseEvent;
import org.viduus.charon.global.input.InputEngine;
import org.viduus.charon.global.input.player.PlayerControlsState;
import org.viduus.charon.global.util.identification.IdentifiedResource;
import org.viduus.charon.global.util.logging.ErrorHandler;
import org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D;
import org.viduus.charon.global.world.objects.twodimensional.weapon.Weapon2D;
import org.viduus.charon.global.world.objects.twodimensional.weapon.range.RangeWeapon2D;
import org.viduus.charon.global.world.objects.twodimensional.weapon.range.bullets.Bullet2D;
import org.viduus.charon.global.world.regions.BaseRegion;
import org.viduus.charon.global.world.util.CooldownTimer;

/**
 * 
 *
 * @author Ethan Toney
 */
public class PlayerCharacter extends PlayableCharacter2D {

	private static final float
		DEFAULT_HEALTH = 3,
		DEFAULT_MANA = 100f,
		DEFAULT_SPEED = 2f,
		DEFAULT_ROTATION_INCREMENT = 0.0872665f;
	private static final String
		DEFAULT_SPRITE_FILE = "vid:animation:player/player_ship",
		DEFAULT_SPRITE_ID = "player_ship",
		DEFAULT_ANIMATION_NAME = "idle";
	
	private final GameSystems game_systems;
	private boolean controller_binded = false;
	
	/*
	 * Cool downs
	 */
	private CooldownTimer primary_weapon_timer;
	private CooldownTimer immunity_timer = new CooldownTimer(Float.MAX_VALUE);
	
	/*
	 * Weapons
	 */
	private Weapon2D primary_weapon;
	
	private int score = 0;

	/**
	 * @param world_engine
	 * @param name
	 * @param location
	 * @param speed
	 * @param health
	 * @param mana
	 * @param max_health
	 * @param max_mana
	 * @param animation_file
	 * @param sprite_id
	 */
	public PlayerCharacter(GameSystems game_systems, String name, Vector2 location) {
		super(game_systems.world_engine, name, location, DEFAULT_SPEED, DEFAULT_HEALTH, DEFAULT_MANA, DEFAULT_HEALTH, DEFAULT_MANA, DEFAULT_SPRITE_FILE, DEFAULT_SPRITE_ID, DEFAULT_ANIMATION_NAME, false);
		
		this.game_systems = game_systems;
		this.<Boolean>set(Property.IS_MOVABLE, false);	
		this.set(Property.AUTO_SPRITE_UPDATE, false);
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	protected void beforeBodyCreation() {
		set(Property.LINEAR_DAMPING, 0.0);
		set(Property.ROTATION, 0.0);
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D#performTick(org.viduus.charon.global.input.player.PlayerControlsState, org.viduus.charon.global.event.events.TickEvent)
	 */
	@Override
	protected void performTick(PlayerControlsState controls_state, TickEvent tick_event) {
		float time_elapsed = tick_event.time_elapsed;
		
		if (primary_weapon_timer != null) primary_weapon_timer.update(time_elapsed);
		immunity_timer.update(time_elapsed);
				
		// Set whether or not the player is attacking
		this.set(Property.IS_ATTACKING, controls_state.getPrimaryAttack());
		
		// Player selected Start key/button. Quick pause the game
		if (controls_state.getStart()) {
			if (!game_systems.isPaused()) {
				game_systems.pauseGame(PauseType.QUICK);
			}
		}
		
		// Check if the player is attacking with primary
		if(controls_state.getPrimaryAttack()){
			if (!primary_weapon_timer.isCooling())
			{
				world_engine.event_engine.queueEvent(this, new WeaponUseEvent(primary_weapon), WeaponUseEvent.class);
				primary_weapon_timer.reset();
			}
		}
		
		double amount_to_rotate = 0;
		if (controls_state.getRight() != 0)
			amount_to_rotate += DEFAULT_ROTATION_INCREMENT;
		if (controls_state.getLeft() != 0)
			amount_to_rotate -= DEFAULT_ROTATION_INCREMENT;

		// radians
		double rotation = rotate(amount_to_rotate);
		
		float speed = this.<Float>get(Property.SPEED);
		
		Vector2 thrust_vector;
		
		if (controls_state.getUp() != 0)
			thrust_vector = new Vector2(speed * Math.cos(rotation), speed * Math.sin(rotation));
		else
			thrust_vector = new Vector2(0, 0);
		
		getBody().setLinearVelocity(getLinearVelocity().add(thrust_vector));
	}

	public void equipPrimaryWeapon(Weapon2D weapon) {
		if (primary_weapon != null) unequipWeapon(primary_weapon);
		primary_weapon = weapon;
		primary_weapon_timer = new CooldownTimer(weapon.getCooldown());
		equipWeapon(weapon);
	}
	
	public CooldownTimer getPrimaryWeaponCooldownTimer() {
		return primary_weapon_timer;
	}
	
	public Weapon2D getPrimaryWeapon() {
		return primary_weapon;
	}
	
	public void setImmunityTime(float cooldown) {
		immunity_timer.setCooldown(cooldown);
		immunity_timer.reset();
	}
	
	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D#bindInputEngine(org.viduus.charon.global.input.InputEngine)
	 */
	@Override
	public void bindInputEngine(InputEngine input_engine) {
		if( !controller_binded ){
			controller_binded = true;
			setController(new PlayerControls());
			game_systems.input_engine.registerListener(0, "main-player-default-controls", getController());
		}else{
			ErrorHandler.println("Tried to add MainCharacter listener after it was already added!");
		}
	}

	/* (non-Javadoc)
	 * @see org.viduus.charon.global.world.objects.twodimensional.character.playable.PlayableCharacter2D#unbindInputEngine(org.viduus.charon.global.input.InputEngine)
	 */
	@Override
	public void unbindInputEngine(InputEngine input_engine) {
		if( controller_binded ){
			controller_binded = false;
			game_systems.input_engine.removeListener(0, "main-player-default-controls");
		}else{
			ErrorHandler.println("Tried to remove MainCharacter listener after it was already removed!");
		}
	}

	@Override
	public void onWeaponUse(WeaponUseEvent weapon_use_event) {
		if (weapon_use_event.weapon instanceof RangeWeapon2D) {
			RangeWeapon2D weapon = (RangeWeapon2D)weapon_use_event.weapon;
			Bullet2D bullet = weapon.getBullet();
			world_engine.insert(bullet);
			this.<BaseRegion>get(Property.CURRENT_REGION).queueEntityForAddition(bullet);
		}
	}

	@Override
	protected void onHitByWeapon(HitByWeaponEvent hit_by_weapon_event) {
		// TODO Weapon strength detection?
	}

	@Override
	protected void onCollision(CollisionEvent collision_event) {
		if (!immunity_timer.isCooling()) {		
			set(Property.HEALTH, getFloat(Property.HEALTH) - 1);
			immunity_timer.reset();
		}
	}
	
	
	@Override
	public void onReleased() {}
	
	@Override
	public void onAttached(IdentifiedResource owner) {
		
	}
	
	@Override
	public void onDetached(IdentifiedResource owner) {}
}