package me.ci;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Sprite{
	private BufferedImage[] i;
	public Sprite(final String file){
		try{ i=Util.splitImage(ImageIO.read(new File(OneWish.FOLDER, file+".png")), 4, 4);
		}catch(final Exception exception){ exception.printStackTrace(); }
	}
	public BufferedImage getFrame(final Direction d, final int step){
		if(d==Direction.SOUTH)return i[step];
		if(d==Direction.WEST)return i[4+step];
		if(d==Direction.EAST)return i[8+step];
		return i[12+step];
	}
}