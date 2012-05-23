package com.jakobsteinn.marmelade.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Options extends Launcher {
	
	
	private int width = 540;
	private int height = 440;
	private JButton ok, back;
	private Rectangle rok, rresolution;
	private Choice resolution = new Choice();

	public Options(){
		super(1);
		setTitle("Options");
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		
		drawButtons();
	}
	
	private void drawButtons(){
		ok = new JButton("OK");
		rok = new Rectangle((width-100), (height-70), buttonWidth, buttonHeight-10);
		ok.setBounds(rok);
		window.add(ok);
		
		rresolution = new Rectangle(50, 80, 80, 25);
		resolution.setBounds(rresolution);
		resolution.add("640, 480");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.select(1);
		window.add(resolution);
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Launcher(0);
			}
		});
		
	}
}
