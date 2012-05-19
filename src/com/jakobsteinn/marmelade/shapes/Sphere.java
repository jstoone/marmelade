package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

import com.jakobsteinn.marmelade.World;

public class Sphere {
	
	private static org.lwjgl.util.glu.Sphere sphere = new org.lwjgl.util.glu.Sphere();
	public static boolean showWire = World.isWIREFRAME_MODE();
	
	public void draw(int sphereObjectList, float radius, int slices, int stacks){
		glNewList(sphereObjectList, GL_COMPILE);
			glColor3f(1.0f, 0.0f, 1.0f);
			sphere.draw(radius, slices, stacks);
			
			if(showWire == true){
				System.out.println("The wireframe is ACTIVATED.");
				drawWireframe();
			}else if(showWire == false){
				System.out.println("The Wireframe is DEACTIVATED.");
			}
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glEndList();
	}
	
	private void drawWireframe(){
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glDisable(GL_CULL_FACE);
		// draw quads
        // + float color (red)
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
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
}
