package com.jakobsteinn.marmelade;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

public class Collision {
	
	private static int occquery;
	private static final IntBuffer samples = BufferUtils.createIntBuffer(1);
	
	public static void initialize(){
		// Query setup
		IntBuffer queries = BufferUtils.createIntBuffer(1);
		glGenQueries(queries);
		occquery = queries.get();
	}
	
	public static void begin(){
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GEQUAL, 0.5f);
		glEnable(GL_SCISSOR_TEST);
		glEnable(GL_STENCIL_TEST);
		glColorMask(false, false, false, false);
	}

}
