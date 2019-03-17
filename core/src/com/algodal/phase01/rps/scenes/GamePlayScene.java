package com.algodal.phase01.rps.scenes;

import com.algodal.phase01.rps.Scene;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.entities.GameButtonsEntity;
import com.algodal.phase01.rps.entities.HandManagerEntity;

public class GamePlayScene extends Scene {

	private final SettingData settingData;
	
	private final HandManagerEntity handManager;
	private final GameButtonsEntity gameButtons;
	
	public GamePlayScene() {
		settingData = new SettingData();
		settingData.numberOfHands = 1;
		
		handManager = new HandManagerEntity();
		gameButtons = new GameButtonsEntity();
		
		addLateInitialization(gameButtons.li);
	}
	
	@Override
	public void show(SubGame sg) {
		gameButtons.show(sg);
	}
	
	@Override
	public void render(SubGame sg, float delta) {
		sg.applySpriteViewport();
		handManager.render(sg, delta);
		sg.applyStageViewport();
		gameButtons.render(sg, delta);
	}
	
	@Override
	public void down(float x, float y) {
		handManager.down(x, y);
		gameButtons.down(x, y);
	}
	
	
	public static class SettingData {
		public int numberOfHands, numberOfRounds;
		
		public SettingData() {
			numberOfHands = 1;
			numberOfRounds = 3;
		}
	}
}
