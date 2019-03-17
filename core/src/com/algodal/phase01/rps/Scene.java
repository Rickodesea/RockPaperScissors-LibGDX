package com.algodal.phase01.rps;

import com.badlogic.gdx.utils.Array;

public abstract class Scene extends GameState {

	private final Array<LateInitialization> lis = new Array<LateInitialization>();
	private boolean lateInitialized = false;
	
	public void addLateInitialization(LateInitialization li) {
		lis.add(li);
	}
	
	protected void lateInitialize(SubGame sg) {
		if(!lateInitialized) {
			lateInitialized = true;
			for(LateInitialization li : lis) li.initialize(sg);
		}
	}
}
