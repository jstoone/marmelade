package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

public class Box {
	
	public Box() {
	}
	
	public void draw(int boxDisplayList, float x, float y, float z){
		glNewList(boxDisplayList, GL_COMPILE);
		{
			glPushMatrix();
			glDisable(GL_CULL_FACE);
			
			glTranslatef(x, y-1, z);
			glColor4f(1.0f, 1.0f, 1.0f, 0.5f);	// green
			glBegin(GL_QUADS);
				// FRONT
				glVertex3f(-1.0f,  1.0f, 1.0f); // upper-left
				glVertex3f( 1.0f,  1.0f, 1.0f); // upper-right
				glVertex3f( 1.0f, -1.0f, 1.0f); // lower-right
				glVertex3f(-1.0f, -1.0f, 1.0f);	// lower-left
				
				// BACK
				glVertex3f(-1.0f,  1.0f, -1.0f);// upper-left (right seen form rotation=180)
				glVertex3f( 1.0f,  1.0f, -1.0f);// upper-right (left seen form rotation=180)
				glVertex3f( 1.0f, -1.0f, -1.0f);// lower-right (left seen from rotation=180)
				glVertex3f(-1.0f, -1.0f, -1.0f);// lower-left (right seen form rotation=180)
				
				// LEFT
				glVertex3f(-1.0f,  1.0f, -1.0f);// upper-left
				glVertex3f(-1.0f,  1.0f,  1.0f);// upper-right
				glVertex3f(-1.0f, -1.0f,  1.0f);// lower-right
				glVertex3f(-1.0f, -1.0f, -1.0f);// lower-left
				
				// RIGHT
				glVertex3f(1.0f,  1.0f,  1.0f); // upper-left
				glVertex3f(1.0f,  1.0f, -1.0f);	// upper-right
				glVertex3f(1.0f, -1.0f, -1.0f);	// lower-right
				glVertex3f(1.0f, -1.0f,  1.0f); // lower-left
				
				// TOP
				glVertex3f(-1.0f, 1.0f, -1.0f);	// upper-left
				glVertex3f( 1.0f, 1.0f, -1.0f); // upper-right
				glVertex3f( 1.0f, 1.0f,  1.0f); // lower-right
				glVertex3f(-1.0f, 1.0f,  1.0f); // lower-left
				
				// BOTTUM
				glVertex3f(-1.0f, -1.0f, -1.0f);// upper-left
				glVertex3f( 1.0f, -1.0f, -1.0f);// upper-right
				glVertex3f( 1.0f, -1.0f,  1.0f);// lower-right
				glVertex3f(-1.0f, -1.0f,  1.0f);// lower-left
			glEnd();
			glPopMatrix();
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		glEndList();
	}
}
