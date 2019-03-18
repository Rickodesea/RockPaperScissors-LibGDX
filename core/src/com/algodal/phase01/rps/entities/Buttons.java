package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.scenes.Play;
import com.algodal.phase01.rps.scenes.Play.Data;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Buttons extends Entity {

	private final NormalState normalState;
	
	private TextButton gameButton;
	
	public Buttons() {
		
		normalState = new NormalState();
		setState(normalState);
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			@Override
			public void initialize(final SubGame sg) {
				gameButton = new TextButton("Button", (Skin) sg.get(defSkin));
				gameButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						final HandManager handManager = sg.getEntity("Hand Manager");
						handManager.randomize();
						handManager.compare();
						handManager.compare(sg);
						final Play play = sg.getScene();
						final Data data = play.data;
						data.setting.completedRounds ++;
						if(data.setting.completedRounds >= data.setting.maxRounds) {
							System.out.println("Game has ended.");
							if(data.player01.scoreAmount > data.player02.scoreAmount) System.out.println("Player 1 wins.");
							if(data.player01.scoreAmount < data.player02.scoreAmount) System.out.println("Player 2 wins.");
							if(data.player01.scoreAmount == data.player02.scoreAmount) System.out.println("It is a draw.");
						}
					}
				});
			}
		};
	}
	
	public class NormalState extends State {
		
		@Override
		public void show(SubGame sg) {
			sg.st.addActor(gameButton);
			gameButton.setTransform(true);
			gameButton.setScale(1f/2.5f);
			setFromBottom(0.20f, gameButton);
			setFromLeft(0.50f, gameButton);
			System.out.println(gameButton.getX()+", "+gameButton.getY());
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
		}
	}
}





