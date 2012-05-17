package com.jakobsteinn.marmelade;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.jakobsteinn.marmelade.utils.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * Uses the Phong lighting model to display a tasty chocolate bunny.
 * @author Oskar Veerhoek
 */
public class Marmelade {
	private static int shaderProgram, diffuseModifierUniform;
	
	// display lists
	private static int bunny, wall, floor, ceiling, box, texture;
	
	public static final String MODEL_LOCATION = "res/stanford-bunny.model";
	public static final String VERTEX_SHADER_LOCATION = "res/specular_lighting.vert";
	public static final String FRAGMENT_SHADER_LOCATION = "res/specular_lighting.frag";
	
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 768));
			Display.setVSyncEnabled(true);
			Display.setTitle("Marmelade dev-0.4");
			Display.create(new PixelFormat(8,	// 8 bits for alpha buffer
										   8,
										   8	// 8 bits for stensil buffer
										   ));
		} catch (LWJGLException e) {
			System.err.println("The display wasn't initialized correctly. :(");
			Display.destroy();
			System.exit(1);
		}

		Camera cam = new Camera((float) Display.getWidth()
				/ (float) Display.getHeight(), 0.0f, 0.0f, 7.0f);
		cam.setFov(60);
		cam.applyProjectionMatrix();
		
		setUpShaders();
		setUpLighting();
		
		// draw the textures
		texture = glGenTextures();
		setUpTextures(texture);
		
		
		ceiling = glGenLists(1);
		Shapes.drawCeiling(ceiling);
		
		wall = glGenLists(1);
		Shapes.drawWall(wall);
		
		floor = glGenLists(1);
		Shapes.drawFloor(floor);
		
		// draw the different shapes
//		bunny = glGenLists(1);
//		Shapes.draw3DModel(bunny, new File(MODEL_LOCATION));
		
		// draw the 3D box
		box = glGenLists(1);
		Shapes.drawBox(box);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glBindTexture(GL_TEXTURE_2D, texture);

			cam.applyModelviewMatrix(true);
			
			glUseProgram(shaderProgram);
			
			glUniform1f(diffuseModifierUniform, 1.5f);
			
//			glCallList(bunny);
			glPushMatrix();
				glDisable(GL_CULL_FACE);
				glCallList(box);
			glPopMatrix();
			glCallList(ceiling);
			glCallList(wall);
			glCallList(floor);
			
			glUseProgram(0);

			cam.processMouse(1, 80, -80);
			cam.processKeyboard(16, 0.003f, 0.003f, 0.003f);

			if (Mouse.isButtonDown(0))
				Mouse.setGrabbed(true);
			else if (Mouse.isButtonDown(1))
				Mouse.setGrabbed(false);

			Display.update();
			Display.sync(60);
			System.out.println("X: " + cam.getPitch() + " Y: " + cam.getYaw() + " Z: " + cam.getRoll());
		}
		glDeleteProgram(shaderProgram);
//		glDeleteLists(bunny, 1);
		glDeleteLists(box, 1);
		glDeleteLists(ceiling, 1);
		glDeleteLists(wall, 1);
		glDeleteLists(floor, 1);
		
		Display.destroy();
		System.exit(0);
		
	}
	
	private static void setUpTextures(int texture) {
		InputStream in = null;
        try {
            in = new FileInputStream("res/floorDefault.png");
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
