package com.algodal.phase01.rps.scenes;

import static com.algodal.phase01.rps.Constants.skiScene;

import com.algodal.phase01.rps.Scene;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.entities.Background;
import com.algodal.phase01.rps.entities.SkinUI;
import com.algodal.phase01.rps.entities.Title;

public class SkinSetup extends Scene {

	public SkinSetup() {
		entities.add(new Background());
		entities.add(new Title());
		entities.add(new SkinUI());
	}
	
	@Override
	protected void lateInitialize(SubGame sg) {
		entities.add(sg.lockBG);
		entities.add(sg.lockHand);
		super.lateInitialize(sg);
	}
	
	@Override
	public void show(SubGame sg) {
		sg.st.clear();
		super.show(sg);
	}
	
	@Override
	public String name() {
		return skiScene;
	}
	
}
