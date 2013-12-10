package me.ci;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class OneWish extends JFrame{
	private static GameScreen game;
	private static Music music;
	private static Player player;
	public static final String FOLDER = "C:\\Users\\TheDudeFromCI\\Desktop";
	public OneWish(){
		init();
		game=new GameScreen();
		game.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(final MouseEvent e){
				for(Entity a : game.getEntities())a.pathFindTo(e.getX()/GameScreen.ZOOM-1, e.getY()/GameScreen.ZOOM-1);
			}
		});
		closeMenu();
		makePlayer();
		game.loadMap(Util.loadMap("Test"));
		game.addEntity(new RandomWalker(250));
		game.addEntity(new RandomWalker(250));
		game.addEntity(new RandomWalker(250));
		game.addEntity(new RandomWalker(250));
		setVisible(true);
	}
	private void init(){
		setTitle("One Wish");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(320, 240));
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(final KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_LEFT)player.move(player.getX()-1, player.getZ(), Direction.WEST, false);
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)player.move(player.getX()+1, player.getZ(), Direction.EAST, false);
				if(e.getKeyCode()==KeyEvent.VK_UP)player.move(player.getX(), player.getZ()-1, Direction.NORTH, false);
				if(e.getKeyCode()==KeyEvent.VK_DOWN)player.move(player.getX(), player.getZ()+1, Direction.SOUTH, false);
			}
		});
	}
	private void makePlayer(){
		player=new Player();
		player.setSpeed(300);
		game.addEntity(player);
	}
	private void setPanel(final JPanel panel){
		getContentPane().removeAll();
		getContentPane().add(panel);
	}
	public void openMenu(final Menu menu){ setPanel(menu); }
	public void closeMenu(){ setPanel(game); }
	public static final void main(final String[] args){
		try{ UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(final Throwable e){ e.printStackTrace(); }
		new OneWish();
	}
	public static void playMusic(final Music m){
		music=m;
		//TODO
	}
	public static Music getPlayingMusic(){ return music; }
	public static void loadMap(final Map map){ game.loadMap(map); }
	public static Player getPlayer(){ return player; }
	public static Map getMap(){ return game.getMap(); }
	public static void renderGame(){ game.render(); }
	public static Tile getTileAt(final int x, final int z){ return game.getTileAt(x, z); }
}