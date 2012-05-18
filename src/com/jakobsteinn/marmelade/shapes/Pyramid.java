package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class Pyramid {
	
	public void draw(int objectDisplayList){
		
		glNewList(objectDisplayList, GL_COMPILE);
        {
            double topPoint = 0.75;
            glBegin(GL_TRIANGLES);
	            glColor4f(1, 1, 0, 1f);
	            glVertex3d(0, topPoint, -5);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(-1, -0.75, -4);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(1, -.75, -4);
	
	            glColor4f(1, 1, 0, 1f);
	            glVertex3d(0, topPoint, -5);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(1, -0.75, -4);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(1, -0.75, -6);
	
	            glColor4f(1, 1, 0, 1f);
	            glVertex3d(0, topPoint, -5);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(1, -0.75, -6);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(-1, -.75, -6);
	            
	            glColor4f(1, 1, 0, 1f);
	            glVertex3d(0, topPoint, -5);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(-1, -0.75, -6);
	            glColor4f(0, 0, 1, 1f);
	            glVertex3d(-1, -.75, -4);
            glEnd();
            glColor4f(1, 1, 1, 1);
        }
        glEndList();
	}

}
