package pt.iscte.pidesco.demo;

import org.eclipse.swt.graphics.Color;

public class ColorPicker implements ColorPolicy {

	@Override
	public Color getColor(Entity e) {
		return new Color(null,153,153,255);
	}

	@Override
	public Color getLineBorder(Entity e) {
		return new Color(null,255,0,255);
	}
}
