package com.jakobsteinn.marmelade;

import java.io.File;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.newdawn.slick.Color;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Vector3f;

/**
* A LWJGL port of the awesome MineFront Pre-ALPHA 0.02 Controls: W/UP =
* forward; A/LEFT = strafe left; D/RIGHT = strafe right; S/DOWN = backward;
* SPACE = fly up; SHIFT = fly down; CONTROL = move faster; TAB = move slower; Q
* = increase walking speed; Z = decrease walking speed; O = increase mouse
* speed; L = decrease mouse speed; C = reset position
*
* @author Oskar Veerhoek, Yan Chernikov, Jakob Steinn
*/
public class Marmelade {
	
	/* 
	 * ***********************************************
	 * ******          APPLICATION             *******
	 * ***********************************************
	 */
		// defines if the application is running. Set to false to terminate the
		// program.
		//DEFAULT: true
		public static volatile boolean running = true;
		
		//Defines if the application is resizable.
		//DEFAULT: true
		public static final boolean resizable = true;
		
		//Defines if the application utilizes full-screen.
		//DEFAULT: false
		public static final boolean fullscreen = false;
		
		//Defines if the application utilizes vertical synchronization (eliminates
		//screen tearing; caps fps to 60fps)
		//DEFAULT: true
		public static final boolean vsync = true;
		
		//Defines if the applications prints its frames-per-second to the console.
		//DEFAULT: false
		public static boolean printFPS = false;
	
	/* 
	 * ***********************************************
	 * ******             PLAYER               *******
	 * ***********************************************
	 */
		//The position of the player as a 3D vector (xyz).
		//DEFAULT: new Vector3f(0.0f, 0.0f, -5.0f);
		public static Vector3f position = new Vector3f(0.0f, 0.0f, 10.0f);
		
		//Defines the walking speed, where 10 is the standard.
		//DEFAULT: 10
		public static int walkingSpeed = 10;
		
		public static float velocityX = 0.0002f;
		public static float velocityY = 0.0002f;
		public static float velocityZ = 0.0002f;
		
		//Defines the mouse speed.
		//DEFAULT: -2
		public static int mouseSpeed = 2;
		
		// The rotation of the axis (where to the player looks). The X component
		// stands for the rotation along the x-axis, where 0 is dead ahead, 180 is
		// backwards, and 360 is automically set to 0 (dead ahead). The value must
		// be between (including) 0 and 360. The Y component stands for the rotation
		// along the y-axis, where 0 is looking straight ahead, -90 is straight up,
		// and 90 is straight down. The value must be between (including) -90 and
		// 90.
		//DEFAULT: new Vector3f(0.0f, 0.0f, 0.0f);
		public static Vector3f rotation = new Vector3f(0.0f, 180.0f, 0.0f);
		
		//Defines the maximum angle at which the player can look up.
		//DEFAULT: 85
		public static final int maxLookUp = 85;
		
		//Defines the minimum angle at which the player can look down.
		// DEFAULT: -85
		public static final int maxLookDown = -85;
	    
		
	/* 
	 * ***********************************************
	 * ******             CAMERA               *******
	 * ***********************************************
	 */   
		//The minimal distance from the camera where objects are rendered.
		//DEFAULT: 0.001f
		public static float zNear = 0.001f;
		  
		//The maximal distance from the camera where objects are rendered.
		//DEFAULT: 20f
		public static float zFar = 200f;
		
		//Defines the field of view.
		//DEFAULT: 68
	    public static int fov = 68;
	
	/* 
	 * ***********************************************
	 * ******               FOG                *******
	 * ***********************************************
	 */
		//The distance where fog starts appearing.
		//DEFAULT: 9f
		public static float fogNear = 20f;
				
		//The distance where the fog stops appearing (fully black here)
		//DEFAULT: 13f
		public static float fogFar = 300f;
				
		//The color ofthe fog in rgba.
		//DEFAULT: new Color(0.5f, 0.0f, 0.5f, 5f); <- pink
		public static Color fogColor = new Color(0.5f, 0.2f, 0.5f, 1.0f);
		
		
	/* 
	 * ***********************************************
	 * ******             LIGHTING             *******
	 * ***********************************************
	 */
		// Position of the lightsource
		//DEFAULT: new Vector3f(position.x, position.y, position.z);
		//       : or just = position; since they are bouth Vector3's
        public static Vector3f lightPosition = new Vector3f(3.0f, 1.0f, -10.0f);
		//public static Vector3f lightPosition = position;
        
        // light intensity
        private static float[] lightIntensity = new float[]{0.5f, 0.5f, 0.5f, 1.0f};
        		
        // light color
        private static float[] lightColor = new float[]{0.5f, 0.5f, 0.5f, 1.0f};

		
	/*
	 *  END OF CONFIG
	 */
	
	// init the mouse and keyboard handler
	private static InputHandler inputHandler = new InputHandler(walkingSpeed, mouseSpeed, position, rotation);
	
	
	
