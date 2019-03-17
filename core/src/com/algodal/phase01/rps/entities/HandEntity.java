package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.GameObject;
import com.algodal.phase01.rps.GameState;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HandEntity extends Entity {

	private final Animation<String> animRock, animPaper, animScissors;
	private final Animation<String>[] anims;
	private int index;
	private float time;
	
	protected final GameObject body;
	
	private final NormalState normalState;
	
	@SuppressWarnings("unchecked")
	public HandEntity() {
		animRock = new Animation<String>(1.0f, "rock");
		animPaper = new Animation<String>(1.0f, "paper");
		animScissors = new Animation<String>(1.0f, "scissors");
		
		anims = (Animation<String>[]) new Animation<?>[] {
			animRock, animPaper, animScissors
		};
		
		index = 0;
		time = 0;
		
		body = new GameObject();
		body.width = worldWidth * 0.25f;
		body.height = worldWidth * 0.25f;
		
		normalState = new NormalState();
		
		setGameState(normalState);
	}
	
	public class NormalState extends GameState {

		@Override
		public String name() {
			return "normal state";
		}

		@Override
		public void render(SubGame sg, float delta) {
			final Animation<String> anim = anims[index];
			final TextureAtlas atlas = sg.get(defAtlas);
			
			final String region = anim.getKeyFrame(time, true);
			final TextureRegion tr = atlas.findRegion(region);
			
			sg.begin(null, null);
			sg.draw(tr, body);
			sg.end();
			
			time += delta;
		}
		
		@Override
		public void down(float x, float y) {
			if(body.contains(x, y)) {
				index = (index > 1) ? 0 : index+1;
			} 
		}
	}
}
