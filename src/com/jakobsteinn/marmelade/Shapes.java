package com.jakobsteinn.marmelade;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.jakobsteinn.marmelade.utils.Face;
import com.jakobsteinn.marmelade.utils.Model;
import com.jakobsteinn.marmelade.utils.ObjLoader;

public class Shapes {
	/* 
	 * ***********************************************
	 * ******               BOX                *******
	 * ***********************************************
	 */
		//The width and length of the floor and ceiling. Don't put anything above
		//1000, or OpenGL will start to freak out, though.
		// floor area, watch out for the walls, the pic's get tiny
		//DEFAULT: 10;
		public static final int gridSize = 20;
		
		//The size of tiles, where 0.5 is the standard size. Increasing the size by
		//results in smaller tiles, and vice versa.
		//DEFAULT: 0.20f
		public static final float tileSize = 0.20f;
	  
		//The height of the ceiling.
		//DEFAULT: 10
		public static final float ceilingHeight = gridSize;
		
		//The height of the floor.
		//DEFAULT: -1:
		public static final float floorHeight = -1.9f;
	
	// constructor
	public Shapes(){
	}

	// basic shapres
	//TODO: make more
	public static void drawPyramid(int objectDisplayList) {
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
	
	public static void drawBox(int boxDisplayList){
		glNewList(boxDisplayList, GL_COMPILE);
		{
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
		}
		glEndList();
	}
	
	// advanced shapes and models
	public static void draw3DModel(int bunnyObjectList, File modelLocation) {
		glNewList(bunnyObjectList, GL_COMPILE);
        {
        	Model m = null;
        	try {
				m = ObjLoader.loadModel(modelLocation);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			} catch (IOException e){
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			}
        	
        	glTranslatef(0.0f, 0.8f, 0.0f);
        	glBegin(GL_TRIANGLES);
        	for(Face faces : m.faces){
        		Vector3f n1 = m.normals.get((int) faces.normal.x -1);
        		glNormal3f(n1.x, n1.y, n1.z);
        		Vector3f v1 = m.vertices.get((int) faces.vertex.x -1);
        		glVertex3f(v1.x, v1.y, v1.z);
        		
        		Vector3f n2 = m.normals.get((int) faces.normal.y -1);
        		glNormal3f(n2.x, n2.y, n2.z);
        		Vector3f v2 = m.vertices.get((int) faces.vertex.y -1);
        		glVertex3f(v2.x, v2.y, v2.z);
        		
        		Vector3f n3 = m.normals.get((int) faces.normal.z -1);
        		glNormal3f(n3.x, n3.y, n3.z);
        		Vector3f v3 = m.vertices.get((int) faces.vertex.z -1);
        		glVertex3f(v3.x, v3.y, v3.z);
        	}
        	glEnd();
        	glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        glEndList();
	}


	// shapes for the box/walls/ceiling/floor etc.
	public static void drawWall(int wallDisplayList) {
		glNewList(wallDisplayList, GL_COMPILE);
        glBegin(GL_QUADS);
	        // North wall
	        glTexCoord2f(0, 0);
	        glVertex3f(-gridSize, floorHeight, -gridSize);
	        glTexCoord2f(0, gridSize * 10 * tileSize);
	        glVertex3f(gridSize, floorHeight, -gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
	        glVertex3f(gridSize, ceilingHeight, -gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, 0);
	        glVertex3f(-gridSize, ceilingHeight, -gridSize);
	        
	        // West wall
	        glTexCoord2f(0, 0);
	        glVertex3f(-gridSize, floorHeight, -gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, 0);
	        glVertex3f(-gridSize, ceilingHeight, -gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
	        glVertex3f(-gridSize, ceilingHeight, +gridSize);
	        glTexCoord2f(0, gridSize * 10 * tileSize);
	        glVertex3f(-gridSize, floorHeight, +gridSize);
	
	        // East wall
	        glTexCoord2f(0, 0);
	        glVertex3f(+gridSize, floorHeight, -gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, 0);
	        glVertex3f(+gridSize, floorHeight, +gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
	        glVertex3f(+gridSize, ceilingHeight, +gridSize);
	        glTexCoord2f(0, gridSize * 10 * tileSize);
	        glVertex3f(+gridSize, ceilingHeight, -gridSize);
	
	        // South wall
	        glTexCoord2f(0, 0);
	        glVertex3f(-gridSize, floorHeight, +gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, 0);
	        glVertex3f(-gridSize, ceilingHeight, +gridSize);
	        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
	        glVertex3f(+gridSize, ceilingHeight, +gridSize);
	        glTexCoord2f(0, gridSize * 10 * tileSize);
	        glVertex3f(+gridSize, floorHeight, +gridSize);
        glEnd();
    glEndList();
		
	}

	public static void drawFloor(int floorDisplayList) {
		glNewList(floorDisplayList, GL_COMPILE);
        	glBegin(GL_QUADS);
		        glTexCoord2f(0, 0);
		        glVertex3f(-gridSize, floorHeight, -gridSize);
		        glTexCoord2f(0, gridSize * 10 * tileSize);
		        glVertex3f(-gridSize, floorHeight, gridSize);
		        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
		        glVertex3f(gridSize, floorHeight, gridSize);
		        glTexCoord2f(gridSize * 10 * tileSize, 0);
		        glVertex3f(gridSize, floorHeight, -gridSize);
		    glEnd();
		glEndList();
		
	}

	public static void drawCeiling(int ceilingDisplayList) {
		glNewList(ceilingDisplayList, GL_COMPILE);
	        glBegin(GL_QUADS);
		        glTexCoord2f(0, 0);
		        glVertex3f(-gridSize, ceilingHeight, -gridSize);
		        glTexCoord2f(gridSize * 10 * tileSize, 0);
		        glVertex3f(gridSize, ceilingHeight, -gridSize);
		        glTexCoord2f(gridSize * 10 * tileSize, gridSize * 10 * tileSize);
		        glVertex3f(gridSize, ceilingHeight, gridSize);
		        glTexCoord2f(0, gridSize * 10 * tileSize);
		        glVertex3f(-gridSize, ceilingHeight, gridSize);
	        glEnd();
	    glEndList();
	}
	
	
	
}
