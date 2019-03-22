package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.margin;
import static com.algodal.phase01.rps.Constants.menScene;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.setFromRight;

import com.algodal.phase01.rps.ActorWrap;
import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.algodal.phase01.rps.helper.PlayHelper;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Buttons extends Entity {

	public final NormalState normalState = new NormalState();
	public final SinglePlayerState singlePlayerState = new SinglePlayerState();
	
	private TextButton playbtn, homebtn, resetbtn, qsetbtn;
	public TextButton getResetBtn() { return resetbtn; }
	
	private ActorWrap message;
	
	public Buttons() {
		setState(singlePlayerState);
	}
	
	@Override
	public String name() {
		return "Buttons";
	}
	
	@Override
	public LateInitialization getLateInitialization() {
		return new LateInitialization() {
			@Override
			public void initialize(final SubGame sg) {
				final float scale = 1f/2.5f;
				
				playbtn = new TextButton("Button", (Skin) sg.get(defSkin));
				playbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						final HandManager handManager = sg.getEntity("Hand Manager");
						
						switch(sg.data.menu.mode) {
						default: {
							switch(sg.playHelper.single.location) {
							case Player01_Get_Ready : {
								sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Playing;
							}break;
							case Player01_Playing: {
								handManager.randomizeRequiredOnly(sg);
								handManager.compare();
								handManager.compare(sg);
								sg.playHelper.single.location = PlayHelper.Single.Step.Game_Reaveal;
							}break;
							case Game_Reaveal : {
								sg.data.play.setting.completedRounds ++;
								if(sg.data.play.setting.completedRounds >= sg.data.play.setting.maxRounds) {
									System.out.println("Game has ended.");
									if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) System.out.println("Player 1 wins.");
									if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount) System.out.println("Player 2 wins.");
									if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) System.out.println("It is a draw.");
								}
								sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Playing;
							}break;
							}
						}
						}
					}
				});
				playbtn.setTransform(true);
				playbtn.setScale(scale);
				setFromBottom(0.20f, playbtn);
				setFromLeft(0.50f, playbtn);
				
				homebtn = new TextButton("Home", (Skin) sg.get(defSkin));
				homebtn.setTransform(true);
				homebtn.setScale(scale);
				setFromBottom(0.025f, homebtn, false);
				setFromLeft(margin, homebtn, false);
				homebtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.setScene(menScene);
					}
				});
				
				resetbtn = new TextButton("Reset", (Skin) sg.get(defSkin));
				resetbtn.setTransform(true);
				resetbtn.setScale(scale);
				setFromBottom(0.025f, resetbtn, false);
				setFromRight(margin, resetbtn, false);
				resetbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.data.play.setting.completedRounds = 0;
						sg.data.play.player01.scoreAmount = 0;
						sg.data.play.player02.scoreAmount = 0;
						((HandManager)sg.getEntity("Hand Manager")).reset();
					}
				});
				
				qsetbtn = new TextButton("Setting", (Skin) sg.get(defSkin));
				qsetbtn.setTransform(true);
				qsetbtn.setScale(scale);
				setFromBottom(0.025f, qsetbtn, false);
				setFromRight(0.50f, qsetbtn);
				qsetbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.getDialog("quicksetting").show(sg);
					}
				});
				
				message = new ActorWrap(new Label("player 1 turn", (Skin) sg.get(defSkin)));
				message.setScale(scale*0.7f);
				
				setFromBottom(0.27f, message);
				setFromLeft(0.50f, message);
			}
		};
	}
	
	public class NormalState extends State {
		
		@Override
		public void show(SubGame sg) {
			sg.st.addActor(playbtn);
			sg.st.addActor(homebtn);
			sg.st.addActor(resetbtn);
			sg.st.addActor(qsetbtn);
			sg.st.addActor(message);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
		}
	}
	
	public class SinglePlayerState extends State {
		
		@Override
		public void show(SubGame sg) {
			sg.st.addActor(playbtn);
			sg.st.addActor(homebtn);
			sg.st.addActor(resetbtn);
			sg.st.addActor(qsetbtn);
			sg.st.addActor(message);
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
			
			switch(sg.data.menu.mode) {
			default: {
				switch(sg.playHelper.single.location) {
				case Player01_Get_Ready : {
					message.actor(Label.class).setText("Press Ready to Start.");
					playbtn.setText("Ready!");
				}break;
				case Player01_Playing: {
					message.actor(Label.class).setText("Press Done when you are finished.");
					playbtn.setText("Done!");
				}break;
				case Game_Reaveal : {
					message.actor(Label.class).setText(sg.play.handManager.getRoundVictoryMsg(0));
					playbtn.setText("Next Round");
				}break;
				}
			}
			}
			
			message.actor(Label.class).pack();
			message.fitWrap();
			setFromLeft(0.50f, message);
			
			playbtn.pack();
			setFromLeft(0.50f, playbtn);
			
		}
	}
}





