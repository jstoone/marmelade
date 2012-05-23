package com.jakobsteinn.marmelade;

import com.jakobsteinn.marmelade.gui.Launcher;

/**
* A LWJGL port of the awesome MineFront Pre-ALPHA 0.02 Controls: W/UP =
* forward; A/LEFT = strafe left; D/RIGHT = strafe right; S/DOWN = backward;
* SPACE = fly up; SHIFT = fly down;
*
* @author Oskar Veerhoek, Yan Chernikov, Jakob Steinn
*/
public class RunGame {
	
	private static int boxDispalyLists;
	
	public static void main(String[] args) {
		new Launcher(0);
	}
}
