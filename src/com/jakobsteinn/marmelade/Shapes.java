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
	
	private int gridSize;
	
	private float floorHeight;
	private float ceilingHeight;
	private float tileSize;
	
	public Shapes(int gridSize,
			float floorHeight, float ceilingHeight, float tileSize){
		this.gridSize = gridSize;
		this.floorHeight = floorHeight;
		this.ceilingHeight = ceilingHeight;
		this.tileSize = tileSize;
	}
	
	public void drawPyramid(int objectDisplayList) {
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
	public void draw3DModel(int bunnyObjectList, File modelLocation) {
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
        		Vector3f n1 = m.normals.get((int) faces.normals.x -1);
        		glNormal3f(n1.x, n1.y, n1.z);
        		Vector3f v1 = m.vertices.get((int) faces.vertex.x -1);
        		glVertex3f(v1.x, v1.y, v1.z);
        		
        		Vector3f n2 = m.normals.get((int) faces.normals.y -1);
        		glNormal3f(n2.x, n2.y, n2.z);
        		Vector3f v2 = m.vertices.get((int) faces.vertex.y -1);
        		glVertex3f(v2.x, v2.y, v2.z);
        		
        		Vector3f n3 = m.normals.get((int) faces.normals.z -1);
        		glNormal3f(n3.x, n3.y, n3.z);
        		Vector3f v3 = m.vertices.get((int) faces.vertex.z -1);
        		glVertex3f(v3.x, v3.y, v3.z);
        	}
        	glEnd();
        	glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        glEndList();
	}


	public void drawWall(int wallDisplayList) {
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
	
	
	
}
