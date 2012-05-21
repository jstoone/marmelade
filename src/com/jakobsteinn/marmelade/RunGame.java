package com.jakobsteinn.marmelade;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class RunGame {
	
	public RunGame(){
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
	}

}
