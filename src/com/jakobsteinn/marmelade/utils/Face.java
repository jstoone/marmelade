package com.jakobsteinn.marmelade.utils;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	// three indices, not vertices or normals!
	public Vector3f vertex = new Vector3f();
	
	public Vector3f normal = new Vector3f();
	
	public Face(Vector3f vertex, Vector3f normal){
		this.vertex = vertex;
		this.normal = normal;
	}

}
