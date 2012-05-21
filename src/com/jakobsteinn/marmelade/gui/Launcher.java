package com.jakobsteinn.marmelade.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Launcher extends JFrame {
	
	private JPanel window = new JPanel();
	private JButton play, options, help, exit;
	private Rectangle rplay, roptions, rhelp, rexit;
	
	private int width = 240;
	private int height = 320;
	private int buttonWidth = 80;
	private int buttonHeight = 40;
	
	public Launcher(){
		setTitle("Marmelade Launcher");
		setSize(new Dimension(240, 320));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		
		drawButtons();
	}
	
	private void drawButtons(){
		play = new JButton("Play");
		rplay = new Rectangle((width/2)-(buttonWidth/2), 90, buttonWidth, buttonHeight);
		play.setBounds(rplay);
		window.add(play);
		
		options = new JButton("Options");
		roptions = new Rectangle((width/2)-(buttonWidth/2), 140, buttonWidth, buttonHeight);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help");
		rhelp = new Rectangle((width/2)-(buttonWidth/2), 190, buttonWidth, buttonHeight);
		help.setBounds(rhelp);
		window.add(help);
		
		exit = new JButton("Exit");
		rexit = new Rectangle((width/2)-(buttonWidth/2), 240, buttonWidth, buttonHeight);
		exit.setBounds(rexit);
		window.add(exit);
		
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}

}
