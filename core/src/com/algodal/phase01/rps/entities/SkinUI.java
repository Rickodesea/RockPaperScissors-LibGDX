package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.menScene;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.worldHeight;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.dialogs.AdsDialog;
import com.algodal.phase01.rps.ui.Gallery;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SkinUI extends Entity {

	public final NormalState normalState = new NormalState();
	
	private TextButton homeBtn, saveBtn;
	private final static float scale = 1f / 3.5f;
	private Gallery handGallery, bgGallery;
	
	private final AdsDialog adsDialog;
	
	public SkinUI() {
		setState(normalState);
		adsDialog = new AdsDialog();
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			
			@Override
			public void initialize(final SubGame sg) {
				adsDialog.getLateInitialization().initialize(sg);
				
				handGallery = sg.handGallery;//new Gallery(sg.data.play.setting.handskins);
				bgGallery = sg.bgGallery;//new Gallery(sg.data.play.setting.bgskins);
				
				handGallery.skinIndex = sg.data.play.setting.handSkinIndex;
				bgGallery.skinIndex = sg.data.play.setting.bgSkinIndex;
				
				handGallery.setUp(sg, scale, 10, 0, worldHeight / 7);
				bgGallery.setUp(sg, scale, 10, 0, -worldHeight / 7);
				
				homeBtn = new TextButton("Home", (Skin) sg.get(defSkin));
				homeBtn.setTransform(true);
				homeBtn.setScale(scale);
				setFromBottom(0.10f, homeBtn);
				setFromLeft(0.25f, homeBtn);
				homeBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.setScene(menScene);
					}
				});
				
				saveBtn = new TextButton("Save", (Skin) sg.get(defSkin));
				saveBtn.setTransform(true);
				saveBtn.setScale(scale);
				setFromBottom(0.10f, saveBtn);
				setFromLeft(0.75f, saveBtn);
				saveBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						Gdx.app.log("Save Button", "Clicked");
						if(saveOK(sg)) {
							sg.data.play.setting.bgSkinIndex = bgGallery.skinIndex;
							sg.data.play.setting.handSkinIndex = handGallery.skinIndex;
							sg.dataSave();
						} else {
							adsDialog.show(sg);
						}
					}
				});
			}
		};
	}
	
	private boolean saveOK(SubGame sg) {
		return sg.data.menu.fullVersionPurchased || (sg.handGallery.skinIndex < 2 && sg.bgGallery.skinIndex < 2);
	}
	
	public class NormalState extends State {

		@Override
		public void show(SubGame sg) {
			sg.st.addActor(homeBtn);
			handGallery.add(sg.st);
			bgGallery.add(sg.st);
			sg.st.addActor(saveBtn);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
			sg.lockBG.index = sg.bgGallery.skinIndex;
			sg.lockHand.index = sg.handGallery.skinIndex;
		}
	}
}
