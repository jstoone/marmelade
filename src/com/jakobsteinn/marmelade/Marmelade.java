package com.jakobsteinn.marmelade;

import static com.jakobsteinn.marmelade.World.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jakobsteinn.marmelade.shapes.*;
import com.jakobsteinn.marmelade.utils.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Marmelade {
	
	public static int boxDisplayLists;
	
	public Marmelade(){
		try {
			Display.setDisplayMode(new DisplayMode(1024, 768));
			Display.setVSyncEnabled(true);
			Display.setTitle("Marmelade dev-0.4");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("The display wasn't initialized correctly. :(");
			Display.destroy();
			System.exit(1);
		}
		
		Camera cam = new Camera((float) Display.getWidth()
				/ (float) Display.getHeight(), 0.0f, 0.0f, 0.0f);
		cam.setFov(60);
		cam.applyProjectionMatrix();
		
		setUpShaders();
		setUpLighting();

		// do shapes
		level = new Level();
		sphere = new Sphere();
		boxColor = new BlockColor();
		
		// generate the floor texture
		Textures.genBoxTextures(textureDisplayList, FLOOR_TEXTURE);
		
		// draw the textures
		ceilingDisplayList = glGenLists(1);
		wallDisplayList = glGenLists(1);
		floorDisplayList = glGenLists(1);
		
		level.drawLevelBox(textureDisplayList, wallDisplayList, floorDisplayList,
				ceilingDisplayList);
		
		
		int size = 100;
		float radius = 60;
		boxDisplayLists = glGenLists(size);
		IntBuffer lists = BufferUtils.createIntBuffer(size);
		
		
		for(int i = 0; i < size; i++){
			float degInRad = (float) Math.toRadians(i);
			boxColor.draw(boxDisplayLists+i, (float) (Math.sin(degInRad * radius)), i, (float) (Math.sin(degInRad * radius)), 0.01f*i, 0.1f, 0.1f);
			lists.put(i);
		}
		lists.flip();
		
		while (running && !Display.isCloseRequested()) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			cam.applyModelviewMatrix(true);
			
			glUseProgram(shaderProgram);
			
			glUniform1f(diffuseModifierUniform, 1.5f);
			
			//glCallList(ceilingDisplayList);
			glCallList(wallDisplayList);
			glCallList(floorDisplayList);
			
			glListBase(boxDisplayLists);
			glCallLists(lists);
			
			glUseProgram(0);

			cam.processMouse(1, 80, -80);
			cam.processKeyboard(16, 0.01f, 0.01f, 0.01f);

			if (Mouse.isButtonDown(0))
				Mouse.setGrabbed(true);
			else if (Mouse.isButtonDown(1))
				Mouse.setGrabbed(false);

			Display.update();
			Display.sync(60);
			//System.out.println("X: " + cam.getPitch() + " Y: " + cam.getYaw() + " Z: " + cam.getRoll());
		}
		glDeleteProgram(shaderProgram);
		glDeleteLists(ceilingDisplayList, 1);
		glDeleteLists(wallDisplayList, 1);
		glDeleteLists(floorDisplayList, 1);
		glDeleteLists(boxDisplayLists, size);
		
		Display.destroy();
		System.exit(0);
		
	}

	private static FloatBuffer asFloatBuffer(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	private static void setUpLighting() {
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[] {0.05f, 0.05f, 0.05f, 1f}));
		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[] { 0.0f, 0.0f, 0.0f, 1.0f }));
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);
	}
	
	private static void setUpShaders() {
		shaderProgram = ShaderLoader.loadShaderPair(VERTEX_SHADER_LOCATION, FRAGMENT_SHADER_LOCATION);
		diffuseModifierUniform = glGetUniformLocation(shaderProgram, "diffuseIntensityModifier");
	}
}
