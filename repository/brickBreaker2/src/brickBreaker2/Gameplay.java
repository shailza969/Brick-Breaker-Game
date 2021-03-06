package brickBreaker2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel
	implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 28;
	private Timer timer;
	private int delay = 8;
	private int Highestscore = 0;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -2;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		map = new MapGenerator(4,7);
	}
	
	public void paint(Graphics g)
	{
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D) g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: "+score, 500, 30);
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Highest Score: "+Highestscore, 100, 30);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY, 20, 20);
		
		if(ballposX == 120 && ballposY == 350 && playerX == 310&&totalBricks == 28&& score ==0) {
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Press 1 for EASY",250, 300);
		g.drawString("Press 2 for MEDIUM", 250, 330);
		g.drawString("Press 3 for HARD", 250, 360);
		
		}
		
		if(totalBricks<=0) {
			play =false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("YOU WON", 260, 300);
			g.drawString("Scores: "+score, 260, 350);
			
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press enter to restart ", 240, 400);
		}
		
		if(ballposY > 570) {
			play =false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 35));
			g.drawString("Game over", 260, 300);
			g.drawString("Scores: "+score, 260, 350);
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press enter to restart ", 240, 400);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
				ballYdir = -ballYdir;
			
			A: for (int i =0; i<map.map.length; i++)
			{
				for(int j=0; j< map.map[0].length; j++) {
					if(map.map[i][j]>0) {
						int brickX =j*map.brickWidth + 70;
						int brickY =i*map.brickHeight +50;
						int brickWidth= map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle (brickX,brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle (ballposX,ballposY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score +=5;
							
							if(ballposX +19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}else
								ballYdir = -ballYdir;
							
							break A;
							
						}
					}
			}
			}
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX <0)
				ballXdir = -ballXdir;
			if(ballposY <0)
				ballYdir = -ballYdir;
			if(ballposX >670)
				ballXdir = -ballXdir;
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600)
				playerX= 600;
			else
				moveRight();
			
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(playerX <= 10)
				playerX= 10;
			else
				moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				ballposX = 120;
				ballposY = 350;
				ballXdir = -2;
				ballYdir = -2;
				playerX = 310;
				totalBricks = 28;
				map = new MapGenerator(4,7);
				if(Highestscore<score)
					Highestscore = score;
				score = 0;
				repaint();
				
				
		}}
		if(e.getKeyCode() == KeyEvent.VK_1)
		{
			play = true;
			ballposX = 120;
			ballposY = 350;
			ballXdir = -2;
			ballYdir = -2;
			playerX = 310;
			totalBricks = 28;
			map = new MapGenerator(4,7);
			if(Highestscore<score)
				Highestscore = score;
			score = 0;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_2)
		{
			play = true;
			ballposX = 120;
			ballposY = 350;
			ballXdir = -3;
			ballYdir = -4;
			playerX = 310;
			totalBricks = 28;
			map = new MapGenerator(4,7);
			if(Highestscore<score)
				Highestscore = score;
			score = 0;
			repaint();
		}

	
	if(e.getKeyCode() == KeyEvent.VK_3)
	{
		play = true;
		ballposX = 120;
		ballposY = 350;
		ballXdir = -5;
		ballYdir = -5;
		playerX = 310;
		totalBricks = 28;
		map = new MapGenerator(4,7);
		if(Highestscore<score)
			Highestscore = score;
		score = 0;
		repaint();
	}}
	
	

	private void moveRight() {
		// TODO Auto-generated method stub
		
		play = true;
		playerX +=20;
	}

	private void moveLeft() {
		
			play = true;
			playerX -=20;
		}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
