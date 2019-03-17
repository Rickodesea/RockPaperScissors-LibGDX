package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.handHeight;
import static com.algodal.phase01.rps.Constants.handPositions;
import static com.algodal.phase01.rps.Constants.handWidth;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.GameState;
import com.algodal.phase01.rps.SubGame;

public class HandManagerEntity extends Entity {

	private final HandEntity[] hands;
	
	private final NormalState normalState;
	
	public HandManagerEntity() {
		hands = new HandEntity[] {
			new HandEntity(),
			new HandEntity(),
			new HandEntity(),
			new HandEntity(),
			new HandEntity(),
			new HandEntity()
		};
		
		for(int i = 0; i < hands.length; i++) {
			final HandEntity hand = hands[i];
			
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
		
		setGameState(normalState);
	}
	
	public class NormalState extends GameState {

		@Override
		public void render(SubGame sg, float delta) {
			for(HandEntity hand : hands) hand.render(sg, delta);
		}
		
		@Override
		public void down(float x, float y) {
			for(HandEntity hand : hands) hand.down(x, y);
		}
	}
}
