package com.jakobsteinn.marmelade;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.*;
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
import static com.jakobsteinn.marmelade.World.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
* A LWJGL port of the awesome MineFront Pre-ALPHA 0.02 Controls: W/UP =
* forward; A/LEFT = strafe left; D/RIGHT = strafe right; S/DOWN = backward;
* SPACE = fly up; SHIFT = fly down;
*
* @author Oskar Veerhoek, Yan Chernikov, Jakob Steinn
*/
public class Marmelade {
	
	private static int boxDispalyLists;
	
	public static void main(String[] args) {
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
		box = new Block();
		
		
		// draw the textures
		textureDisplayList = glGenTextures();
		setUpTextures(textureDisplayList);
		
		ceilingDisplayList = glGenLists(1);
		level.drawCeiling(ceilingDisplayList);
		
		wallDisplayList = glGenLists(1);
		level.drawWall(wallDisplayList);
		
		floorDisplayList = glGenLists(1);
		level.drawFloor(floorDisplayList);
		
		int size = 100;
		float radius = 60;
		boxDispalyLists = glGenLists(size);
		IntBuffer lists = BufferUtils.createIntBuffer(size);
		
		
		for(int i = 0; i < size; i++){
			float degInRad = (float) Math.toRadians(i);
			box.draw(boxDispalyLists+i, (float) (Math.sin(degInRad * radius)), .5f*i, (float) (Math.sin(degInRad * radius)), 0.01f*i, 0.1f, 0.1f);
			lists.put(i);
		}
		lists.flip();

		
		while (running && !Display.isCloseRequested()) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glBindTexture(GL_TEXTURE_2D, textureDisplayList);

			cam.applyModelviewMatrix(true);
			
			glUseProgram(shaderProgram);
			
			glUniform1f(diffuseModifierUniform, 1.5f);
			
			//glCallList(ceilingDisplayList);
			glCallList(wallDisplayList);
			glCallList(floorDisplayList);
			
			glListBase(boxDispalyLists);
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
		glDeleteLists(sphereObjList, 3);
		
		Display.destroy();
		System.exit(0);
		
	}
	
	private static void setUpTextures(int texture) {
		InputStream in = null;
        try {
            in = new FileInputStream("res/floorBlueGray.png");
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
            buffer.flip();
            in.close();
            glBindTexture(GL_TEXTURE_2D, texture);
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
