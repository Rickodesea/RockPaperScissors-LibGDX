package com.algodal.phase01.rps.entities;

import static com.algodal.phase01.rps.Constants.defSkin;
import static com.algodal.phase01.rps.Constants.margin;
import static com.algodal.phase01.rps.Constants.menScene;
import static com.algodal.phase01.rps.Constants.setFromBottom;
import static com.algodal.phase01.rps.Constants.setFromLeft;
import static com.algodal.phase01.rps.Constants.setFromRight;

import com.algodal.phase01.rps.Entity;
import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.State;
import com.algodal.phase01.rps.SubGame;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Buttons extends Entity {

	private final NormalState normalState;
	
	private TextButton playbtn, homebtn, resetbtn, qsetbtn;
	public TextButton getResetBtn() { return resetbtn; }
	
	public Buttons() {
		
		normalState = new NormalState();
		setState(normalState);
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
						handManager.randomize();
						handManager.compare();
						handManager.compare(sg);
						sg.data.play.setting.completedRounds ++;
						if(sg.data.play.setting.completedRounds >= sg.data.play.setting.maxRounds) {
							System.out.println("Game has ended.");
							if(sg.data.play.player01.scoreAmount > sg.data.play.player02.scoreAmount) System.out.println("Player 1 wins.");
							if(sg.data.play.player01.scoreAmount < sg.data.play.player02.scoreAmount) System.out.println("Player 2 wins.");
							if(sg.data.play.player01.scoreAmount == sg.data.play.player02.scoreAmount) System.out.println("It is a draw.");
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
				
				qsetbtn = new TextButton("Quick Setting", (Skin) sg.get(defSkin));
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
		}
		
		@Override
		public void render(SubGame sg, float delta) {
			sg.applyStageViewport();
			sg.st.act();
			sg.st.draw();
		}
	}
}





