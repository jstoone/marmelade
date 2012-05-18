package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Level {
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
	
	public void drawWall(int wallDisplayList){
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
	
	public void drawFloor(int floorDisplayList) {
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
	
	public void drawCeiling(int ceilingDisplayList) {
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
	
	public void genTextures(int textureDisplayList){
		InputStream in = null;
        try {
            in = new FileInputStream("res/floorDefault.png");
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
            buffer.flip();
            in.close();
            glBindTexture(GL_TEXTURE_2D, textureDisplayList);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glBindTexture(GL_TEXTURE_2D, 0);
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to find the texture files.");
            Display.destroy();
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Failed to load the texture files.");
            Display.destroy();
            System.exit(1);
        }
	}

}
