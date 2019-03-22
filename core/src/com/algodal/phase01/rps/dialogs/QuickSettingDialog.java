package com.algodal.phase01.rps.dialogs;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

public class QuickSettingDialog implements IDialog {

	private final LateInitialization lateInitialization;
	
	private Dialog dialog;
	private final static float scale = 1f / 3.0f;
	private Slider slider;
	private final StringBuilder stringBuilder;
	
	public QuickSettingDialog() {
		stringBuilder = new StringBuilder();
		
		lateInitialization = new LateInitialization() {
			@Override
			public void initialize(final SubGame sg) {
				
				slider = new Slider(3, 25, 1, false,  (Skin) sg.get(defSkin));
				
				dialog = new Dialog("Setting", (Skin) sg.get(defSkin)) {
					@Override
					protected void result(Object object) {
						final boolean result = (Boolean)object;
						if(result) {
							sg.data.play.setting.maxRounds = (int)slider.getValue();
							sg.dataSave();
						}
					}
				};
				
				dialog.setScale(scale);
				dialog.setMovable(false);
				dialog.getTitleLabel().setAlignment(Align.center);
				
				dialog.text(getCurrentMaxRoundsText(sg));
				dialog.button("   Save   ", true); //sends "true" as the result
				dialog.button("  Cancel  ", false); //sends "false" as the result
				dialog.getContentTable().row();
				dialog.getContentTable().add(slider);
				
				//Listeners
				slider.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						stringBuilder.clear();
						stringBuilder.append("Maximum Rounds : ");
						if(slider.getValue() < 10) stringBuilder.append("0");
						stringBuilder.append((int)slider.getValue());
						final Label label = (Label) dialog.getContentTable().getCells().get(0).getActor();
						label.setText(stringBuilder);
					}
				});
			}
		};
	}
	
	private String getCurrentMaxRoundsText(SubGame sg) {
		stringBuilder.clear();
		stringBuilder.append("Maximum Rounds : ");
		if(sg.data.play.setting.maxRounds < 10) stringBuilder.append("0");
		stringBuilder.append(sg.data.play.setting.maxRounds);
		
		return stringBuilder.toString();
	}
	
	private int getCurrentMaxRounds(SubGame sg) {
		return sg.data.play.setting.maxRounds;
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return lateInitialization;
	}
	
	@Override
	public void show(SubGame sg) {
		slider.setValue((float)getCurrentMaxRounds(sg));
		dialog.show(sg.st);
		dialog.setWidth(worldWidth/scale);
		dialog.setY(0);
	}
}
