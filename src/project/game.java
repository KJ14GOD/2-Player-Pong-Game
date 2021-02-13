package project;


import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.Timer;



public class game extends JPanel implements KeyListener, ActionListener{
	
	private String ip = "localhost";
	private Scanner scanner = new Scanner(System.in);
	private int port = 22222;
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ServerSocket serverSocket;
	private boolean unableToCommunicateWithOpponent = false;
	private String waitingString = "Waiting for another player";
	private String unableToCommunicateWithOpponentString = "Unable to communicate with opponent.";

	private boolean play = false;
	private int score1 = 0;
	private int score2 = 0;
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
	    
	    System.out.println("Please input the IP: ");
	    ip = scanner.nextLine();
	    System.out.println("Please input the port: ");
	    port = scanner.nextInt();
	    while (port < 1 || port > 65535) {
	    	System.out.println("The port entered was invalid. Please enter another port");
	    	port = scanner.nextInt();
	    }

		if (!connect()) initializeServer();
	}
	
	public void paint (Graphics g) {
		
	
		g.setColor(Color.BLACK);
		g.fillRect(1,1, 692, 592);
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString("Start Game", 300, 300);
		
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
	
	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	}
	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}