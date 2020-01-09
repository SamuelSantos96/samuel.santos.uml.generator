package pt.iscte.pidesco.demo;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.minimap.extensibility.InspectionContext;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.utils.Colors;
import pa.iscde.minimap.utils.Styles;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public class MinimapInspector implements MinimapInspection {

	@Override
	public void inspect(ASTNode node, InspectionContext context) {
		// TODO Auto-generated method stub
		// Display metrics over the class declaration
		if (node instanceof MethodInvocation) {
			String methodName = ((MethodInvocation) node).getName().toString();
			int line = context.getLineStart();

			context.setForeground(Colors.PINK);
			context.setStyle(Styles.BOLD);
			context.addTooltip("METHOD BEING CALLED: " + methodName);
			context.addTooltip("LINE " + line);
		}
	}
}
