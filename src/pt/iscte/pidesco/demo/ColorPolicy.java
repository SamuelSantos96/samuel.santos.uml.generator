package pt.iscte.pidesco.demo;

import org.eclipse.swt.graphics.Color;

import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public interface ColorPolicy {
	
	Color getColor(Entity e);
	
	Color getLineBorder(Entity e);
}
