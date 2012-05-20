package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

public class Box {
	
	public Box() {
	}
	
	public void draw(int boxDisplayList, float x, float y, float z, float r, float g, float b){
		glNewList(boxDisplayList, GL_COMPILE);
		{
			glDisable(GL_CULL_FACE);
			glBegin(GL_QUADS);
			// FRONT
			glVertex3f(0.0f, 0.0f, 1.0f);
			glVertex3f(1.0f, 0.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 1.0f);
			glVertex3f(0.0f, 1.0f, 1.0f);
			
			// BACK
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(1.0f, 0.0f, 0.0f);
			glVertex3f(1.0f, 1.0f, 0.0f);
			glVertex3f(0.0f, 1.0f, 0.0f);
			
			// RIGHT
			glVertex3f(1.0f, 0.0f, 0.0f);
			glVertex3f(1.0f, 0.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 1.0f);
			glVertex3f(1.0f, 1.0f, 0.0f);
			
			// LEFT
			glVertex3f(0.0f, 0.0f, 1.0f);
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(0.0f, 1.0f, 0.0f);
			glVertex3f(0.0f, 1.0f, 1.0f);
			
			// TOP
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(1.0f, 0.0f, 0.0f);
			glVertex3f(1.0f, 0.0f, 1.0f);
			glVertex3f(0.0f, 0.0f, 1.0f);
		glEnd();
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		glEndList();
	}
}
