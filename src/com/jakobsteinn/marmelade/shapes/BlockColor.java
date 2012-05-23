package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

public class BlockColor {
	
	public BlockColor() {
	}
	
	public void draw(int boxDispalyLists, float x, float y, float z, float r, float g, float b){
		glNewList(boxDispalyLists, GL_COMPILE);
		
		glPushMatrix();
			glDisable(GL_CULL_FACE);
			glColor3f(r, g, b);
			glTranslatef(x, y, z);
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
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
				
				// LEFT
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(0.0f, 0.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 0.0f);
				
				// RIGHT
				glVertex3f(1.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 0.0f);
				
				// TOP
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 1.0f);
				glVertex3f(0.0f, 0.0f, 1.0f);
				
				// BUTT :D
				glVertex3f(0.0f, 1.0f, 0.0f);
				glVertex3f(1.0f, 1.0f, 0.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 1.0f);
			glEnd();
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glColor3f(0.0f, 0.0f, 0.0f);
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
				
				// LEFT
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(0.0f, 0.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 0.0f);
				
				// RIGHT
				glVertex3f(1.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 0.0f);
				
				// TOP
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 0.0f);
				glVertex3f(1.0f, 0.0f, 1.0f);
				glVertex3f(0.0f, 0.0f, 1.0f);
				
				// BUTT :D
				glVertex3f(0.0f, 1.0f, 0.0f);
				glVertex3f(1.0f, 1.0f, 0.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(0.0f, 1.0f, 1.0f);
			glEnd();
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			glColor3f(1.0f, 1.0f, 1.0f);
		glPopMatrix();
	glEndList();
	}
}
