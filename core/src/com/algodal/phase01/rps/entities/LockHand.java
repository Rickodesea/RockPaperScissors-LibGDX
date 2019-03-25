package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldHeight;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.Unit;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LockHand extends Entity {
	protected final Unit body;
	
	private final NormalState normalState;
	
	public int index = 0;
	
	public LockHand() {
		body = new Unit();
		body.width = worldWidth*0.125f;
		body.height = worldWidth*0.125f;
		body.y = worldHeight / 7;
		body.x = 0;
		
		normalState = new NormalState();
		
		setState(normalState);
	}
	
	@Override
		public String name() {
			return "lockhand";
		}
	
	public class NormalState extends State {

		@Override
		public String name() {
			return "normal state";
		}

		@Override
		public void render(SubGame sg, float delta) {
			final TextureAtlas atlas = sg.get(defAtlas);
			final TextureRegion tr = atlas.findRegion("lock_1");
			
			if(!sg.data.menu.fullVersionPurchased) {
				if(index > 2) {
					sg.begin(null, null);
					sg.draw(tr, body);
					sg.end();
				}
			}
		}
	}
}
