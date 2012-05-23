package com.jakobsteinn.marmelade.shapes;

import static org.lwjgl.opengl.GL11.*;

public class Level {
	/*
	 * ****************************************
	 * ***************** BOX ******************
	 * ****************************************
	 */
	// The width and length of the floor and ceiling. Don't put anything above
	// 1000, or OpenGL will start to freak out, though.
	// floor area, watch out for the walls, the pic's get tiny
	// DEFAULT: 10;
	public static final int gridSize = 20;

	// The size of tiles, where 0.5 is the standard size. Increasing the size by
	// results in smaller tiles, and vice versa.
	// DEFAULT: 0.20f
	public static final float tileSize = 0.20f;

	// The height of the ceiling.
	// DEFAULT: 10
	public static final float ceilingHeight = gridSize;

	// The height of the floor.
	// DEFAULT: -1:
	public static final float floorHeight = -1.9f;
	
	public Level(int wallDisplayList){
		// NORTH-SOUTH-EAST-WEST WALLS!
		glBindTexture(GL_TEXTURE_2D, wallDisplayList);
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
		glBindTexture(GL_TEXTURE_2D, 0);
		
		// FLOOR!
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
		glBindTexture(GL_TEXTURE_2D, 0);
		
		// CEILING!
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
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
	}
	
}
