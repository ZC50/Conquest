package me.ci;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public enum Material{
	GRASS(0, true),
	ROCK_BASE(1, false),
	ROCK_WALL(2, false);
	private final int id;
	private final BufferedImage img;
	private final boolean walkable;
	private Material(final int id, final boolean walkable){
		this.id=id;
		this.walkable=walkable;
		img=loadImage();
	}
	private BufferedImage loadImage(){
		try{ return ImageIO.read(new File(OneWish.FOLDER, "Material-"+id+".png"));
		}catch(final Exception exception){ exception.printStackTrace(); }
		return null;
	}
	public BufferedImage getImage(){ return img; }
	public int getId(){ return id; }
	public boolean isWalkable(){ return walkable; }
	public static Material getById(final int id){
		for(Material m : values())if(m.getId()==id)return m;
		return null;
	}
}