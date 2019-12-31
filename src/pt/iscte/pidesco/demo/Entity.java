package pt.iscte.pidesco.demo;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	private String type = "";
	private String name = "";
	private String color = "Blue";
	public List<String> attributes = new ArrayList<String>();
	public List<String> methods = new ArrayList<String>();
	public List<String> dependencies = new ArrayList<String>();
	
	public Entity() {
		
	}
	
	public Entity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
