package com.algodal.phase01.rps;

import com.badlogic.gdx.utils.Array;

public abstract class Scene extends State {
	
	public final Array<Entity> entities = new Array<Entity>();
	protected boolean lateInitialized;
	
	protected void lateInitialize(SubGame sg) {
		if(!lateInitialized) {
			lateInitialized = true;
			for(Entity entity : entities) entity.getLateInitialization().initialize(sg);
		}
	}
	
	/** For scene's only.  Use to avoid overriding your main render. **/
	public void backendRender(SubGame sg, float delta) {}
	
	@Override
	public void render(SubGame sg, float delta) {
		for(Entity entity : entities) entity.render(sg, delta);
		backendRender(sg, delta);
	}
	
	@Override
	public void down(float x, float y) {
		for(Entity entity : entities) entity.down(x, y);
	}
	
	@Override
	public void drag(float x, float y) {
		for(Entity entity : entities) entity.drag(x, y);
	}
	
	@Override
	public void fling(float vx, float vy) {
		for(Entity entity : entities) entity.fling(vx, vy);
	}
	
	@Override
	public void press(float x, float y) {
		for(Entity entity : entities) entity.press(x, y);
	}
	
	@Override
	public void tap(float x, float y, int count) {
		for(Entity entity : entities) entity.tap(x, y, count);
	}
	
	@Override
	public void show(SubGame sg) {
		for(Entity entity : entities) entity.show(sg);
	}
	
	@Override
	public void up(float x, float y) {
		for(Entity entity : entities) entity.up(x, y);
	}
}






















