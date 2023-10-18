package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	static final int screen_Width=750;
	static final int screen_Height=750;

	static final int unit_Size=30;
	static final int game_Units=(screen_Width*screen_Height)/unit_Size;
	static final int delay=85;
	final int x[]=new int[game_Units];
	final int y[]=new int[game_Units];
	int bodyParts=5;
	int applesEaten;
	int applex;
	int appley;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;

	public GamePanel() {
		// TODO Auto-generated constructor stub
		random=new Random();
		this.setPreferredSize(new Dimension(screen_Width,screen_Height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple();
		running=true;
		timer=new Timer(delay,this);
		timer.start();

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}
	public void draw(Graphics g) {
		if(running) {
		for(int i=0;i<screen_Height/unit_Size;i++) {
			g.drawLine(i*unit_Size, 0, i*unit_Size, screen_Height);
			g.drawLine(0, i*unit_Size, screen_Width, i*unit_Size);
		}
		g.setColor(Color.RED);
		g.fillOval(applex, appley, unit_Size, unit_Size);

		for(int i=0;i<bodyParts;i++) {
			if(i==0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], unit_Size, unit_Size);
			}else {
				g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(250),random.nextInt(250),random.nextInt(250)));
				g.fillRect(x[i], y[i], unit_Size, unit_Size);
			}
		}
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score :"+applesEaten, (screen_Width-metrics.stringWidth("Score :"+applesEaten))/2, g.getFont().getSize());
	}
		else {
			gameOver(g);
		}
	}
	public void newApple() {
		applex=random.nextInt((int)(screen_Width/unit_Size))*unit_Size;
		
		
		appley=random.nextInt((int)(screen_Height/unit_Size))*unit_Size;

	}

	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];

		}
		switch(direction) {
		case 'U':
			y[0]=y[0]-unit_Size;
			break;
		case 'D':
			y[0]=y[0]+unit_Size;
			break;
		case 'L':
			x[0]=x[0]-unit_Size;
			break;
		case 'R':
			x[0]=x[0]+unit_Size;
			break;





		}

	}
	public void checkApple() {
		if((x[0]==applex) && (y[0]==appley)) {
			bodyParts++;;
			applesEaten+=1;
			newApple();
		}

	}
	public void checkCollisions() {
		//check if head collides with body
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i]) && (y[0]==y[i])){
				running =false;

			}
		}
		//check if head touches left border
		if(x[0]<0) {
			running=false;
		}
		//check if head touches right border
		
		if(x[0]>screen_Width) {
			running=false;
		}
		//check if head touches top border
		if(y[0]<0) {
			running=false;

		}
		//check if head touches bottom border
		if(y[0]>screen_Height) {
			running=false;
		}
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g){
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Game Over", (screen_Width-metrics.stringWidth("RE_TRY"))/2, screen_Height/2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
	repaint();

	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction !='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction !='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction !='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction !='U') {
					direction='D';
				}
				break;


			}

		}
	}

}
