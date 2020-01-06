package pt.iscte.pidesco.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.eclipse.zest.core.viewers.ISelfStyleProvider;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.demo.ConventionChecker.CheckConventions;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement.Visitor;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class DemoView implements PidescoView {
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("samuel.santos.uml.generator.colorpolicy");
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				try {
					ColorPolicy o = (ColorPolicy) c.createExecutableExtension("class");
					System.out.println(o);
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		ZestNodeContentProvider zestnodeContentProvider = new ZestNodeContentProvider();
		ZestFigureProvider zestFigureProvider = new ZestFigureProvider();
		
		GraphViewer viewer = new GraphViewer(viewArea, SWT.BORDER);
		viewer.setContentProvider(zestnodeContentProvider);
		viewer.setLabelProvider(zestFigureProvider);
		//List<String> model = new ArrayList<String>();
		
		///
		
		BundleContext context = Activator.getContext();
		ServiceReference<ProjectBrowserServices> serviceReference = context.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices projServ = context.getService(serviceReference);

		ServiceReference<JavaEditorServices> serviceReference2 = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServ = context.getService(serviceReference2);
		
		PackageElement myPackage = projServ.getRootPackage();
		System.out.println(myPackage.getChildren());
		
		myPackage.traverse(new Visitor() {
			
			@Override
			public boolean visitPackage(PackageElement packageElement) {
				return true;
			}
			
			@Override
			public void visitClass(ClassElement classElement) {
				//model.add(classElement.getName());
				javaServ.parseFile(classElement.getFile(), new CheckConventions());
			}
		});
		
		///
		//System.out.println("|=|=|=|=|=|=|=|");
		System.out.println("Number of Entities: " + ConventionChecker.umlEntities.size());
		for (Entity e : ConventionChecker.umlEntities) {
			
			//ConventionChecker.model.add(e.getName(), e.getColor(), e.attributes, e.methods);
			ConventionChecker.model.add(e.getName());
			
			//System.out.println("___");
			//System.out.println(e.getName());
			
			/*
			for (String s : e.attributes) {
				System.out.println(s);				
			}
			
			for (String m : e.methods) {
				System.out.println(m);				
			}
			*/
		}
		//System.out.println("|=|=|=|=|=|=|=|");
		//model.add("A");
		//model.add("B");
		//ConventionChecker.model.add("C");
		viewer.setInput(ConventionChecker.model);
		viewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		viewer.applyLayout();
	}
	
	class ZestNodeContentProvider extends ArrayContentProvider implements IGraphEntityContentProvider {
	    @Override
	    public Object[] getConnectedTo(Object entity) {
	    	ArrayList<Object> obj = new ArrayList<Object>();
	    	
	    	ConventionChecker.umlEntities.forEach(ent -> {
	    		if(entity.equals(ent.getName())) {
	    			
	    			for (String dep : ent.dependencies) {
	    				obj.add(dep);
					}
		    	}	
	    	});
	    	
	    	if(obj.size() >= 1) {
	    		return obj.toArray();
	    	}
	    	/*
	    	if(entity.equals("Class Pessoa")) {
	    		return new Object[] {"B","C","D"};
	    		//return new Object[] {"Utilizador.java","C"};
	    	}
	    	*/
	    	return new Object[0];
	    }
	}
	
	class ZestFigureProvider extends LabelProvider implements IFigureProvider, IConnectionStyleProvider, ISelfStyleProvider {
		@Override
		public IFigure getFigure(Object element) {
			return new DemoFigure(element.toString(), new ColorPicker());
			//return new DemoFigure((Entity) element);
		}
		
		@Override
		public String getText(Object element) {
			//return "Herda";
			return null;
		}

		@Override
		public int getConnectionStyle(Object rel) {
			return ZestStyles.CONNECTIONS_DASH;
		}

		@Override
		public Color getColor(Object rel) {
			//return ColorConstants.red;
			return ColorConstants.black;
		}

		@Override
		public Color getHighlightColor(Object rel) {
			//return null;
			return ColorConstants.buttonDarkest;
		}

		@Override
		public int getLineWidth(Object rel) {
			//return 1;
			return 2;
		}

		@Override
		public IFigure getTooltip(Object entity) {
			//return null;
			//return new DemoFigure("Tooltip for " + entity.toString());
			return new DemoFigure("Tooltip", new ColorPicker());
		}

		@Override
		public void selfStyleConnection(Object element, GraphConnection connection) {
			PolylineConnection connectionFig = (PolylineConnection) connection.getConnectionFigure();
			PolygonDecoration decoration = new PolygonDecoration();
			decoration.setScale(20, 10);
			decoration.setLineWidth(2);
			decoration.setOpaque(true);
			decoration.setBackgroundColor(ColorConstants.white);
			connectionFig.setTargetDecoration(decoration);
		}

		@Override
		public void selfStyleNode(Object element, GraphNode node) {
			
		}		
	}
}
