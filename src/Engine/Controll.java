package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Controll implements KeyListener, MouseMotionListener{

	public static int xMouse;
	public static int yMouse;
	
	public static boolean Pressed;
	private static int Key;
	
	private boolean works;
	private int lastIndex;
	public static int[] LeftKeys = new int[4];
	
	public static char[] LeftkeysC = new char[4];
	
	public static int KeyCode() {;
		return Key;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		xMouse = e.getX()/Engine.Scale;
		yMouse = e.getY()/Engine.Scale;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		Pressed = true;
		Key = e.getKeyCode();
		
		if(Key == KeyEvent.VK_ESCAPE) {
			works = true;
			Engine.Pause = true;
		}
		if(works) setKeys(e);
	}
	
	private void setKeys(KeyEvent e) {
		if(Key != KeyEvent.VK_ESCAPE && Key != KeyEvent.VK_ENTER) {
			LeftKeys[lastIndex] = Key;
			LeftkeysC[lastIndex] = e.getKeyChar();
			lastIndex++;
		}
		if(lastIndex == LeftKeys.length) {
			lastIndex = 0;
			works = false;
			Engine.Pause = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Pressed = false;
	}

}
