# samuel.santos.uml.generator
pa-iscde-90917

# What is this?
This is an UML Diagram Generator Plugin developed for PIDESCO.
This is an Academic Project for Advanced Programming, MSc Computer Engineering, ISCTE-IUL.

# How does it work?
It uses an Abstract Syntax Tree (AST) to visit each node inside a package.
It will populate an ArrayList of Objects that contain the name of each class and interface found, its attributes, methods and dependencies (Extends or Implements).
With the Objects found it will generate an UML Diagram.

# How to use this plugin?
Use the ColorPolicy extension point, with that you need to create a class that implements the Interface ColorPolicy (samuel.santos.uml.generator.colorpolicy).
The class has to implement the following methods.

| Method | Description |
| --- | --- |
| `getColor` | Defines the **Background Color** for each **Class** and **Interface**. |
| `getLineBorder` | Defines the **Borderline Color** for each **Class** and **Interface**. |

The point of the class is to choose what colors will the classes and interfaces have for its background and borders on the UML Diagram.

You can also create multiple classes to define multiple styles, in the UML Diagram it will display a box with a button to change between the styles.

You can check the example classes made (ColorPicker and AnotherColorPicker).
