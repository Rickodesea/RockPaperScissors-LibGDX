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
import com.algodal.phase01.rps.dialogs.EndGameDialog;
import com.algodal.phase01.rps.dialogs.ForceQuitDialog;
import com.algodal.phase01.rps.helper.PlayHelper;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Buttons extends Entity {

	public final NormalState normalState = new NormalState();
	public final SinglePlayerState singlePlayerState = new SinglePlayerState();
	public final LocalMultiPlayerState localMultiPlayerState = new LocalMultiPlayerState();
	
	private TextButton playbtn, homebtn, resetbtn, qsetbtn;
	public TextButton getResetBtn() { return resetbtn; }
	
	private ActorWrap message;
	
	private final EndGameDialog endGameDialog;
	
	public Buttons() {
		setState(singlePlayerState);
		endGameDialog = new EndGameDialog();
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
				
				endGameDialog.getLateInitialization().initialize(sg);
				
				playbtn = new TextButton("Button", (Skin) sg.get(defSkin));
				playbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						sg.playTone();
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
								sg.data.play.setting.completedRounds ++;
								sg.playHelper.single.location = PlayHelper.Single.Step.Game_Reveal;
								sg.playDoor();
							}break;
							case Game_Reveal : {									
								if(sg.data.play.setting.completedRounds >= sg.data.play.setting.maxRounds) {
									System.out.println("Game has ended.");
									if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) System.out.println("Player 1 wins.");
									if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount) System.out.println("Player 2 wins.");
									if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) System.out.println("It is a draw.");
									endGameDialog.show(sg);
								} else {
									sg.playHelper.single.location = PlayHelper.Single.Step.Player01_Playing;
								}
							}break;
							}
						}break;
						
						case 1: {
							switch(sg.playHelper.local.location) {
							case Game_Intro : {
								sg.playHelper.local.location = PlayHelper.Local.Step.Player01_Playing;
							}break;
							case Player01_Playing: {
								sg.playHelper.local.location = PlayHelper.Local.Step.Game_Trans;
							}break;
							case Game_Trans: {
								sg.playHelper.local.location = PlayHelper.Local.Step.Player02_Playing;
							}break;
							case Player02_Playing: {
								sg.play.handManager.compare();
								sg.play.handManager.compare(sg);
								sg.data.play.setting.completedRounds ++;
								sg.playHelper.local.location = PlayHelper.Local.Step.Game_Reveal;
								sg.playDoor();
							}break;
							case Game_Reveal : {									
								if(sg.data.play.setting.completedRounds >= sg.data.play.setting.maxRounds) {
									System.out.println("Game has ended.");
									if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) System.out.println("Player 1 wins.");
									if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount) System.out.println("Player 2 wins.");
									if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) System.out.println("It is a draw.");
									endGameDialog.show(sg);
								} else {
									sg.playHelper.local.location = PlayHelper.Local.Step.Player01_Playing;
								}
							}break;
							}
						}break;
						
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
				final Runnable homeRunnable = new Runnable() {
					@Override
					public void run() {
						sg.setScene(menScene);		
						sg.reset();
					}
				};
				homebtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(sg.hasStartedPlaying()) {
							final ForceQuitDialog fqd = sg.dialog("forcequit");
							fqd.title = "Return to Home Screen";
							fqd.runnable = homeRunnable;
							fqd.show(sg);
						} else {
							homeRunnable.run();
						}
						sg.playTone();
					}
				});
				
				resetbtn = new TextButton("Reset", (Skin) sg.get(defSkin));
				resetbtn.setTransform(true);
				resetbtn.setScale(scale);
				setFromBottom(0.025f, resetbtn, false);
				setFromRight(margin, resetbtn, false);
				final Runnable resetRunnable = new Runnable() {
					@Override
					public void run() {
						if(sg.hasStartedPlaying()) sg.reset(); else sg.resetWithoutIndex();
					}
				};
				resetbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(sg.hasStartedPlaying()) {
							final ForceQuitDialog fqd = sg.dialog("forcequit");
							fqd.title = "Reset to New Game";
							fqd.runnable = resetRunnable;
							fqd.show(sg);
						} else {
							resetRunnable.run();
						}
						sg.playTone();
					}
				});
				
				qsetbtn = new TextButton("Setting", (Skin) sg.get(defSkin));
				qsetbtn.setTransform(true);
				qsetbtn.setScale(scale);
				setFromBottom(0.025f, qsetbtn, false);
				setFromRight(0.50f, qsetbtn);
				final Runnable qsetRunnable = new Runnable() {					
					@Override
					public void run() {
						sg.getDialog("quicksetting").show(sg);			
					}
				};
				qsetbtn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(sg.hasStartedPlaying()) {
							final ForceQuitDialog fqd = sg.dialog("forcequit");
							fqd.title = "Setting Change";
							fqd.runnable = qsetRunnable;
							fqd.show(sg);
						} else {
							qsetRunnable.run();
						}
						sg.playTone();
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
				case Game_Reveal : {
					message.actor(Label.class).setText(sg.play.handManager.getRoundVictoryMsg(0));
					if(sg.isMatchFinished()) {
						playbtn.setText("End Match");
					} else {
						playbtn.setText("Next Round");
					}
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
	
	public class LocalMultiPlayerState extends State {
		
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
			
			/*  //Shouldn't need this since the GameStates are set base on the mode
			 * switch(sg.data.menu.mode) {
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
				case Game_Reveal : {
					message.actor(Label.class).setText(sg.play.handManager.getRoundVictoryMsg(0));
					playbtn.setText("Next Round");
				}break;
				}
			}break;
			case 1: {
				switch(sg.playHelper.local.location) {
				case Game_Intro : {
					message.actor(Label.class).setText("P1 (bottom) v P2 (top) On Same Device.");
					playbtn.setText("Start");
				}break;
				case Player01_Playing: {
					message.actor(Label.class).setText("P1 Turn.");
					playbtn.setText("P1 - Done");
				}break;
				case Game_Trans: {
					message.actor(Label.class).setText("Hand Device to Next Player.");
					playbtn.setText("Next Player");
				}break;
				case Player02_Playing: {
					message.actor(Label.class).setText("P2 (top) Turn.");
					playbtn.setText("P2 - Done");
				}break;
				case Game_Reveal : {
					message.actor(Label.class).setText(sg.play.handManager.getRoundVictoryMsg(1));
					if(sg.isMatchFinished()) {
						playbtn.setText("End Match");
					} else {
						playbtn.setText("Next Round");
					}
				}break;
				}
			}break;
			}*/
			
			switch(sg.playHelper.local.location) {
			case Game_Intro : {
				message.actor(Label.class).setText("P1 (bottom) v P2 (top) On Same Device.");
				playbtn.setText("Start");
			}break;
			case Player01_Playing: {
				message.actor(Label.class).setText("P1 Turn.");
				playbtn.setText("P1 - Done");
			}break;
			case Game_Trans: {
				message.actor(Label.class).setText("Hand Device to Next Player.");
				playbtn.setText("Next Player");
			}break;
			case Player02_Playing: {
				message.actor(Label.class).setText("P2 (top) Turn.");
				playbtn.setText("P2 - Done");
			}break;
			case Game_Reveal : {
				message.actor(Label.class).setText(sg.play.handManager.getRoundVictoryMsg(1));
				if(sg.isMatchFinished()) {
					playbtn.setText("End Match");
				} else {
					playbtn.setText("Next Round");
				}
			}break;
			}
			
			message.actor(Label.class).pack();
			message.fitWrap();
			setFromLeft(0.50f, message);
			
			playbtn.pack();
			setFromLeft(0.50f, playbtn);
			
		}
	}
}





