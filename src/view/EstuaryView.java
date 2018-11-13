package view;

import java.util.ArrayList;

import controller.Code;
import controller.CodeListener;
import gameobject.GameObject;

public class EstuaryView extends ObjectView {

	public EstuaryView(int width, int height, ArrayList<GameObject> objects, CodeListener listener) {
		super(width, height, objects, listener);
		listener.codeEmitted(Code.STARTTIMER);
	}

	@Override
	public View nextView(ArrayList<GameObject> objects) {
		// Returns the next model for transition purposes
		return new ResearchView(getFrameWidth(), getFrameHeight(), objects, super.getListener());
	}
}
