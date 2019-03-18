package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.Unit;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hand extends Entity {

	private final Animation<String> animRock, animPaper, animScissors;
	private final Animation<String>[] anims;
	protected int index; public int getIndex() { return index; }
	private float time;
	
	public final static int Rock = 0, Paper = 1, Scissors = 2;
	
	protected final Unit body;
	
	private final NormalState normalState;
	
	@SuppressWarnings("unchecked")
	public Hand() {
		animRock = new Animation<String>(1.0f, "rock");
		animPaper = new Animation<String>(1.0f, "paper");
		animScissors = new Animation<String>(1.0f, "scissors");
		
		anims = (Animation<String>[]) new Animation<?>[] {
			animRock, animPaper, animScissors
		};
		
		index = 0;
		time = 0;
		
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
