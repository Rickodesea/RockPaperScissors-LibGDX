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
	
	public void centerActor() {
		actor.setPosition(getWidth() / 2 - actor.getWidth() / 2, getHeight() / 2 - actor.getHeight() / 2);
	}
	
	public void fitActor() {
		actor.setSize(getWidth(), getHeight());
	}
	
	public void fitWrap() {
		setSize(actor.getWidth(), actor.getHeight());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T actor(Class<T> clazz) {
		return (T)actor;
	}
}
