package com.algodal.phase01.rps;

import static com.algodal.phase01.rps.Constants.defGS;

public abstract class Entity extends GameState {

	private final static String en = "com.algodal.phase01.Entity";
	
	private GameState state;
	
	public Entity() {
		setGameState(defGS);
	}
	
	@Override
	public String name() {
		return en;
	}
	
	@Override
	public void show(SubGame sg) {
		state.show(sg);
	}
	
	@Override
	public void render(SubGame sg, float delta) {
		state.render(sg, delta);
	}
	
	public void setGameState(GameState state) {
		this.state = state;
	}
	
	@Override
	public void down(float x, float y) {
		state.down(x, y);
	}
	
	@Override
	public void drag(float x, float y) {
		state.drag(x, y);
	}
	
	@Override
	public void fling(float vx, float vy) {
		state.fling(vx, vy);
	}
	
	@Override
	public void press(float x, float y) {
		state.press(x, y);
	}
	
	@Override
	public void tap(float x, float y, int count) {
		state.tap(x, y, count);
	}
	
	@Override
	public void up(float x, float y) {
		state.up(x, y);
	}
}
