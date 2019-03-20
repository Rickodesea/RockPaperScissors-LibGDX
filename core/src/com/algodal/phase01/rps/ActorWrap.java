package com.algodal.phase01.rps;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ActorWrap extends Group {
	
	public final Actor actor;
	
	public ActorWrap(Actor actor) {
		super();
		addActor(actor);
		setSize(actor.getWidth(), actor.getHeight());
		this.actor = actor;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T actor(Class<T> clazz) {
		return (T)actor;
	}
}
