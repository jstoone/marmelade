package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

public class Box {
	
	private static float x = 0;
	private static float y = 0;
	private static float z = 0;

	public Box(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void draw(){
		glLoadIdentity();
		glTranslatef(x, y, z);
		glBegin(GL_QUADS);
										// FRONT
			glColor3f(0.0f, 1.0f, 0.0f);	// green
			glVertex3f(-1.0f,  1.0f, 1.0f); // upper-left
			glVertex3f( 1.0f,  1.0f, 1.0f); // upper-right
			glVertex3f( 1.0f, -1.0f, 1.0f); // lower-right
			glVertex3f(-1.0f, -1.0f, 1.0f);	// lower-left
			
											// BACK
			glColor3f(  1.0f,  0.0f,  0.0f);// red
			glVertex3f(-1.0f,  1.0f, -1.0f);// upper-left (right seen form rotation=180)
			glVertex3f( 1.0f,  1.0f, -1.0f);// upper-right (left seen form rotation=180)
			glVertex3f( 1.0f, -1.0f, -1.0f);// lower-right (left seen from rotation=180)
			glVertex3f(-1.0f, -1.0f, -1.0f);// lower-left (right seen form rotation=180)
			
											// LEFT
			glColor3f(  0.0f,  0.0f,  1.0f);// blue
			glVertex3f(-1.0f,  1.0f, -1.0f);// upper-left
			glVertex3f(-1.0f,  1.0f,  1.0f);// upper-right
			glVertex3f(-1.0f, -1.0f,  1.0f);// lower-right
			glVertex3f(-1.0f, -1.0f, -1.0f);// lower-left
			
											// RIGHT
			glColor3f( 1.0f,  0.0f,  1.0f);	// purple
			glVertex3f(1.0f,  1.0f,  1.0f); // upper-left
			glVertex3f(1.0f,  1.0f, -1.0f);	// upper-right
			glVertex3f(1.0f, -1.0f, -1.0f);	// lower-right
			glVertex3f(1.0f, -1.0f,  1.0f); // lower-left
			
											// TOP
			glColor3f(  1.0f, 1.0f,  0.0f);	// yellow
			glVertex3f(-1.0f, 1.0f, -1.0f);	// upper-left
			glVertex3f( 1.0f, 1.0f, -1.0f); // upper-right
			glVertex3f( 1.0f, 1.0f,  1.0f); // lower-right
			glVertex3f(-1.0f, 1.0f,  1.0f); // lower-left
			
											// BOTTUM
			glColor3f(  0.0f,  1.0f,  1.0f);// light-blue 
			glVertex3f(-1.0f, -1.0f, -1.0f);// upper-left
			glVertex3f( 1.0f, -1.0f, -1.0f);// upper-right
			glVertex3f( 1.0f, -1.0f,  1.0f);// lower-right
			glVertex3f(-1.0f, -1.0f,  1.0f);// lower-left
		glEnd();
	}
}
