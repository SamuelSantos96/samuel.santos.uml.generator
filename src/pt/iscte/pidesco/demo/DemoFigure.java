package pt.iscte.pidesco.demo;

import java.util.List;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

public class DemoFigure extends Figure {
	
	public DemoFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		Button btn = new Button("Change ColorPolicy");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				int index = DemoView.colorPolicies.indexOf(DemoView.currentColorPolicy);
				
				if(index == (DemoView.colorPolicies.size() - 1)) {
					DemoView.currentColorPolicy = DemoView.colorPolicies.get(0);					
				}
				else {
					DemoView.currentColorPolicy = DemoView.colorPolicies.get(index + 1);
				}
				System.out.println("ColorPolicy has changed!");
			}
		});
		
		setLayoutManager(layout);
		add(new Label("ColorPolicy"));
		String currentColorPolicyClass = DemoView.currentColorPolicy.getClass().getName().replace("pt.iscte.pidesco.demo.", "");
		add(new Label(">>> " + currentColorPolicyClass + " <<<"));
		add(btn);
		
		setBorder(new LineBorder(new Color(null,0,0,0),1));
		// Changes the color of the box
		setBackgroundColor(new Color(null,255,255,255));
		setOpaque(true);
		setSize(getPreferredSize());
	}
	
	public DemoFigure(String text, ColorPolicy colorPolicy) {
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setOpaque(true);
		add(new Label(text));
		setSize(getPreferredSize());
				
		ConventionChecker.umlEntities.forEach(entity -> {
			if(entity.getName().equals(text)) {				
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