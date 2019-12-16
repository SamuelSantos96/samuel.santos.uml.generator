package pt.iscte.pidesco.demo;

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
	
	public DemoFigure(String text) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);	
		//setBorder(new LineBorder(ColorConstants.black,1));
		setBorder(new LineBorder(ColorConstants.black,2));
		setBackgroundColor(classColor);
		setOpaque(true);
		setSize(100, 50);
		add(new Label(text));
	}
	
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