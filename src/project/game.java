package project;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;

import javax.swing.Timer;



public class game extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score1 = 0;
	private int score2 = 0;
	private int totalBricks = 21;
	private Timer timer; 	
	private int delay = 8;	
	private int playerX = 310;
	private int playerX2 = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	
	
	public game() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	    timer = new Timer(delay, this);
	    timer.start();
	}
	
	public void paint (Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(1,1, 692, 592);
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
	//	g.setColor(Color.WHITE);
	//	g.setFont(new Font("serif",Font.BOLD, 25));
	//	g.drawString("Start Game", 300, 300);
		
		g.setColor(Color.WHITE);
	    g.setFont(new Font("serif",Font.BOLD, 40));
	    g.drawString("" +score1, 625, 75);
	    
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("serif",Font.BOLD, 40));
	    g.drawString("" +score2, 625, 550);
		
		g.setColor(Color.BLUE);
		g.fillRect(playerX, 550	, 100 , 8);
		
		g.setColor(Color.BLUE);
		g.fillRect(playerX2, 40, 100, 8);
		
		g.setColor(Color.WHITE);
		g.fillOval(ballposX, ballposY, 20 , 20);
		
		
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;			
			score1 += 1; 
			ballposX = 300;
		    ballposY = 300;

			
			
		}
		
		if (ballposY < 10) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			score2 += 1;
			
			ballposX = 300;
			ballposY = 300;
		
		} 
		
		if (score1 > 6) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif",Font.BOLD, 40));
			g.drawString("Player 1 Wins", 300, 300);
			play = false;
		}
		
		if (score2 > 6) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif",Font.BOLD, 40));
			g.drawString("Player 2 Wins", 250, 300);
			play = false;
		}
		
		
		g.dispose();
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
			
		if (play) {

			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			if (new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX2, 40, 100,8))) {
				ballYdir = -ballYdir; 
			}
		    ballposX += ballXdir;
		    ballposY += ballYdir;
			if (ballposX < 0 ) {
				ballXdir = -ballXdir;
			}
			if (ballposX > 670 ) {
				ballXdir = -ballXdir;
			}
		}
		
		
		repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}	
	@Override
	public void keyTyped(KeyEvent e) {}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			if(playerX2 >= 600) {
				playerX2 = 600;
			} else {
				moveRight2();
			}	
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(playerX2 <= 10) {
				playerX2 = 10;
			} else {
				moveLeft2();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 300; 
				ballposY = 300;
				ballXdir = -2;
				ballYdir = -2;
				timer.start();
				if (play) {
				ballposX += ballXdir;
				ballposY += ballYdir;
				}
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	public void moveRight2() {
		play = true;
		playerX2 += 20;
	}
	public void moveLeft2() {
		play = true;
		playerX2 -= 20;
	}

}