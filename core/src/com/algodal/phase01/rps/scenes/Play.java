package com.algodal.phase01.rps.scenes;

import static com.algodal.phase01.rps.Constants.gplScene;

import com.algodal.phase01.rps.Scene;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.entities.Background;
import com.algodal.phase01.rps.entities.Buttons;
import com.algodal.phase01.rps.entities.HandManager;
import com.algodal.phase01.rps.entities.Labels;
import com.algodal.phase01.rps.entities.Title;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Play extends Scene {

	public Play() {
		entities.add(new Background());
		entities.add(new HandManager());
		entities.add(new Buttons());
		entities.add(new Labels());
		entities.add(new Title());
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
			sg.data.play.setting.completedRounds = 0;
			sg.data.play.player01.scoreAmount = 0;
			sg.data.play.player02.scoreAmount = 0;
			((HandManager)sg.getEntity("Hand Manager")).reset();
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			sg.data.play.setting.maxRounds ++;
			if(sg.data.play.setting.maxRounds > 99) sg.data.play.setting.maxRounds = 99;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			sg.data.play.setting.maxRounds --;
			if(sg.data.play.setting.maxRounds < 3) sg.data.play.setting.maxRounds = 3;
		}
		
		if(sg.data.play.setting.completedRounds >= sg.data.play.setting.maxRounds) {
			for(Actor actor : sg.st.getActors()) if(actor.getTouchable() != Touchable.disabled) {
				actor.setTouchable(Touchable.disabled);
			}
			final Buttons b = sg.getEntity("Buttons");
			b.getResetBtn().setTouchable(Touchable.enabled);
		}
		
		if(sg.data.play.setting.completedRounds < sg.data.play.setting.maxRounds) {
			for(Actor actor : sg.st.getActors()) if(actor.getTouchable() != Touchable.enabled) {
				actor.setTouchable(Touchable.enabled);
			}
		}
	}
}
