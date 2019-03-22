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
	
	public final NormalState normalState = new NormalState();
	public final SinglePlayerState singlePlayerState = new SinglePlayerState();
	public final LocalMultiPlayerState localMultiPlayerState = new LocalMultiPlayerState();
	
	public final RandomXS128 random = new RandomXS128(TimeUtils.nanoTime());
	
	public HandManager() {
		setState(singlePlayerState);
		
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
	}
	
	public void randomize() {
		for(Hand hand : hands) hand.index = random.nextInt(3);
	}
	
	public void randomizeRequiredOnly(SubGame sg) {
		if(getState().equals(singlePlayerState)) {
			for(int i = 0; i < hands.length; i++) {
				final Hand hand = hands[i];
				
				/**  //Deprecated Code 
				if(sg.player01Turn) {
					if(i > 2 && i < 6) {
						hand.index = random.nextInt(3);
					}
				} else {
					if(i > -1 && i < 3) {
						hand.index = random.nextInt(3);
					}
				}
				**/
				if(i > 2 && i < 6) {
					hand.index = random.nextInt(3);
				}
			}
		}
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
	
	public String getRoundVictoryMsg(int mode) {
		int player01 = 0, player02 = 0;
		for(int i = 0; i < pairs.length; i++) {
			final Pair pair = pairs[i];
			switch(pair.compare()) {
			case Pair.BottomWins:{
				player01 ++;
			}break;
			case Pair.TopWins:{
				player02 ++;
			}
			default: continue;
			}
		}
		
		switch(mode) {
		default: {
			if(player01>player02) return "You Win this Round! Scored " + player01 + " points.";
			if(player01<player02) return "You Lose this Round. Scored " + player01 + " points.";
			return "This Round ends in a Draw. Scored " + player01 + " points.";			
		}
		case 1: {
			if(player01>player02) return "P1 (bottom) Wins round.";
			if(player01<player02) return "P2 (top) Wins round.";
			return "Round Draw.";	
		}
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
	
	public class SinglePlayerState extends State {

		@Override
		public void render(SubGame sg, float delta) {
			sg.applySpriteViewport();
			
			for(int i = 0; i < hands.length; i++) {
				final Hand hand = hands[i];
				
				switch(sg.playHelper.single.location) {
				case Player01_Get_Ready : {
					hand.setState(hand.unCoveredState);
				}break;
				case Player01_Playing: {
					if(i > 2 && i < 6) {
						hand.setState(hand.coveredState);
					} else {
						hand.setState(hand.normalState);
					}
				}break;
				case Game_Reveal : {
					hand.setState(hand.unCoveredState);
				}break;
				}
				
				/*if(sg.player01Turn) {
					if(i > 2 && i < 6) {
						hand.setState(hand.coveredState);
					} else {
						hand.setState(hand.normalState);
					}
				} else {
					if(i > -1 && i < 3) {
						hand.setState(hand.coveredState);
					} else {
						hand.setState(hand.normalState);
					}
				}*/
				
				hand.render(sg, delta);
			}
		}
		
		@Override
		public void down(float x, float y) {
			for(Hand hand : hands) hand.down(x, y);
		}
		
	}
	
	public class LocalMultiPlayerState extends State {

		@Override
		public void render(SubGame sg, float delta) {
			sg.applySpriteViewport();
			
			for(int i = 0; i < hands.length; i++) {
				final Hand hand = hands[i];
				
				switch(sg.playHelper.local.location) {
				case Game_Intro : {
					hand.setState(hand.unCoveredState);
				}break;
				case Player01_Playing: {
					if(i > 2 && i < 6) {
						hand.setState(hand.coveredState);
					} else {
						hand.setState(hand.normalState);
					}
				}break;
				case Game_Trans: {
					hand.setState(hand.coveredState);
				}break;
				case Player02_Playing: {
					if(i > -1 && i < 3) {
						hand.setState(hand.coveredState);
					} else {
						hand.setState(hand.normalState);
					}
				}break;
				case Game_Reveal : {
					hand.setState(hand.unCoveredState);
				}break;
				}
				
				hand.render(sg, delta);
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











