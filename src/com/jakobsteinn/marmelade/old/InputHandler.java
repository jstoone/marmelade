package com.jakobsteinn.marmelade.old;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;


public class InputHandler {

	Vector3f position;
	Vector3f rotation;
	int walkingSpeed;
	int mouseSpeed;

	float gravity = 0.05f;
	public float jumpY = 0;

	public InputHandler(int walkingSpeed, int mouseSpeed, Vector3f position,
			Vector3f rotation) {
		this.walkingSpeed = walkingSpeed;
		this.mouseSpeed = mouseSpeed;
		this.position = position;
		this.rotation = rotation;
	}

	public void handleMouseActions(int maxLookDown, int maxLookUp) {
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
			float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
			if (rotation.y + mouseDX >= 360) {
				rotation.y = rotation.y + mouseDX - 360;
			} else if (rotation.y + mouseDX < 0) {
				rotation.y = 360 - rotation.y + mouseDX;
			} else {
				rotation.y += mouseDX;
			}
			if (rotation.x - mouseDY >= maxLookDown
					&& rotation.x - mouseDY <= maxLookUp) {
				rotation.x += -mouseDY;
			} else if (rotation.x - mouseDY < maxLookDown) {
				rotation.x = maxLookDown;
			} else if (rotation.x - mouseDY > maxLookUp) {
				rotation.x = maxLookUp;
			}
		}
	}

	public void handleKeyboardActions(boolean running, boolean resizeable,
			int fov, int delta, float zNear, float zFar, Vector3f lightPosition) {
		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP)
				|| Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN)
				|| Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				|| Keyboard.isKeyDown(Keyboard.KEY_D);

		boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

		boolean moveFaster = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		boolean moveSlower = Keyboard.isKeyDown(Keyboard.KEY_TAB);
		
		if (moveFaster && !moveSlower) {
			walkingSpeed *= 4f;
		}
		if (moveSlower && !moveFaster) {
			walkingSpeed /= 10f;
		}

		if (keyUp && keyRight && !keyLeft && !keyDown) {
			float angle = rotation.y + 45;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyUp && keyLeft && !keyRight && !keyDown) {
			float angle = rotation.y - 45;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyUp && !keyLeft && !keyRight && !keyDown) {
			float angle = rotation.y;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyDown && keyLeft && !keyRight && !keyUp) {
			float angle = rotation.y - 135;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyDown && keyRight && !keyLeft && !keyUp) {
			float angle = rotation.y + 135;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyDown && !keyUp && !keyLeft && !keyRight) {
			float angle = rotation.y;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = -(walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyLeft && !keyRight && !keyUp && !keyDown) {
			float angle = rotation.y - 90;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}
		if (keyRight && !keyLeft && !keyUp && !keyDown) {
			float angle = rotation.y + 90;
			Vector3f newPosition = new Vector3f(position);
			float diagonal = (walkingSpeed * 0.0002f) * delta;
			float adjecent = diagonal * (float) Math.cos(Math.toRadians(angle));
			float hypotenuse = (float) (Math.sin(Math.toRadians(angle)) * diagonal);
			newPosition.z += adjecent;
			newPosition.x -= hypotenuse;
			position.z = newPosition.z;
			position.x = newPosition.x;
		}

		if (flyUp && !flyDown && position.y >= -9) {
			double newPositionY = (walkingSpeed * 0.0002) * delta;
			position.y -= newPositionY;
		}
		if (flyDown && !flyUp && position.y <= 0) {
			double newPositionY = (walkingSpeed * 0.0002) * delta;
			position.y += newPositionY;
		}

		if (moveFaster && !moveSlower) {
			walkingSpeed /= 4f;
		}
		if (moveSlower && !moveFaster) {
			walkingSpeed *= 10f;
		}
		while (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				Mouse.setGrabbed(true);
			}
			if (Mouse.isButtonDown(1)) {
				Mouse.setGrabbed(false);
			}

		}
		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				mouseSpeed += 1;
				System.out
						.println("Mouse speed changed to " + mouseSpeed + ".");
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
				if (mouseSpeed - 1 > 0) {
					mouseSpeed -= 1;
					System.out.println("Mouse speed changed to " + mouseSpeed
							+ ".");
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				System.out.println("Walking speed changed to " + walkingSpeed
						+ ".");
				walkingSpeed += 1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				System.out.println("Walking speed changed to " + walkingSpeed
						+ ".");
				walkingSpeed -= 1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
				try {
					Display.setFullscreen(!Display.isFullscreen());
					if (!Display.isFullscreen()) {
						Display.setResizable(resizeable);
						Display.setDisplayMode(new DisplayMode(1024, 768));
						glViewport(0, 0, Display.getWidth(),
								Display.getHeight());
						glMatrixMode(GL_PROJECTION);
						glLoadIdentity();
						gluPerspective(fov, (float) Display.getWidth()
								/ (float) Display.getHeight(), zNear, zFar);
						glMatrixMode(GL_MODELVIEW);
						glLoadIdentity();
					} else {
						glViewport(0, 0, Display.getWidth(),
								Display.getHeight());
						glMatrixMode(GL_PROJECTION);
						glLoadIdentity();
						gluPerspective(fov, (float) Display.getWidth()
								/ (float) Display.getHeight(), zNear, zFar);
						glMatrixMode(GL_MODELVIEW);
						glLoadIdentity();
					}
				} catch (LWJGLException ex) {
					Logger.getLogger(MarmeladeOld.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
	}

}
