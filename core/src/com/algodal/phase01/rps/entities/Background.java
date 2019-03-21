package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldHeight;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.Unit;
import com.algodal.phase01.rps.utils.BackgroundSkin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background extends Entity {
	protected final Unit body;
	
	private final NormalState normalState;
	
	public Background() {
		body = new Unit();
		body.width = worldWidth;
		body.height = worldHeight;
		
		normalState = new NormalState();
		
		setState(normalState);
	}
	
	public class NormalState extends State {

		@Override
		public String name() {
			return "normal state";
		}

		@Override
		public void render(SubGame sg, float delta) {
			final TextureAtlas atlas = sg.get(defAtlas);
			final BackgroundSkin bgSkin = sg.data.play.setting.bgSkin();
			
			final String region = bgSkin.getRegion();
			final TextureRegion tr = atlas.findRegion(region);
			
			sg.begin(null, null);
			sg.draw(tr, body);
			sg.end();
		}
	}
}
