package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

public class BlockColor {
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	public float r = 0;
	public float g = 0;
	public float b = 0;
	
	public float id;
	
	public boolean drawLine = true;
	

	public BlockColor(int boxDisplayLists, float x, float y, float z, float r, float g, float b) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.r = r;
		this.g = g;
		this.b = b;
		
		draw(boxDisplayLists, x, y, z, r, g, b);
		
	}
	
	private void draw(int boxDisplayLists, float x, float y, float z, float r, float g, float b){
		glNewList(boxDisplayLists, GL_COMPILE);
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
			if(isDrawLine()){
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
			}
			glColor3f(1.0f, 1.0f, 1.0f);
		glPopMatrix();
	glEndList();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	private boolean isDrawLine() {
		return drawLine;
	}

	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}
	
}
