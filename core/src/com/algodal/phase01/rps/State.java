package com.algodal.phase01.rps;

public abstract class State implements Input {

	public String name() {
		return "com.algodal.phase01.GameState";
	};
	
	public void show(SubGame sg) {}
	
	public abstract void render(SubGame sg, float delta);
	
	@Override
	public void up(float x, float y) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void down(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drag(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tap(float x, float y, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void press(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fling(float vx, float vy) {
		// TODO Auto-generated method stub
		
	}
	
	public static class DefaultGameState extends State {

		@Override
		public String name() {
			return "com.algodal.phase01.GameState.DefaultGameState";
		}

		@Override
		public void render(SubGame sg, float delta) {
			//
		}
	}
}
