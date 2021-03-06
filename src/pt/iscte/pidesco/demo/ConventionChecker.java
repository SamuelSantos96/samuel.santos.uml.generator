package pt.iscte.pidesco.demo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ConventionChecker {

	/**
	 * Given any node of the AST, returns the source code line where it was parsed.
	 */
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}
	
	public static List<String> model = new ArrayList<String>();
	public static String umlInfo = "";
	
	public static List<Entity> umlEntities = new ArrayList<Entity>();
	public static Entity currentEntity = new Entity();

	public static class CheckConventions extends ASTVisitor {
		
		// visits class/interface declaration
		@Override
		public boolean visit(TypeDeclaration node) {			
			if(currentEntity.getName() != "") {
				currentEntity = new Entity();
			}
			
			umlEntities.add(currentEntity);			
			
			String name = node.getName().toString();
			
			if(node.isInterface()) {
				currentEntity.setName("Interface " + name);
			}
			else {
				currentEntity.setName("Class " + name);
			}
			
			// Superclass
			Type superclass = node.getSuperclassType();
			if(superclass != null) {
				currentEntity.dependencies.add("Class " + superclass.toString());
			}
			
			//Interfaces
			ITypeBinding resolveBinding = node.resolveBinding();
			for (ITypeBinding t : resolveBinding.getInterfaces()) {
				String i = t.toString();
				i = i.replace("[MISSING:", "");
				i = i.replace("]", "");
				currentEntity.dependencies.add("Interface " + i);
			}
						
			return true;
		}

		// visits attributes
		@Override
		public boolean visit(FieldDeclaration node) {
			
			// loop for several variables in the same declaration
			for(Object o : node.fragments()) {
				String umlValue = "";
				
				VariableDeclarationFragment var = (VariableDeclarationFragment) o;
				String name = var.getName().toString();
				
				boolean isPublic = Modifier.isPublic(node.getModifiers());
				umlValue += isPublic ? "+" : "-";
				
				String type = node.getType().toString();
				umlValue += name + " : " + type;
								
				currentEntity.attributes.add(umlValue);
			}			
			
			return false; // false to avoid child VariableDeclarationFragment to be processed again
		}

		// visits variable declarations in parameters
		@Override
		public boolean visit(SingleVariableDeclaration node) {
			String name = node.getName().toString();
			
			// another visitor can be passed to process the method (parent of parameter) 
			class AssignVisitor extends ASTVisitor {
				// visits assignments (=, +=, etc)
				@Override
				public boolean visit(Assignment node) {					
					return true;
				}
				
				// visits post increments/decrements (i++, i--) 
				@Override
				public boolean visit(PostfixExpression node) {
					return true;
				}

				// visits pre increments/decrements (++i, --i)
				@Override
				public boolean visit(PrefixExpression node) {
					return true;
				}
			}
			AssignVisitor assignVisitor = new AssignVisitor();
			node.getParent().accept(assignVisitor);
			return true;
		}
		
		// visits variable declarations
		@Override
		public boolean visit(VariableDeclarationFragment node) {			
			return true;
		}
		
		// visits method declarations
		@Override
		public boolean visit(MethodDeclaration node) {
			String umlValue = "";
			
			String name = node.getName().toString();
			
			boolean isPublic = Modifier.isPublic(node.getModifiers());
			umlValue += isPublic ? "+" : "-";

			Type type = node.getReturnType2();
			umlValue += name;
			
			int hasParameters = node.parameters().size();
			if(hasParameters == 0) {
				umlValue += "()";
			}
			else if(hasParameters == 1) {
				umlValue += "(" + hasParameters + " param)";
			}
			else {
				umlValue += "(" + hasParameters + " params)";
			}
			
			umlValue += " : " + type;
						
			currentEntity.methods.add(umlValue);
			
			return true;
		}
	}
}
