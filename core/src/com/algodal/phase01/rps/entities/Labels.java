package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.margin;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.setFromRight;
import static com.algodal.phase01.rps.Constants.setFromTop;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.scenes.Play;
import com.algodal.phase01.rps.scenes.Play.Data;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;

public class Labels extends Entity {

	private final NormalState normalState;
	private ActorWrap score, round;
	
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
			}
		};
	}
	
	public class NormalState extends State {

		@Override
		public void show(SubGame sg) {
			sg.st.addActor(score);
			sg.st.addActor(round);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			final Play play = sg.getScene();
			final Data data = play.data;
			
			stringBuilder.clear();
			stringBuilder.append("Score ");
			if(data.player01.scoreAmount<10) stringBuilder.append("0");
			stringBuilder.append(data.player01.scoreAmount);
			stringBuilder.append(" v ");
			if(data.player02.scoreAmount<10) stringBuilder.append("0");
			stringBuilder.append(data.player02.scoreAmount);
			final Label scoreLabel = (Label)score.actor;
			scoreLabel.setText(stringBuilder);
			
			stringBuilder.clear();
			if(data.setting.completedRounds<10) stringBuilder.append("0");
			stringBuilder.append(data.setting.completedRounds);
			stringBuilder.append(" / ");
			if(data.setting.maxRounds<10) stringBuilder.append("0");
			stringBuilder.append(data.setting.maxRounds);
			stringBuilder.append(" Round");
			final Label roundLabel = (Label)round.actor;
			roundLabel.setText(stringBuilder);
		}
		
	}
	
	public static class ActorWrap extends Group {
		
		public final Actor actor;
		
		public ActorWrap(Actor actor) {
			super();
			addActor(actor);
			setSize(actor.getWidth(), actor.getHeight());
			this.actor = actor;
		}
	}
}
