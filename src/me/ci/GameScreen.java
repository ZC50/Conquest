package me.ci;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameScreen extends JPanel{
	private HashMap<Point,Tile> tiles = new HashMap<>();
	private ArrayList<Entity> entities = new ArrayList<>();
	private double camX = 1;
	private double camZ = 1;
	private boolean followplayer = false;
	private Map map;
	public static final int ZOOM = 32;
	public static final int IMAGE_SIZE = 32;
	public static final float MOTION_BLUR = 0f;
	public GameScreen(){
		new Timer().schedule(new TimerTask(){
			public void run(){ repaint(); }
		}, 10, 10);
	}
	@Override
	public void paint(final Graphics g1){
		try{
			final int width = getWidth();
			final int height = getHeight();
			if(followplayer){
				camX=-OneWish.getPlayer().getX()+(width/ZOOM/2);
				camZ=-OneWish.getPlayer().getZ()+(height/ZOOM/2);
			}
			final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g = img.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			Tile tile;
			int x;
			int z;
			for(Point p : tiles.keySet()){
				tile=tiles.get(p);
				x=(int)((p.getX()+camX)*ZOOM);
				z=(int)((p.getY()+camZ)*ZOOM);
				g.drawImage(tile.getImage(), x, z, ZOOM+x, ZOOM+z, 0, 0, IMAGE_SIZE, IMAGE_SIZE, this);
			}
			reorderEntityList();
			for(Entity e : entities){
				x=(int)((e.getX()-e.getCollideX()+camX)*ZOOM);
				z=(int)((e.getZ()-e.getCollideZ()+camZ)*ZOOM);
				g.drawImage(e.getImage(), x, z, e.getWidth()*ZOOM+x, e.getHeight()*ZOOM+z, 0, 0, e.getWidth()*32, e.getHeight()*32, this);
			}
			g.dispose();
			final Graphics2D g2 = (Graphics2D)g1;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f-MOTION_BLUR));
			g2.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, width, height, this);
			g2.dispose();
		}catch(final Exception exception){ exception.printStackTrace(); }
	}
	public void loadMap(final Map map){
		try{
			tiles=map.getTiles();
			entities.clear();
			addEntity(OneWish.getPlayer());
			if(map.getMusic()!=null
				&&OneWish.getPlayingMusic()!=map.getMusic())OneWish.playMusic(map.getMusic());
			this.map=map;
		}catch(final Exception exception){ exception.printStackTrace(); }
	}
	private void reorderEntityList(){
		Collections.sort(entities, new Comparator<Entity>(){
			public int compare(final Entity a, final Entity b){
				if(a.getZ()==b.getZ())return 0;
				if(a.getZ()<b.getZ())return -1;
				return 1;
			}
		});
	}
	public void render(){ repaint(); }
	public void addEntity(final Entity... e){ for(Entity a : e)entities.add(a); }
	public Map getMap(){ return map; }
	public boolean isFollowingPlayer(){ return followplayer; }
	public void setFollowPlayer(final boolean follow){ followplayer=follow; }
	public Tile getTileAt(final int x, final int z){ return tiles.get(new Point(x, z)); }
	public ArrayList<Entity> getEntities(){ return entities; }
}