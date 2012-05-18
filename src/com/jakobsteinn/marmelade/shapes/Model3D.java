package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.jakobsteinn.marmelade.utils.*;

public class Model3D {

	public void draw(int bunnyObjectList, File modelLocation) {
		glNewList(bunnyObjectList, GL_COMPILE);
        {
        	Model m = null;
        	try {
				m = ObjLoader.loadModel(modelLocation);
			} catch (FileNotFoundException e) {
				System.out.println("Could not find the model file at: " + modelLocation);
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			} catch (IOException e){
				System.out.println("Could not find the model file at: " + modelLocation);
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			} catch (NullPointerException e) {
				System.out.println("Could not find the model file at: " + modelLocation);
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

}
