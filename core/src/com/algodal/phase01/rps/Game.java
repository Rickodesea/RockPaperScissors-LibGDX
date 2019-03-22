package com.algodal.phase01.rps;

import com.badlogic.gdx.ApplicationAdapter;

public class Game extends ApplicationAdapter {
	
	private SubGame sg;
	private boolean running = true;
	
	@Override
	public void create() {
		sg = new SubGame();
		running = true;
	}
	
	@Override
	public void resize(int width, int height) {
		sg.resize(width, height);
	}
	
	@Override
	public void render() {
		if(running) sg.render();
	}
	
	@Override
	public void pause() {
		sg.pause();
		running = false;
	}
	
	@Override
	public void resume() {
		running = true;
	}
	
	@Override
	public void dispose() {
		sg.destroy();
	}
}
