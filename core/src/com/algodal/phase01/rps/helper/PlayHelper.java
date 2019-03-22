package com.algodal.phase01.rps.helper;

public class PlayHelper {

	public final Single single = new Single();
	
	public static class Single {
		
		public Step location = Step.Player01_Get_Ready;
		
		public static enum Step {
			Player01_Get_Ready,
			Player01_Playing,
			Game_Reaveal
		}
		
	}
}
