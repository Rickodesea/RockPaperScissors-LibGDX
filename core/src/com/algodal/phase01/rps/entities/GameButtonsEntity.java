package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.setFromRight;
import static com.algodal.phase01.rps.Constants.xoffset;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.GameState;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameButtonsEntity extends Entity {

	private final NormalState normalState;
	
	private TextButton gameButton;
	
	public final LateInitialization li;
	
	public GameButtonsEntity() {
		
		normalState = new NormalState();
		setGameState(normalState);
		
		li = new LateInitialization() {
			@Override
			public void initialize(SubGame sg) {
				gameButton = new TextButton("Button", (Skin) sg.get(defSkin));
			}
		};
	}
	
	public class NormalState extends GameState {

		@Override
		public void show(SubGame sg) {
			sg.st.clear();
			sg.st.addActor(gameButton);
			gameButton.setTransform(true);
			gameButton.setScale(1f/2.5f);
			setFromBottom(0.25f, gameButton);
			setFromLeft(0.50f, gameButton);
			System.out.println(gameButton.getX()+", "+gameButton.getY());
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.st.act();
			sg.st.draw();
		}
	}
}





