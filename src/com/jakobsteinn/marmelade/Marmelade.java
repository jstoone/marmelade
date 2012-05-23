package com.jakobsteinn.marmelade;

import static com.jakobsteinn.marmelade.World.FRAGMENT_SHADER_LOCATION;
import static com.jakobsteinn.marmelade.World.VERTEX_SHADER_LOCATION;
import static com.jakobsteinn.marmelade.World.box;
import static com.jakobsteinn.marmelade.World.ceilingDisplayList;
import static com.jakobsteinn.marmelade.World.diffuseModifierUniform;
import static com.jakobsteinn.marmelade.World.floorDisplayList;
import static com.jakobsteinn.marmelade.World.level;
import static com.jakobsteinn.marmelade.World.running;
import static com.jakobsteinn.marmelade.World.shaderProgram;
import static com.jakobsteinn.marmelade.World.sphere;
import static com.jakobsteinn.marmelade.World.textureDisplayList;
import static com.jakobsteinn.marmelade.World.wallDisplayList;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glCallLists;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glListBase;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUseProgram;

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

import com.jakobsteinn.marmelade.shapes.Block;
import com.jakobsteinn.marmelade.shapes.Level;
import com.jakobsteinn.marmelade.shapes.Sphere;
import com.jakobsteinn.marmelade.utils.Camera;
import com.jakobsteinn.marmelade.utils.ShaderLoader;

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
		boxDisplayLists = glGenLists(size);
		IntBuffer lists = BufferUtils.createIntBuffer(size);
		
		
		for(int i = 0; i < size; i++){
			float degInRad = (float) Math.toRadians(i);
			box.draw(boxDisplayLists+i, (float) (Math.sin(degInRad * radius)), .5f*i, (float) (Math.sin(degInRad * radius)), 0.01f*i, 0.1f, 0.1f);
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
