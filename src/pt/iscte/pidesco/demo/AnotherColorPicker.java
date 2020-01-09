package pt.iscte.pidesco.demo;

import org.eclipse.swt.graphics.Color;

public class AnotherColorPicker implements ColorPolicy {
	
	@Override
	public Color getColor(Entity e) {
		return new Color(null,0,204,153);
	}
		
	@Override
	public Color getLineBorder(Entity e) {
		return new Color(null,0,102,102);
	}
}
