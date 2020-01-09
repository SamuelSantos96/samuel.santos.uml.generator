package pt.iscte.pidesco.demo;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class DemoFigure extends Figure {	
	public static Color classColor = new Color(null,40,80,250);
	public static Color classColorYellow = new Color(null,230,250,60);
	public static Color classColorRed = new Color(null,200,0,0);
	
	public DemoFigure(String text, String color) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBorder(new LineBorder(ColorConstants.black,2));
		// Changes the color of the box
		if(color.equals("Yellow")) {
			setBackgroundColor(classColorYellow);			
		}
		else if(color.equals("Red")) {
			setBackgroundColor(classColorRed);
		}
		else {
			setBackgroundColor(classColor);
		}
		setOpaque(true);
		setSize(100, 50);
		add(new Label(text));
	}
		
	public DemoFigure(String text, ColorPolicy colorPolicy) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);		
		setBackgroundColor(classColor);
		setOpaque(true);
		//setSize(50, 50);
		add(new Label(text));
		setSize(getPreferredSize());
		
		ConventionChecker.umlEntities.forEach(entity -> {
			if(entity.getName().equals(text)) {
				//ToolbarLayout layout = new ToolbarLayout();
				setLayoutManager(layout);	
								
				setBorder(new LineBorder(colorPolicy.getLineBorder(entity),3));
				// Changes the color of the box
				setBackgroundColor(colorPolicy.getColor(entity));
				
				setOpaque(true);
								
				for (String attr : entity.attributes) {
					add(new Label(attr));
				}
				
				for (String method : entity.methods) {
					add(new Label(method));
				}
				
				setSize(getPreferredSize());
			}
		});
	}
}