package com.algodal.phase01.rps.scenes;

import static com.algodal.phase01.rps.Constants.gplScene;

import com.algodal.phase01.rps.Scene;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.entities.Buttons;
import com.algodal.phase01.rps.entities.HandManager;
import com.algodal.phase01.rps.entities.Labels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Play extends Scene {

	public final Data data;
	
	public Play() {
		data = new Data();
		
		entities.add(new HandManager());
		entities.add(new Buttons());
		entities.add(new Labels());
	}
	
	@Override
	public void show(SubGame sg) {
		sg.st.clear();
		super.show(sg);
	}
	
	@Override
	public String name() {
		return gplScene;
	}
	
	@Override
	public void backendRender(SubGame sg, float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			data.setting.completedRounds = 0;
			data.player01.scoreAmount = 0;
			data.player02.scoreAmount = 0;
			((HandManager)sg.getEntity("Hand Manager")).reset();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			data.setting.maxRounds ++;
			if(data.setting.maxRounds > 99) data.setting.maxRounds = 99;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			data.setting.maxRounds ++;
			if(data.setting.maxRounds < 3) data.setting.maxRounds = 3;
		}
		
		if(data.setting.completedRounds >= data.setting.maxRounds) {
			for(Actor actor : sg.st.getActors()) if(actor.getTouchable() != Touchable.disabled) {
				actor.setTouchable(Touchable.disabled);
			}
		}
		
		if(data.setting.completedRounds < data.setting.maxRounds) {
			for(Actor actor : sg.st.getActors()) if(actor.getTouchable() != Touchable.enabled) {
				actor.setTouchable(Touchable.enabled);
			}
		}
	}
	
	public static class Data {
		
		public final Player player01 = new Player();
		public final Player player02 = new Player();
		public final Setting setting = new Setting();
		
		public static class Setting {
			public int maxRounds = 3;
			public int completedRounds = 0;
		}
		
		public static class Player {
			public int scoreAmount = 0;
		}
	}
}
