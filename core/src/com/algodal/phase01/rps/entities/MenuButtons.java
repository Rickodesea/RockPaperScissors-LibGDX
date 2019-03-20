package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.gplScene;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;

import com.algodal.phase01.rps.ActorWrap;
import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuButtons extends Entity {

	public final NormalState normalState;
	
	private TextButton newGameBtn, contGameBtn, remAdsBtn;
	private ActorWrap sliMaster, sliSound, sliMusic;
	private ActorWrap master, sfx, bgm;
	
	private final static float scale = 1.0f / 3.5f;
	
	public MenuButtons() {
		normalState = new NormalState();
		setState(normalState);
	}
	
	@Override
	public String name() {
		return "menubuttons";
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			
			@Override
			public void initialize(final SubGame sg) {
				newGameBtn = new TextButton("New Game", (Skin) sg.get(defSkin)); newGameBtn.setTransform(true);
				contGameBtn = new TextButton("Continue Game", (Skin) sg.get(defSkin)); contGameBtn.setTransform(true);
				remAdsBtn = new TextButton("Remove Ads + Unlock Skins", (Skin) sg.get(defSkin)); remAdsBtn.setTransform(true);
				
				sliMaster = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				sliSound  = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				sliMusic = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				
				master = new ActorWrap(new Label("Master", (Skin) sg.get(defSkin)));
				sfx = new ActorWrap(new Label("SFX", (Skin) sg.get(defSkin)));
				bgm = new ActorWrap(new Label("BGM", (Skin) sg.get(defSkin)));
				
				newGameBtn.setScale(scale);
				contGameBtn.setScale(scale);
				remAdsBtn.setScale(scale);
				
				sliMaster.setScale(scale);
				sliMusic.setScale(scale);
				sliSound.setScale(scale);
				
				master.setScale(scale);
				bgm.setScale(scale);
				sfx.setScale(scale);
				
				setFromLeft(0.5f, newGameBtn);
				setFromLeft(0.5f, contGameBtn);
				setFromLeft(0.5f, remAdsBtn);
				
				setFromLeft(0.5f, master);
				setFromLeft(0.5f, sliMaster);
				setFromLeft(0.5f, bgm);
				setFromLeft(0.5f, sliMusic);
				setFromLeft(0.5f, sfx);
				setFromLeft(0.5f, sliSound);
				
				final float push =-0.05f;
				
				setFromBottom(0.800f+push, contGameBtn);
				setFromBottom(0.725f+push, newGameBtn);
				setFromBottom(0.65f+push, remAdsBtn);
				
				setFromBottom(0.503f+push, master);
				setFromBottom(0.47f+push, sliMaster);
				setFromBottom(0.393f+push, bgm);
				setFromBottom(0.36f+push, sliMusic);
				setFromBottom(0.283f+push, sfx);
				setFromBottom(0.25f+push, sliSound);
				
				((Slider)sliMaster.actor).setValue(sg.data.menu.masterVolume);
				((Slider)sliSound.actor).setValue(sg.data.menu.soundVolume);
				((Slider)sliMusic.actor).setValue(sg.data.menu.musicVolume);
				
				sliMaster.actor(Slider.class).addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						sg.data.menu.masterVolume = sliMaster.actor(Slider.class).getValue();
					}
				});
				
				sliMusic.actor(Slider.class).addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						sg.data.menu.musicVolume = sliMusic.actor(Slider.class).getValue();
					}
				});
				
				sliSound.actor(Slider.class).addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						sg.data.menu.soundVolume = sliSound.actor(Slider.class).getValue();
					}
				});
				
				newGameBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.setScene(gplScene);
					}
				});
				
				contGameBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.setScene(gplScene);
					}
				});
				
				remAdsBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						Gdx.app.log("Remove Ads", "Button Selected");
					}
				});
			}
		};
	}
	
	private boolean newGame(SubGame sg) {
		return sg.data.menu.newGame;
	}
	
	private boolean ads(SubGame sg) {
		return !sg.data.menu.fullVersionPurchased;
	}
	
	public class NormalState extends State {

		@Override
		public void show(SubGame sg) {
			if(!newGame(sg))sg.st.addActor(contGameBtn);
			sg.st.addActor(newGameBtn);
			if(ads(sg))sg.st.addActor(remAdsBtn);
			sg.st.addActor(sliMaster);
			sg.st.addActor(sliSound);
			sg.st.addActor(sliMusic);
			sg.st.addActor(master);
			sg.st.addActor(bgm);
			sg.st.addActor(sfx);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
		}
	}
}
