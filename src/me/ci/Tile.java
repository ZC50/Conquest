package me.ci;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile{
	private BufferedImage img;
	private final ArrayList<Material> layers = new ArrayList<>();
	public Tile(final int[] m, final boolean update){
		for(int i = 0; i<m.length; i++)addLayer(Material.getById(m[i]), false);
		if(update)update();
	}
	public Material getLayer(final int layer){
		if(layer>=layers.size())return null;
		return layers.get(layer);
	}
	public void update(){
		final BufferedImage i = new BufferedImage(GameScreen.IMAGE_SIZE, GameScreen.IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = i.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(Material m : layers)g.drawImage(m.getImage(), 0, 0, GameScreen.IMAGE_SIZE, GameScreen.IMAGE_SIZE, 0, 0, GameScreen.IMAGE_SIZE, GameScreen.IMAGE_SIZE, null);
		g.dispose();
		img=i;
	}
	public void addLayer(final Material m, final boolean update){
		layers.add(m);
		if(update)update();
	}
	public boolean isWalkable(){
		if(layers.isEmpty())return false;
		for(Material m : layers)if(!m.isWalkable())return false;
		return true;
	}
	public Tile(final boolean update){ if(update)update(); }
	public Material getBottomLayer(){ return getLayer(0); }
	public Material getTopLayer(){ return getLayer(layers.size()); }
	public BufferedImage getImage(){ return img; }
}