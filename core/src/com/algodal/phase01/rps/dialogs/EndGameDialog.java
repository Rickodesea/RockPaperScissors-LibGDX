package com.algodal.phase01.rps.dialogs;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.menScene;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.helper.PlayHelper;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class EndGameDialog implements IDialog {

private final LateInitialization lateInitialization;
	
	private Dialog dialog;
	private final static float scale = 1f / 4.0f;
	
	public EndGameDialog() {
		lateInitialization = new LateInitialization() {
			
			@Override
			public void initialize(final SubGame sg) {
				dialog = new Dialog("End Of Game", (Skin) sg.get(defSkin)) {
					@Override
					protected void result(Object object) {
						final boolean result = (Boolean)object;
						switch(sg.data.menu.mode) {
						default: {
							if(result) {
								sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Playing;
							} else {
								sg.setScene(menScene);
								sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Get_Ready;
							}
						}break;
						
						case 1: {
							if(result) {
								sg.playHelper.local.location = PlayHelper.Local.Step.Player01_Playing;
							} else {
								sg.setScene(menScene);
								sg.playHelper.local.location = PlayHelper.Local.Step.Game_Intro;
							}
						}break;
						}
						sg.reset();
					}
				};
				
				dialog.setScale(scale);
				dialog.setMovable(false);
				dialog.getTitleLabel().setAlignment(Align.center);
				
				dialog.button("  New Game   ", true ); //sends "true" as the result
				dialog.button("    Home     ", false); //sends "false" as the result
			}
		};
	}
	
	@Override
	public void show(SubGame sg) {
		dialog.getContentTable().clear();
		
		switch(sg.data.menu.mode) {
		default: {
			if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) 
				dialog.text("Congratulations - You Win!");
			if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount)
				dialog.text("Best Of Luck Next Time - You Loose.");
			if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) 
				dialog.text("You will win for Sure Next Time - Match Draw.");
		}break;
		
		case 1: {
			if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) 
				dialog.text("P1 (bottom) Wins!");
			if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount)
				dialog.text("P2 (top) Wins!");
			if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) 
				dialog.text("Match Draw.");
		}break;
		}
		
		dialog.getContentTable().row();
		dialog.text("Play Again ?");
		
		dialog.show(sg.st);
		dialog.setWidth(worldWidth/scale);
		dialog.setY(-12);
	}

	@Override
	public LateInitialization getLateInitialization() {
		return lateInitialization;
	}

}
