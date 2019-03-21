package com.algodal.phase01.rps.dialogs;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class AdsDialog implements IDialog {

	private final LateInitialization lateInitialization;
	
	private Dialog dialog;
	private final static float scale = 1f / 3.0f;
	
	public AdsDialog() {
		
		lateInitialization = new LateInitialization() {
			
			@Override
			public void initialize(final SubGame sg) {
				dialog = new Dialog("Remove Ads and Unlock Assets", (Skin) sg.get(defSkin)) {
					@Override
					protected void result(Object object) {
						final boolean result = (Boolean)object;
						Gdx.app.log("Ads Dialog", Boolean.toString(result));
					}
				};
				
				dialog.setScale(scale);
				dialog.setMovable(false);
				dialog.getTitleLabel().setAlignment(Align.center);
				
				dialog.text("Conduct One-Time purchase ?");
				dialog.button("    Yes   ", true ); //sends "true" as the result
				dialog.button("  Cancel  ", false); //sends "false" as the result
			}
		};
	}
	
	@Override
	public void show(SubGame sg) {
		dialog.show(sg.st);
		dialog.setWidth(worldWidth/scale);
		dialog.setY(-12);
	}

	@Override
	public LateInitialization getLateInitialization() {
		return lateInitialization;
	}

}