	// calculate fps
    private static int fps;
    private static long lastFPS;
    private static long lastFrame;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }
    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            if (printFPS) {
                System.out.println("FPS: " + fps);
            }
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public static void main(String[] args) {
        initDisplay();

        if (fullscreen) {
            Mouse.setGrabbed(true);
        } else {
            Mouse.setGrabbed(false);
        }

        if (!GLContext.getCapabilities().OpenGL11) {
            System.err.println("Your OpenGL version doesn't support the required functionality.");
            Display.destroy();
            System.exit(1);
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();


        // generate the texture for the floor, walls and ceiling
        int floorTexture = glGenTextures();
        Textures.genBoxTextures(floorTexture);
        
        // draw the ceiling
        int ceilingDisplayList = glGenLists(1);
        Shapes.drawSeiling(ceilingDisplayList);

        // draw the wall
        int wallDisplayList = glGenLists(1);
        Shapes.drawWall(wallDisplayList);

        // draw the floor
        int floorDisplayList = glGenLists(1);
        Shapes.drawFloor(floorDisplayList);

        // draw the pyramid!
        int objectDisplayList = glGenLists(1);
        Shapes.drawPyramid(objectDisplayList);
        
        // draw 3d model! (bunny)
        int bunnyObjectList = glGenLists(1);
        Shapes.draw3DModel(bunnyObjectList, new File("res/bunny.obj"));

        // prepare for fps counting!
        getDelta();
        lastFPS = getTime();
        
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_ALPHA_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glCullFace(GL_BACK);
//        //glEnable(GL_FOG);
//        
//        {
//        	FloatBuffer fogColours = BufferUtils.createFloatBuffer(4);
//        	fogColours.put(new float[]{fogColor.r, fogColor.g, fogColor.b, fogColor.a});
//        	glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
//        	fogColours.flip();
//        	glFog(GL_FOG_COLOR, fogColours);
//        	glFogi(GL_FOG_MODE, GL_LINEAR);
//        	glHint(GL_FOG_HINT, GL_NICEST);
//        	glFogf(GL_FOG_START, fogNear);
//        	glFogf(GL_FOG_END, fogFar);
//        	glFogf(GL_FOG_DENSITY, 0.005f);
//        }


        while (running) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            int delta = getDelta();
            glBindTexture(GL_TEXTURE_2D, floorTexture);

            glShadeModel(GL_SMOOTH);
            glEnable(GL_CULL_FACE);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_LIGHTING);
            glEnable(GL_LIGHT0);
            glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(lightColor));
            glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(lightIntensity));
            glCullFace(GL_BACK);
            glEnable(GL_COLOR_MATERIAL);
            glColorMaterial(GL_FRONT, GL_DIFFUSE);
            glCallList(floorDisplayList);
            glCallList(ceilingDisplayList);
            glCallList(wallDisplayList);
            
            glPushMatrix();
            glRotatef(180, 0.0f, 1.0f, 0.0f);
            glColor3f(1.0f, 0.0f, 0.0f);
            glCallList(bunnyObjectList);
            glColor3f(1.0f, 1.0f, 1.0f);
            glPopMatrix();
            
            glDisable(GL_LIGHTING);
            glCallList(objectDisplayList);
            glDisable(GL_CULL_FACE);
            glBindTexture(GL_TEXTURE_2D, 0);
            

            glLoadIdentity();
            glRotatef(rotation.x, 1, 0, 0);
            glRotatef(rotation.y, 0, 1, 0);
            glRotatef(rotation.z, 0, 0, 1);
            glTranslatef(position.x, position.y, position.z);
            
            glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[]{lightPosition.x, lightPosition.y, lightPosition.z, 1.0f}));
            
            
            // handles the keyboard
            inputHandler.handleKeyboardActions(running, resizable, objectDisplayList, delta, bunnyObjectList, delta, lightPosition);
            
            // handles the mouse
            inputHandler.handleMouseActions(maxLookDown, maxLookUp);
            // couldn't get this to fit in...
            // .... that's what she said ....
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                if (!Mouse.isGrabbed() || Display.isFullscreen()) {
                    running = false;
                } else {
                    Mouse.setGrabbed(false);
                }
            }
            
            if (resizable) {
                if (Display.wasResized()) {
                    glViewport(0, 0, Display.getWidth(), Display.getHeight());
                    glMatrixMode(GL_PROJECTION);
                    glLoadIdentity();
                    gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
                    glMatrixMode(GL_MODELVIEW);
                    glLoadIdentity();
                }
            }
            if (printFPS) {
                updateFPS();
            }
            Display.update();
            if (vsync) {
                Display.sync(60);
            }
            if (Display.isCloseRequested()) {
                running = false;
            }
            
            System.out.println("pX: " + position.x + " pY: " + position.y + " pZ: " + position.z + " | " +
            			       "rX: " + rotation.x + " rY: " + rotation.y + " rZ: " + rotation.z);
        }
        glDeleteTextures(floorTexture);
        glDeleteLists(floorDisplayList, 1);
        glDeleteLists(ceilingDisplayList, 1);
        glDeleteLists(wallDisplayList, 1);
        glDeleteLists(objectDisplayList, 1);
        glDeleteLists(bunnyObjectList, 1);
        Display.destroy();
        System.exit(0);
    }
    
    private static void initDisplay() {
    	try {
            if (fullscreen) {
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            } else {
                Display.setResizable(resizable);
                Display.setDisplayMode(new DisplayMode(1024, 768));
            }
            Display.setTitle("Minefront Pre-Alpha 0.02 LWJGL Port");
            Display.setVSyncEnabled(vsync);
            Display.create();
        } catch (LWJGLException ex) {
            System.err.println("Display initialization failed.");
            System.exit(1);
        }
	}

	private static FloatBuffer asFloatBuffer(float[] value){
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(value.length);
    	buffer.put(value);
    	buffer.flip();
    	return buffer;
    }
}