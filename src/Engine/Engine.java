package Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Game.Game;

public class Engine implements Runnable{
	
	public static Thread thread;
	public static boolean Game_Running;
	
	public static BufferedImage GameImage;
	
	public static Controll controll = new Controll();
	
	public static Window window;
	public static BufferStrategy buffer;
	public static int Scale = 2;
	
	public static Game game;
	
	public static boolean Pause;
	
	public Engine() {
		//Local de criação
		window = new Window(1980, 1080, true);
//		Scale = window.getWidth()/640;
		GameImage = new BufferedImage(window.getWidth()/Scale, window.getHeight()/Scale, BufferedImage.TYPE_INT_RGB);
		Start();
		game = new Game();
	}
	
	public synchronized void Start() { 
		thread = new Thread(this);
		Game_Running = true;
		thread.start();
	}
	
	public synchronized void Stop() {
		Game_Running = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(!Pause) game.tick();
	}
	
	public void render() {
		if(buffer == null) {
    		buffer = window.getBufferStrategy();
    	}
		
		Graphics g = GameImage.getGraphics();
		
    	g.setColor(Color.black);
    	g.fillRect(0, 0, window.getWidth(), window.getHeight());
    	
    	//Game
    	game.render(g);
    	Game.RGB = GameImage.getRGB(0, 0, GameImage.getWidth(), GameImage.getHeight(), Game.RGB, 0, GameImage.getWidth());
    	
    	if( Pause ) renderUi(g);
    	
    	g = buffer.getDrawGraphics();
    	
    	g.drawImage(GameImage, 0, 0, window.getWidth(), window.getHeight(), null);
    	
    	g.dispose();
    	buffer.show();
	}
	
	public void renderUi(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.WHITE);
		g.drawString("Up: ["+Controll.LeftkeysC[0]+"] / Left: ["+Controll.LeftkeysC[1]+"] / Down: ["+Controll.LeftkeysC[2]+"] / Right: ["+Controll.LeftkeysC[3]+"]", 1, 10);
	}
	
	public static void main(String[] args) {
		new Engine();
	}

	@Override
	public void run() {
		window.requestFocus();
		//Run
		long lastTime = System.nanoTime();
		double amountOfTicks = 144.0;
		double ns = 1000000000 / amountOfTicks; // 1000000000
		double delta = 0;
		window.requestFocus();
		while(Game_Running) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1 && delta <= 10) {
				tick();
				render();
				delta--;
			}else {
				while(delta >= 1){
					tick();
					delta--;
				}
			}
		}
		Stop();
	}
	
	

}
