package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Engine.Controll;

public class Player {
	
	protected int X;
	protected int Y;
	
	public boolean Alive;
	public Color color = Color.white;
	public List<Trail> trails;
	
	private String side = "";
	
	//Controll
	public Grip[] controll;
	
	public Player(int x, int y) {
		this.X = x;
		this.Y = y;
		trails = new ArrayList<Trail>();
		setControll();
	}
	
	public void tick() {
		control();
		if(Alive) trails.add(new Trail(X, Y).setColor(color));
		MOVE();
		SCREEN();
		for(int i = 0; i < trails.size(); i++) {
			trails.get(i).tick();
			if(trails.get(i).getTicks() >= 255) {
				trails.remove(trails.get(i));
			}
		}
		//Collision with some trail
		int color = Game.RGB[X+Y*Game.Width];
		int R = (color >> 16) & 0xff;	//Red
		int G = (color >> 8) & 0xff;	//Green
		int B = color & 0xff;			//Blue
		if(R > 50 || G > 50 || B > 50) {
			Game.players.remove(this);
		}
	}
	
	public void C() {
		
	}
	
	private void MOVE() {
		switch(side) {
		case "left": X--;
			break;
		case "right": X++;
			break;
		case "up": Y--;
			break;
		case "down": Y++;
			break;
		}
	}
	
	private void SCREEN() {
		if(this.X > Game.Width-1) {
			this.X = 0;
		}else if(this.X < 0) {
			this.X = Game.Width-1;
		}
		if(this.Y > Game.Height-1) {
			this.Y = 0;
		}else if(this.Y < 0) {
			this.Y = Game.Height-1;
		}
	}
	
	private void control() {
		if(controll[0].press(Controll.KeyCode()) && side != "down") {
			side = "up";
			Alive = true;
		}
		if(controll[1].press(Controll.KeyCode()) && side != "right") {
			side = "left";
			Alive = true;
		}
		if(controll[2].press(Controll.KeyCode()) && side != "up") {
			side = "down";
			Alive = true;
		}
		if(controll[3].press(Controll.KeyCode()) && side != "left") {
			side = "right";
			Alive = true;
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < trails.size(); i++) {
			trails.get(i).render(g);
		}
	}
	
	public Player setSide(String side) {
		this.side = side;
		return this;
	}
	
	public Player setColor(Color color) {
		this.color = color;
		return this;
	}
	
	public boolean collisionTrail(Player player, int x, int y) {
		for(int i = 0; i < player.trails.size(); i++) {
			Player.Trail t = player.trails.get(i);
			if(t.getX() == x && t.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	private Player setControll() {
		controll = new Grip[4];
		for(int i = 0; i < controll.length; i++) {
			controll[i] = new Grip(Controll.LeftKeys[i]);
		}
		return this;
	}
	
	private static class Trail extends Player{
		
		protected double ticks;
		public Color color;
		
		public Trail(int x, int y) {
			super(x, y);
		}
		
		public void tick() {
			ticks += 0.25;
		}
		
		public Trail setColor(Color color) {
			this.color = color;
			return this;
		}
		
		public int getX() {
			return this.X;
		}
		
		public int getY() {
			return this.Y;
		}
		
		public int getTicks() {
			return (int)ticks;
		}
		
		public void render(Graphics g) {
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255 - getTicks()));
			g.fillRect(X, Y, 1, 1);
		}
		
	}
	
	private static class Grip {
		
		private boolean Pressed;
		private int Code;
		
		public Grip(int Code) {
			this.Code = Code;
		}
		
		public boolean press(int code) {
			Pressed = Controll.Pressed;
			if(code == Code && Pressed) {
				return true;
			}
			return false;
		}
		
	}
	
}
