package com.algodal.phase01.rps;

import com.badlogic.gdx.ApplicationAdapter;

public class Game extends ApplicationAdapter {
	
	private SubGame sg;
	
	@Override
	public void create() {
		sg = new SubGame();
	}
	
	@Override
	public void resize(int width, int height) {
		sg.resize(width, height);
	}
	
	@Override
	public void render() {
		sg.render();
	}
	
	@Override
	public void dispose() {
		sg.destroy();
	}
}
