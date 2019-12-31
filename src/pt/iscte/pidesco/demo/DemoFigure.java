package pt.iscte.pidesco.demo;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class DemoFigure extends Figure {
	
	// public static Color classColor = new Color(null,255,255,206);
	public static Color classColor = new Color(null,40,80,250);
	public static Color classColorYellow = new Color(null,230,250,60);
	public static Color classColorRed = new Color(null,40,80,250);
	/*
	public DemoFigure(String text) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);	
		//setBorder(new LineBorder(ColorConstants.black,1));
		setBorder(new LineBorder(ColorConstants.black,2));
		setBackgroundColor(classColor);
		setOpaque(true);
		setSize(150, 150);
		add(new Label(text));
		
		//add(new Label("Attr1\nAttr2"));
		//add(new Label("Method1"));
		//add(new Label("Method2"));
		
	}
	*/
	public DemoFigure(String text, String color) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);	
		//setBorder(new LineBorder(ColorConstants.black,1));
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
	
	
	public DemoFigure(String text) {
		ConventionChecker.umlEntities.forEach(entity -> {
			if(entity.getName().equals(text)) {
		
				//String text, String color, List<String> attributes, List<String> methods
				
				ToolbarLayout layout = new ToolbarLayout();
				setLayoutManager(layout);	
				//setBorder(new LineBorder(ColorConstants.black,1));
				setBorder(new LineBorder(ColorConstants.black,2));
				// Changes the color of the box
				if(entity.getColor().equals("Yellow")) {
					setBackgroundColor(classColorYellow);			
				}
				else if(entity.getColor().equals("Red")) {
					setBackgroundColor(classColorRed);
				}
				else {
					setBackgroundColor(classColor);
				}
				setOpaque(true);
				//setSize(200, 200);
				add(new Label(entity.getName()));
				
				int height = 200;
				int weight = 200;
				
				for (String attr : entity.attributes) {
					add(new Label(attr));
					height += 5;
				}
				
				for (String method : entity.methods) {
					add(new Label(method));
					height += 5;
				}
				
				setSize(weight, height);
			}
		});
	}
	
	
	/*
	public DemoFigure(String text, String details[]) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);	
		//setBorder(new LineBorder(ColorConstants.black,1));
		setBorder(new LineBorder(ColorConstants.black,2));
		setBackgroundColor(classColor);
		setOpaque(true);
		setSize(100, 50);
		add(new Label(text));
		
		for(int i = 0; i < details.length; i++) {
			add(new Label(details[i]));
		}
	}
	
	public DemoFigure(String text, String details[], String color) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);	
		//setBorder(new LineBorder(ColorConstants.black,1));
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
		for(int i = 0; i < details.length; i++) {
			add(new Label(details[i]));
		}
	}
	*/
}