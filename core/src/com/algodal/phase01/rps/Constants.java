package com.algodal.phase01.rps;

import com.algodal.phase01.rps.State.DefaultGameState;
import com.algodal.phase01.rps.utils.Position;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Constants {

	public final static float worldWidth = 100.0f;
	public final static float worldHeight = worldWidth * 16 / 9;
	
	public final static String defScene = "com.algodal.phase01.scenes.DefaultScene";
	public final static String gplScene = "com.algodal.phase01.scenes.PlayScene";
	public final static String menScene = "com.algodal.phase01.scenes.MenuScene";
	public final static String skiScene = "com.algodal.phase01.scenes.SkinScene";
	
	public final static String defAtlas = "atlas/rockpaperscissors.atlas";
	public final static String defSkin = "skin/uiskin.json";
	
	public final static DefaultGameState defGS = new DefaultGameState();
	
	private final static float size = 0.333f;
	public final static int maxNumberOfHands = 3;
	public final static float margin = (1f - (size * maxNumberOfHands)) / 6;
	
	public final static float handWidth = worldWidth * size;
	public final static float handHeight = handWidth;
	
	public final static float fromLeft(float percentage) { return percentage * worldWidth - worldWidth / 2;}
	public final static float fromTop(float percentage) { return -percentage * worldHeight + worldHeight / 2; }
	public final static float fromBottom(float percentage) { return percentage * worldHeight - worldHeight / 2;}
	public final static float fromRight(float percentage) { return -percentage * worldWidth + worldWidth / 2; }
	
	private final static float topRow = 0.325f, bottomRow = 0.575f;
	public final static Position[] handPositions = new Position[] {
			new Position(fromLeft(margin+size/2), fromTop(bottomRow)),
			new Position(0, fromTop(bottomRow)),
			new Position(fromRight(margin+size/2), fromTop(bottomRow)),
			new Position(fromLeft(margin+size/2), fromTop(topRow)),
			new Position(0, fromTop(topRow)),
			new Position(fromRight(margin+size/2), fromTop(topRow)),
	};
	
	public final static float xoffset(Actor actor) {
		return -actor.getWidth() * actor.getScaleX() / 2;
	}
	
	public final static float yoffset(Actor actor) {
		return -actor.getHeight() * actor.getScaleY() / 2;
	}
	
	public final static void setFromLeft(float percentage, Actor actor, boolean center) { 
		actor.setX(fromLeft(percentage)+((center)?xoffset(actor):0)); 
	}
	
	public final static void setFromRight(float percentage, Actor actor, boolean center) { 
		actor.setX(fromRight(percentage)+((center)?xoffset(actor):xoffset(actor)*2)); 
	}
	
	public final static void setFromBottom(float percentage, Actor actor, boolean center) { 
		actor.setY(fromBottom(percentage)+((center)?yoffset(actor):0)); 
	}
	
	public final static void setFromTop(float percentage, Actor actor, boolean center) { 
		actor.setY(fromTop(percentage)+((center)?yoffset(actor):yoffset(actor)*2)); 
	}
	
	public final static void setFromLeft(float percentage, Actor actor) {
		setFromLeft(percentage, actor, true);
	}
	
	public final static void setFromRight(float percentage, Actor actor) {
		setFromRight(percentage, actor, true);
	}
	
	public final static void setFromBottom(float percentage, Actor actor) {
		setFromBottom(percentage, actor, true);
	}
	
	public final static void setFromTop(float percentage, Actor actor) {
		setFromTop(percentage, actor, true);
	}
}












