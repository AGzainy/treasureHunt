package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	// screen settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //40X40 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow;//576 pixels
	
	//world settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//system
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();

	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetsSetter aSetter = new AssetsSetter(this);
	TileManager tileM = new TileManager(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// entity and objects
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	// game state
	public int gameState;
	public final int playState =1;
	public final int pauseState = 2;
	
	//frame per second
	int fps = 60;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //enabling this can improve game's rendering performance. 
		this.addKeyListener(keyH);
		this.setFocusable(true);//gamePanel can be focused to receive key input.
		
		
	}
	//i call this method in main before starting the game thread
	public void setupGame() {
		
		aSetter.setObject();
		
		playMusic(0);
		//stopMusic();
		gameState = playState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
			}
			
		}
		
	}
	
	//or
//	public void run() {
//		
//		
//		double drawInterval = 1000000000/fps; //0.01666bseconds
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//
//		//1- updates info "player's position". 
//		update();
//		//2- draws the screen with the updates info
//		repaint(); //the way to call paintComponent.
//		
//		
//		try {
//			double remainingTime = nextDrawTime - System.nanoTime();
//			remainingTime = remainingTime/1000000;
//			
//			if(remainingTime < 0) {
//				remainingTime = 0;
//			}
//
//			Thread.sleep((long) remainingTime);
//			
//			nextDrawTime += drawInterval;
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//	}}

	public void update() {
		
		if(gameState == playState) {
		
		player.update();
		}
		if(playState == pauseState) {
			//no
		}
	}
	
	public void paintComponent(Graphics g) { //graphics is a java class for drawing
		
		super.paintComponent(g);
		//convert graphics to graphics 2d. graphics2d extends graphics to provide more sophisticated control over all kinds of edits.
        Graphics2D g2 = (Graphics2D)g; 
        
        // debug:
        long drawStart = 0;

        if(keyH.checkDrawTime == true) {
        drawStart = System.nanoTime();
        }
        
        //tile:
        tileM.draw(g2);
        
        //object:
        for(int i = 0; i< obj.length; i++) {
        	if(obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        
        //player:
        player.draw(g2);
        
        //UI
        ui.draw(g2);
        
        //debug:
        if(keyH.checkDrawTime == true) {

        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time: " + passed, 10, 400);
        System.out.println("draw Time "+ passed);
        }
        g2.dispose(); 
        //dispose of this graphics context and release any system resources that it is using.
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	

}
