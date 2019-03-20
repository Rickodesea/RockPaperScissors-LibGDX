package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.margin;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.setFromRight;
import static com.algodal.phase01.rps.Constants.setFromTop;

import com.algodal.phase01.rps.ActorWrap;
import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

public class Labels extends Entity {

	private final NormalState normalState;
	private ActorWrap score, round, message;
	
	private final StringBuilder stringBuilder;
	
	public Labels() {
		normalState = new NormalState();
		setState(normalState);
		stringBuilder = new StringBuilder();
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			@Override
			public void initialize(SubGame sg) {
				score = new ActorWrap(new Label("Score 00 v 00", (Skin) sg.get(defSkin)));
				round = new ActorWrap(new Label("00 / 00 Round", (Skin) sg.get(defSkin)));
				
				final float scale = 1f / 2.5f;
				
				score.setScale(scale);
				round.setScale(scale);
				
				setFromTop(0.15f, score, false);
				setFromLeft(margin, score, false);
				
				setFromTop(0.15f, round, false);
				setFromRight(margin, round, false);
				
				message = new ActorWrap(new Label("player 1 turn", (Skin) sg.get(defSkin)));
				message.setScale(scale);
				
				setFromBottom(0.27f, message);
				setFromLeft(0.50f, message);
			}
		};
	}
	
	public class NormalState extends State {

		@Override
		public void show(SubGame sg) {
			sg.st.addActor(score);
			sg.st.addActor(round);
			sg.st.addActor(message);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			stringBuilder.clear();
			stringBuilder.append("Score ");
			if(sg.data.play.player01.scoreAmount<10) stringBuilder.append("0");
			stringBuilder.append(sg.data.play.player01.scoreAmount);
			stringBuilder.append(" v ");
			if(sg.data.play.player02.scoreAmount<10) stringBuilder.append("0");
			stringBuilder.append(sg.data.play.player02.scoreAmount);
			final Label scoreLabel = (Label)score.actor;
			scoreLabel.setText(stringBuilder);
			
			stringBuilder.clear();
			if(sg.data.play.setting.completedRounds<10) stringBuilder.append("0");
			stringBuilder.append(sg.data.play.setting.completedRounds);
			stringBuilder.append(" / ");
			if(sg.data.play.setting.maxRounds<10) stringBuilder.append("0");
			stringBuilder.append(sg.data.play.setting.maxRounds);
			stringBuilder.append(" Round");
			final Label roundLabel = (Label)round.actor;
			roundLabel.setText(stringBuilder);
		}
		
	}
}
