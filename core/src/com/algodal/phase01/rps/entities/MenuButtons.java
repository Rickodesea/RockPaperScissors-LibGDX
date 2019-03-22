package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.gplScene;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.skiScene;
import static com.algodal.phase01.rps.Constants.worldWidth;

import com.algodal.phase01.rps.ActorWrap;
import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.dialogs.AdsDialog;
import com.algodal.phase01.rps.helper.PlayHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MenuButtons extends Entity {

	public final NormalState normalState;
	
	private TextButton newGameBtn, contGameBtn, remAdsBtn, qSetBtn, skinBtn;
	private ActorWrap sliMaster, sliSound, sliMusic;
	private ActorWrap master, sfx, bgm;
	private ActorWrap mode;
	private AdsDialog adsDialog;
	
	private final static float scale = 1.0f / 3.5f;
	
	public MenuButtons() {
		normalState = new NormalState();
		setState(normalState);
		adsDialog = new AdsDialog();
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
				adsDialog.getLateInitialization().initialize(sg);
				
				newGameBtn = new TextButton("  Start  ", (Skin) sg.get(defSkin)); newGameBtn.setTransform(true);
				contGameBtn = new TextButton("Continue Game", (Skin) sg.get(defSkin)); contGameBtn.setTransform(true);
				remAdsBtn = new TextButton("Remove Ads + Unlock Skins", (Skin) sg.get(defSkin)); remAdsBtn.setTransform(true);
				qSetBtn = new TextButton("Setting", (Skin) sg.get(defSkin)); qSetBtn.setTransform(true);
				skinBtn = new TextButton("Skin", (Skin) sg.get(defSkin)); skinBtn.setTransform(true);
				
				sliMaster = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				sliSound  = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				sliMusic = new ActorWrap(new Slider(0, 1, 0.1f, false, (Skin) sg.get(defSkin)));
				
				master = new ActorWrap(new Label("Master", (Skin) sg.get(defSkin)));
				sfx = new ActorWrap(new Label("SFX", (Skin) sg.get(defSkin)));
				bgm = new ActorWrap(new Label("BGM", (Skin) sg.get(defSkin)));
				
				newGameBtn.setScale(scale);
				contGameBtn.setScale(scale);
				remAdsBtn.setScale(scale);
				qSetBtn.setScale(scale);
				skinBtn.setScale(scale);
				
				sliMaster.setScale(scale);
				sliMusic.setScale(scale);
				sliSound.setScale(scale);
				
				master.setScale(scale);
				bgm.setScale(scale);
				sfx.setScale(scale);
				
				setFromLeft(0.5f, newGameBtn);
				setFromLeft(0.5f, contGameBtn);
				setFromLeft(0.5f, remAdsBtn);
				setFromLeft(0.5f, qSetBtn);
				setFromLeft(0.5f, skinBtn);
				
				setFromLeft(0.5f, master);
				setFromLeft(0.5f, sliMaster);
				setFromLeft(0.5f, bgm);
				setFromLeft(0.5f, sliMusic);
				setFromLeft(0.5f, sfx);
				setFromLeft(0.5f, sliSound);
				
				final float push =-0.05f;
				final float btnoff = +0.08f;
				
				setFromBottom(0.800f+push+btnoff, contGameBtn);
				setFromBottom(0.725f+push+btnoff, newGameBtn);
				setFromBottom(0.65f+push+btnoff, remAdsBtn);
				
				setFromBottom(0.600f, qSetBtn);
				setFromBottom(0.525f, skinBtn);
				
				setFromBottom(0.403f+push, master);
				setFromBottom(0.37f+push, sliMaster);
				setFromBottom(0.293f+push, bgm);
				setFromBottom(0.26f+push, sliMusic);
				setFromBottom(0.183f+push, sfx);
				setFromBottom(0.15f+push, sliSound);
				
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
						switch(sg.data.menu.mode) {
						default:{
							sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Get_Ready;
						}break;
						case 1:{
							sg.playHelper.local.location = PlayHelper.Local.Step.Game_Intro;
						}break;
						}
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
						adsDialog.show(sg);
					}
				});
				
				qSetBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.getDialog("quicksetting").show(sg);
					}
				});
				
				skinBtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.setScene(skiScene);
					}
				});
				
				//MODE
				mode = new ActorWrap(new SelectBox<String>((Skin) sg.get(defSkin)));
				final String[] items = new String[] {
						"SINGLE PLAYER", "LOCAL MULTIPLAYER", "ONLINE MULTIPLAYER"
				};
				@SuppressWarnings("unchecked")
				final SelectBox<String> modeBox = (SelectBox<String>) mode.actor;
				modeBox.setItems(items);
				modeBox.setAlignment(Align.center);
				mode.setScale(scale);
				modeBox.pack();
				modeBox.setSelectedIndex(sg.data.menu.mode);
				mode.setWidth(worldWidth*0.80f/scale);
				setFromLeft(0.50f, mode, true);
				setFromBottom(0.430f, mode);
				mode.centerActor();
				modeBox.clearListeners();
				modeBox.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.data.menu.mode++;
						if(sg.data.menu.mode>2) sg.data.menu.mode=0;
						modeBox.setSelectedIndex(sg.data.menu.mode);
						switch(sg.data.menu.mode) {
						default: {
							sg.play.handManager.setState(sg.play.handManager.singlePlayerState);
							sg.play.buttons.setState(sg.play.buttons.singlePlayerState);
						}break;
						case 1:{
							sg.play.handManager.setState(sg.play.handManager.localMultiPlayerState);
							sg.play.buttons.setState(sg.play.buttons.localMultiPlayerState);
						}break;
						}
					}
				});
			}
		};
	}
	
	@SuppressWarnings("unused")
	private boolean newGame(SubGame sg) {
		return sg.data.menu.newGame;
	}
	
	private boolean ads(SubGame sg) {
		return !sg.data.menu.fullVersionPurchased;
	}
	
	public class NormalState extends State {

		@Override
		public void show(SubGame sg) {
			//if(!newGame(sg))sg.st.addActor(contGameBtn);
			sg.st.addActor(newGameBtn);
			if(ads(sg))sg.st.addActor(remAdsBtn);
			sg.st.addActor(sliMaster);
			sg.st.addActor(sliSound);
			sg.st.addActor(sliMusic);
			sg.st.addActor(master);
			sg.st.addActor(bgm);
			sg.st.addActor(sfx);
			sg.st.addActor(qSetBtn);
			sg.st.addActor(skinBtn);
			sg.st.addActor(mode);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
			
			switch(sg.data.menu.mode) {
			default: {
				sg.play.handManager.setState(sg.play.handManager.singlePlayerState);
				sg.play.buttons.setState(sg.play.buttons.singlePlayerState);
			}break;
			case 1:{
				sg.play.handManager.setState(sg.play.handManager.localMultiPlayerState);
				sg.play.buttons.setState(sg.play.buttons.localMultiPlayerState);
			}break;
			}
		}
	}
}
