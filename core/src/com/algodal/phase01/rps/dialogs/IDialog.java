package com.algodal.phase01.rps.dialogs;

import com.algodal.phase01.rps.LateInitialization;
import com.algodal.phase01.rps.SubGame;

public interface IDialog {

	public void show(SubGame sg);
	public LateInitialization getLateInitialization();
}
