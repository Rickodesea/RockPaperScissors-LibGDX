package com.algodal.phase01.rps.helper;

public class PlayHelper {

	public final Single single = new Single();
	public final Local local = new Local();
	
	public static class Single {
		
		public Step location = Step.Player01_Get_Ready;
		
		public static enum Step {
			Player01_Get_Ready,
			Player01_Playing,
			Game_Reveal
		}
		
	}
	
	public static class Local {
		
		public Step location = Step.Game_Intro;
		
		public static enum Step {
			Game_Intro,
			Player01_Playing,
			Game_Trans,
			Player02_Playing,
			Game_Reveal
		}
	}
}
