package Engine;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	
	private boolean fullScreen;
	
	private static int Width;
	private static int Height;

	public Window(int w, int h, boolean fullScreen) {
		Width = w;
		Height = h;
		this.fullScreen = fullScreen;
		initFrame();
		addKeyListener(Engine.controll);
		addMouseMotionListener(Engine.controll);
	}

	public void initFrame() {
		frame = new JFrame("TROM");
		frame.add(this);
		this.setPreferredSize(new Dimension(Width, Height));
		frame.setUndecorated(fullScreen);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		createBufferStrategy(2);
	}
}
