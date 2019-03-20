package com.algodal.phase01.rps.scenes;

import static com.algodal.phase01.rps.Constants.menScene;

import com.algodal.phase01.rps.Scene;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.entities.MenuButtons;
import com.algodal.phase01.rps.entities.Title;

public class Menu extends Scene {

	public Menu() {
		entities.add(new MenuButtons());
		entities.add(new Title());
	}
	
	@Override
	public void show(SubGame sg) {
		sg.st.clear();
		super.show(sg);
	}
	
	@Override
	public String name() {
		return menScene;
	}
	
}
