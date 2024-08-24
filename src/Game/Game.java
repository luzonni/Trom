package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Engine.Controll;
import Engine.Engine;

public class Game {

	public static List<Player> players;
	
	public static int Width;
	public static int Height;
	
	public static Color[] colors = {Color.red, Color.MAGENTA, Color.CYAN, Color.yellow, Color.GREEN, Color.ORANGE, Color.PINK, Color.BLUE};
	
	public static int RGB[];
	
	public Game() {
		Width = Engine.GameImage.getWidth();
		Height = Engine.GameImage.getHeight();
		players = new ArrayList<Player>();
		
	}
	
	public void tick() {
		if(Controll.Pressed && Controll.KeyCode() == KeyEvent.VK_ENTER && players.size() < colors.length) {
			Controll.Pressed = false;
			players.add(new Player(Controll.xMouse, Controll.yMouse).setColor(colors[players.size()]));
		}
		for(int i = 0; i < players.size(); i++) {
			players.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(g);
		}
	}
	
}
