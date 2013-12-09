package me.ci;

import java.awt.Point;
import java.util.HashMap;

public class Map{
	private HashMap<Point,Tile> tiles = new HashMap<>();
	private Music bgm;
	public void setTiles(final HashMap<Point,Tile> t){ tiles=t; }
	public void setMusic(final Music m){ bgm=m; }
	public HashMap<Point,Tile> getTiles(){ return tiles; }
	public Music getMusic(){ return bgm; }
}