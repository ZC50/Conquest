package me.ci;

import java.util.Timer;
import java.util.TimerTask;

public class RandomWalker extends Entity{
	public RandomWalker(final long steptime){
		setSprite(new Sprite("Player"));
		new Timer().schedule(new TimerTask(){
			public void run(){
				final int i = (int)(Math.random()*4);
				if(i==0)move(getX()-1, getZ(), Direction.WEST, false);
				else if(i==1)move(getX()+1, getZ(), Direction.EAST, false);
				else if(i==2)move(getX(), getZ()-1, Direction.NORTH, false);
				else move(getX(), getZ()+1, Direction.SOUTH, false);
			}
		}, steptime, steptime);
	}
	public int getHeight(){ return 2; }
	public int getWidth(){ return 1; }
	public int getCollideX(){ return 0; }
	public int getCollideZ(){ return 1; }
}