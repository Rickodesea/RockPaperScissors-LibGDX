package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.Unit;
import com.algodal.phase01.rps.utils.HandSkin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hand extends Entity {

	protected int index; public int getIndex() { return index; }
	
	public final static int Rock = 0, Paper = 1, Scissors = 2;
	
	protected final Unit body;
	
	private final NormalState normalState;
	
	public Hand() {
		index = 0;
		
		body = new Unit();
		body.width = worldWidth * 0.25f;
		body.height = worldWidth * 0.25f;
		
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
			final HandSkin handSkin = sg.data.play.setting.handSkin();
			handSkin.type = index;
			
			final String region = handSkin.getRegion();
			final TextureRegion tr = atlas.findRegion(region);
			
			sg.begin(null, null);
			sg.draw(tr, body);
			sg.end();
		}
		
		@Override
		public void down(float x, float y) {
			if(body.contains(x, y)) {
				index = (index > 1) ? 0 : index+1;
			} 
		}
	}
}
