package pt.iscte.pidesco.demo;

import org.eclipse.swt.graphics.Color;

public class ColorPicker implements ColorPolicy {

	@Override
	public Color getColor(Entity e) {
		return new Color(null,120,100,0);
	}

	@Override
	public Color getLineBorder(Entity e) {
		return new Color(null,20,100,130);
	}

}
