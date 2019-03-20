package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.handHeight;
import static com.algodal.phase01.rps.Constants.handPositions;
import static com.algodal.phase01.rps.Constants.handWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.TimeUtils;

public class HandManager extends Entity {

	private final Hand[] hands;
	public final Pair[] pairs;
	
	private final NormalState normalState;
	private final RandomXS128 random = new RandomXS128(TimeUtils.nanoTime());
	
	public HandManager() {
		hands = new Hand[] {
			new Hand(),
			new Hand(),
			new Hand(),
			new Hand(),
			new Hand(),
			new Hand()
		};
		
		pairs = new Pair[] {
			new Pair(hands[0], hands[3]),
			new Pair(hands[1], hands[4]),
			new Pair(hands[2], hands[5]),
		};
		
		for(int i = 0; i < hands.length; i++) {
			final Hand hand = hands[i];
			
			hand.body.width = handWidth;
			hand.body.height = handHeight;
			hand.body.x = handPositions[i].x;
			hand.body.y = handPositions[i].y;
			
			//rotate hand 3 - 5
			if(i > 2) {
				hand.body.angle = 180.0f;
			}
		}
		
		normalState = new NormalState();
		
		setState(normalState);
		
		//randomize();//TODO remove
	}
	
	public void randomize() {
		for(Hand hand : hands) hand.index = random.nextInt(3);
	}
	
	public void reset() {
		for(Hand hand : hands) hand.index = 0;
	}
	
	@Override
	public String name() {
		return "Hand Manager";
	}
	
	public void compare() {
		System.out.println("--------------");
		for(int i = 0; i < pairs.length; i++) {
			final Pair pair = pairs[i];
			System.out.println(i+") "+pair.toString());
		}
	}
	
	public void compare(SubGame sg) {
		for(int i = 0; i < pairs.length; i++) {
			final Pair pair = pairs[i];
			switch(pair.compare()) {
			case Pair.BottomWins:{
				sg.data.play.player01.scoreAmount++;
			}break;
			case Pair.TopWins:{
				sg.data.play.player02.scoreAmount++;
			}
			default: continue;
			}
		}
	}
	
	public class NormalState extends State {
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applySpriteViewport();
			for(Hand hand : hands) hand.render(sg, delta);
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				randomize();
			}
		}
		
		@Override
		public void down(float x, float y) {
			for(Hand hand : hands) hand.down(x, y);
		}
	}
	
	public static class Pair {
		private final Hand top, bottom;
		public final static int TopWins = +1, BottomWins = -1, Draw = 0;

		public Pair(Hand bottom, Hand top) {
			this.top = top;
			this.bottom = bottom;
		}
		
		public int compare() {
			switch(bottom.getIndex()) {
			case Hand.Rock: {
				switch(top.getIndex()) {
				case Hand.Rock: return Draw;
				case Hand.Paper: return TopWins;
				case Hand.Scissors: return BottomWins;
				}
			} break;
			case Hand.Paper: {
				switch(top.getIndex()) {
				case Hand.Rock: return BottomWins;
				case Hand.Paper: return Draw;
				case Hand.Scissors: return TopWins;
				}
			} break;
			case Hand.Scissors: {
				switch(top.getIndex()) {
				case Hand.Rock: return TopWins;
				case Hand.Paper: return BottomWins;
				case Hand.Scissors: return Draw;
				}
			} break;
			}
			Gdx.app.log("compare Pair", "failed to satisfy switch");
			return 99;
		}
		
		@Override
		public String toString() {
			final int result = compare();
			return
					(result==TopWins)?"Top Wins":
					(result==BottomWins)?"Bottom Wins":
					(result==Draw)?"Draw":"Error in Pair";
		}
	}
}











