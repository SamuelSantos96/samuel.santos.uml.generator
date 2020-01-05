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

//import ConventionChecker.CheckConventions;
//import pa.javaparser.JavaParser;

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
				System.out.println("Interface " + name);
				currentEntity.setName("Interface " + name);
			}
			else {
				System.out.println("Class " + name);
				currentEntity.setName("Class " + name);
			}
			
			// Superclass
			Type superclass = node.getSuperclassType();
			if(superclass != null) {
				currentEntity.dependencies.add("Class " + superclass.toString());
			}
			//System.out.println("super " + superclass);
			
			//Interfaces
			ITypeBinding resolveBinding = node.resolveBinding();
			//System.out.println(resolveBinding.getInterfaces().length);
			for (ITypeBinding t : resolveBinding.getInterfaces()) {
				//System.out.println("interface: " + t);
				String i = t.toString();
				i = i.replace("[MISSING:", "");
				i = i.replace("]", "");
				currentEntity.dependencies.add("Interface " + i);
			}
			
			//System.out.println("Parsing class " + name + ", starting on line " + sourceLine(node));
			
			if(!Character.isUpperCase(name.charAt(0))) {
				//System.out.println("Classes must start with an uppercase letter!");
            	currentEntity.setColor("Red");
			}
			
			if(name.contains("_")) {
				//System.out.println("Class names can't contain underscores!");
            	currentEntity.setColor("Red");
			}			
			
			//model.add(currentEntity.getName());
			
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
				umlValue += isPublic ? "public " : "private ";
				
				boolean isStatic = Modifier.isStatic(node.getModifiers());
				umlValue += isStatic ? "static " : "";
				
				boolean isAbstract = Modifier.isAbstract(node.getModifiers());
				umlValue += isAbstract ? "abstract " : "";
				
				boolean isFinal = Modifier.isFinal(node.getModifiers());
				umlValue += isFinal ? "final " : "";
				
				String type = node.getType().toString();
				umlValue += type + " " + name;

				if(isFinal && isStatic) {
					boolean error = false;
					//System.out.println("Parsing constant " + name + ", starting on line " + sourceLine(node));
					
					for(int i=0; i < name.length(); i++) {
		                if(Character.isLowerCase(name.charAt(i)) && name.charAt(i) != '_') {
		                	error = true;
		                }
			        }
					if(error) {
						//System.out.println("Constants can only contain uppercase letters and underscores!");
	                	currentEntity.setColor("Red");
					}
				}
				
				currentEntity.attributes.add(umlValue);
				
				//model.add(name);
				//umlInfo += name + " : " + node.getType().toString() + "\n";
				/*
				System.out.println("///////////");
				System.out.println("Name: " + name);				
				System.out.println("N Attributes: " + currentEntity.attributes.size());
				System.out.println("///////////");
				*/
			}			
			
			return false; // false to avoid child VariableDeclarationFragment to be processed again
		}

		// visits variable declarations in parameters
		@Override
		public boolean visit(SingleVariableDeclaration node) {
			String name = node.getName().toString();
			
			//System.out.println("Parsing parameter " + name + ", starting on line " + sourceLine(node));
			
			// another visitor can be passed to process the method (parent of parameter) 
			class AssignVisitor extends ASTVisitor {
				// visits assignments (=, +=, etc)
				@Override
				public boolean visit(Assignment node) {
					String varName = node.getLeftHandSide().toString();
					if(name.equals(varName)) {
						//System.out.println("Parameters can't change their value!");
		            	currentEntity.setColor("Red");
					}
					
					return true;
				}
				
				// visits post increments/decrements (i++, i--) 
				@Override
				public boolean visit(PostfixExpression node) {
					String varName = node.getOperand().toString();
					if(name.equals(varName)) {
						//System.out.println("Parameters can't change their value!");
		            	currentEntity.setColor("Red");
					}
					return true;
				}

				// visits pre increments/decrements (++i, --i)
				@Override
				public boolean visit(PrefixExpression node) {
					String varName = node.getOperand().toString();
					if(name.equals(varName)) {
						//System.out.println("Parameters can't change their value!");
		            	currentEntity.setColor("Red");
					}
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
			String name = node.getName().toString();
			
			//System.out.println("Parsing variable " + name + ", starting on line " + sourceLine(node));
			
			if(Character.isUpperCase(name.charAt(0))) {
				//System.out.println("Variables must start with a lowercase letter!");
            	currentEntity.setColor("Red");
			}
			
			if(name.contains("_")) {
				//System.out.println("Variable names can't contain underscores!");
            	currentEntity.setColor("Red");
			}
						
			return true;
		}
		
		// visits method declarations
		@Override
		public boolean visit(MethodDeclaration node) {
			String umlValue = "";
			
			String name = node.getName().toString();
			
			boolean isPublic = Modifier.isPublic(node.getModifiers());
			umlValue += isPublic ? "public " : "private ";
			
			boolean isStatic = Modifier.isStatic(node.getModifiers());
			umlValue += isStatic ? "static " : "";

			boolean isAbstract = Modifier.isAbstract(node.getModifiers());
			umlValue += isAbstract ? "abstract " : "";
			
			boolean isFinal = Modifier.isFinal(node.getModifiers());
			umlValue += isFinal ? "final " : "";
			
			//String type = node.getReturnType2().toString();
			String type = "";
			umlValue += type + " " + name;
			
			int hasParameters = node.parameters().size();						
			umlValue += hasParameters == 0 ? "()" : "(args)";
			
			//System.out.println("Parsing method " + name + ", starting on line " + sourceLine(node));
			
			if(Character.isUpperCase(name.charAt(0))) {
				//System.out.println("Methods must start with a lowercase letter!");
            	currentEntity.setColor("Red");
			}
			
			if(name.contains("_")) {
				//System.out.println("Method names can't contain underscores!");
            	currentEntity.setColor("Red");
			}
			
			currentEntity.methods.add(umlValue);
			
			return true;
		}
	}
}
