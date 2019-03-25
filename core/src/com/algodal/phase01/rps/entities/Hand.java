package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defAtlas;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.Unit;
import com.algodal.phase01.rps.utils.HandSkin;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Hand extends Entity {

	protected int index; public int getIndex() { return index; }
	
	public final static int Rock = 0, Paper = 1, Scissors = 2;
	
	protected final Unit body;
	
	public final NormalState normalState = new NormalState();
	public final CoveredState coveredState = new CoveredState();
	public final UnCoveredState unCoveredState = new UnCoveredState();
	
	public final Unit coverUnit = new Unit();
	
	private SubGame sg;
	
	public Hand() {
		setState(normalState);

		index = 0;
		
		body = new Unit();
		body.width = worldWidth * 0.25f;
		body.height = worldWidth * 0.25f;
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			
			@Override
			public void initialize(SubGame sg) {
				Hand.this.sg = sg;
			}
		};
	}
	
	public class NormalState extends State {

		float time = 0;
		Color color = new Color(Color.WHITE);
		
		@Override
		public String name() {
			return "normal state";
		}Vector3 sp = new Vector3(10.8f, 0.8f, 0.1f);

		@Override
		public void render(SubGame sg, float delta) {
			final TextureAtlas atlas = sg.get(defAtlas);
			final HandSkin handSkin = sg.data.play.setting.handSkin();
			handSkin.type = index;
			
			time += delta/1.5f;
			color.a = MathUtils.sin(Interpolation.exp10.apply(time)*MathUtils.PI);
			if(time > 1f) time = 0;
			
			final TextureRegion htr = atlas.findRegion("highlight");
			sg.begin(null, color);
			sg.draw(htr, body);
			sg.end();	
			
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
				sg.playClick();
			} 
		}
	}
	
	public class CoveredState extends State {

		@Override
		public void render(SubGame sg, float delta) {
			final TextureAtlas atlas = sg.get(defAtlas);
			final TextureRegion ctr = atlas.findRegion("cover");
			
			final HandSkin handSkin = sg.data.play.setting.handSkin();
			handSkin.type = index;
			
			final String region = handSkin.getRegion();
			final TextureRegion tr = atlas.findRegion(region);
			
			coverUnit.x = body.x;
			coverUnit.y = body.y;
			
			sg.begin(null, null);
			sg.draw(tr, body);
			sg.end();		
			
			sg.begin(null, null);
			sg.draw(ctr, coverUnit);
			sg.end();
					
			coverUnit.width = MathUtils.clamp(coverUnit.width + body.width / 60, 0, body.width);
			coverUnit.height = MathUtils.clamp(coverUnit.height + body.height / 60, 0, body.height);
		}
		
	}
	
	public class UnCoveredState extends State {

		@Override
		public void render(SubGame sg, float delta) {
			final TextureAtlas atlas = sg.get(defAtlas);
			final HandSkin handSkin = sg.data.play.setting.handSkin();
			handSkin.type = index;
			final TextureRegion tr = atlas.findRegion(handSkin.getRegion());
			
			sg.begin(null, null);
			sg.draw(tr, body);
			sg.end();
		}
		
	}
}
