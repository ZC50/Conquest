package me.ci;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Util{
	public static BufferedImage[] splitImage(final BufferedImage img, final int cols, final int rows){
		final BufferedImage i[] = new BufferedImage[rows*cols];
		final int height = img.getHeight()/rows;
		final int width = img.getWidth()/cols;
		int pos = 0;
		for(int y = 0; y<rows; y++){
			for(int x = 0; x<cols; x++){
				i[pos]=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				final Graphics2D g = i[pos].createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.drawImage(img, 0, 0, width, height, x*width, y*height, (x+1)*width, (y+1)*height, null);
				g.dispose();
				pos++;
			}
		}
		return i;
	}
	public static void runLater(final Runnable run, final long ms){
		new Timer().schedule(new TimerTask(){
			public void run(){ run.run(); }
		}, ms);
	}
	public static Map loadMap(final String file){
		final Map map  = new Map();
		final HashMap<Point,Tile> tiles = new HashMap<>();
		try{
			final BufferedReader in = new BufferedReader(new FileReader(new File(OneWish.FOLDER, file+".map")));
			String line;
			while((line=in.readLine())!=null){
				final String[] data = line.split(";");
				final int x = Integer.valueOf(data[0]);
				final int z = Integer.valueOf(data[1]);
				final String[] layers = data[2].split(",");
				final Tile tile = new Tile(false);
				for(String a : layers)tile.addLayer(Material.getById(Integer.valueOf(a)), false);
				tile.update();
				tiles.put(new Point(x, z), tile);
			}
			in.close();
			map.setTiles(tiles);
		}catch(final Exception exception){ exception.printStackTrace(); }
		return map;
	}
}