package com.jakobsteinn.marmelade;

import java.io.File;

import com.jakobsteinn.marmelade.shapes.Level;
import com.jakobsteinn.marmelade.shapes.Sphere;

public class World {
	
	// determins if the program is in running or !running state
	public static boolean running = true;
	
	public static int shaderProgram, diffuseModifierUniform;
	
	// display lists
	public static int wallDisplayList, floorDisplayList, ceilingDisplayList, sphereObjList, textureDisplayList;
	
	// file locations
	public static final File MODEL_LOCATION = new File("res/stanford-bunny.model");
	// shaders
	public static final String VERTEX_SHADER_LOCATION = "res/specular_lighting.vert";
	public static final String FRAGMENT_SHADER_LOCATION = "res/specular_lighting.frag";
	
	// Shapes
	public static Sphere sphere;
	public static Level level;

}
