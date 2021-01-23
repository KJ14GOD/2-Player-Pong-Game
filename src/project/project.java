package project;
import java.awt.*;

import javax.swing.*;

public class project extends JFrame {
	
	public static void main (String [] args) {
		JFrame gameplay = new JFrame("Game");
		game breakerBall = new game();
		gameplay.setBounds(10,10,700,700);
		gameplay.setTitle("Game");
		gameplay.setResizable(true);
		gameplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameplay.setVisible(true);
		gameplay.add(breakerBall);
		
		
	}
}
