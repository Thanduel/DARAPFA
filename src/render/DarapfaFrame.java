package render;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JFrame;

import render.map.Camera;
import render.map.MapCanvas;
import render.map.MapStub;

public class DarapfaFrame extends JFrame implements KeyListener, Runnable {
	private Camera cam;
	private MapStub map;
	private HashSet<Integer> keysDown;
	
	public DarapfaFrame()	{
		super("David and Roland's Amazing Path Finding Algorithm");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		map = MapStub.generateRandomMap(40, 40, 100, 30);
		cam = new Camera(0, 0, 0, 30, 20);
		keysDown = new HashSet<Integer>();
		
		getContentPane().add(new MapCanvas(map, cam));
		
		setVisible(true);
		
		addKeyListener(this);
		
		new Thread(this).start();
	}
	
	public void run()	{
		try	{
			while(true)	{
				Thread.sleep(10);
				
				if(keysDown.contains(KeyEvent.VK_UP))
					cam.move(0, -1);
				if(keysDown.contains(KeyEvent.VK_DOWN))
					cam.move(0, 1);
				if(keysDown.contains(KeyEvent.VK_LEFT))
					cam.move(-1, 0);
				if(keysDown.contains(KeyEvent.VK_RIGHT))
					cam.move(1, 0);
				
				render();
			}
		}
		catch(InterruptedException ie)	{
			System.out.println("Darapfa session terminated");
		}
	}
	
	public void render()	{
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove(e.getKeyCode());
	}
}
