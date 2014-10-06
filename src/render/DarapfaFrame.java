package render;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import map.CircleObstruction;
import map.SquareObstruction;
import map.Map;
import map.MapReader;

public class DarapfaFrame extends JFrame implements KeyListener, ActionListener {
	public static int RENDER_DELAY = 20; // ± 1000/60 -> 60 frames a second

	private Camera cam;
	private Map map;
	//private MapStub map;
	private HashSet<Integer> keysDown;

	public DarapfaFrame()	{
		super("David and Roland's Amazing Path Finding Algorithm");
		MapCanvas map_canvas;
		setSize(1000, 1000);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		//map = Map.generateRandomMap(40, 40, 100, 30);
		//MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Arcadia 02.pmp", "placeholder");
		MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Sandbox - Romans.pmp", "placeholder");
		//MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Jaxartes River (2).pmp", "placeholder");
		//MapReader rawr = new MapReader("C:/Users/FNB/git/0ad/0ad/binaries/data/mods/public/maps/scenarios/Battle for the Tiber.pmp", "placeholder");
		map = new Map(rawr);
		cam = new Camera(0, 0, 0, 30000, 20);
		keysDown = new HashSet<Integer>();

		//map.addObstruction(new SquareObstruction(20, 20, 50, 50, (float)Math.toRadians(50)));
		//map.addObstruction(new SquareObstruction(100, 200, 50, 50, (float)Math.toRadians(20)));
		//map.addObstruction(new CircleObstruction(200, 200, 50));
		map_canvas = new MapCanvas(map, cam);
		JScrollPane pane = new JScrollPane(map_canvas, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//JScrollPane pane = new JScrollPane();
		pane.getVerticalScrollBar().setUnitIncrement( 16 );
        pane.getVerticalScrollBar().setValue( 0 );
        // Using the default VERTICAL_SCROLLBAR_AS_NEEDED will use scrollbars even when
        // not strictly needed.  If you want precise control you must decide for yourself
        // dynamically.
        pane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
		//pane.add(map_canvas);
		pane.setViewportView(map_canvas);
		//setContentPane(pane);
		//getContentPane().add(new MapCanvas(map, cam));
		getContentPane().add(pane);

		setVisible(true);

		addKeyListener(this);
		// Create the timer that does our rendering and game logic
		new Timer(RENDER_DELAY, this).start();
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
	
	@Override
	// This is given to the timer and run every frame
	public void actionPerformed(ActionEvent evt) {
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
