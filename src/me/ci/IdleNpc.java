package me.ci;

import java.util.Timer;
import java.util.TimerTask;

public class IdleNpc extends Entity{
	private boolean animated;
	public IdleNpc(final boolean animated){
		setSprite(new Sprite("Bat"));
		this.animated=animated;
		new Timer().schedule(new TimerTask(){
			public void run(){ if(animated)setStep((getStep()+1)%4); }
		}, 200, 200);
	}
	public int getHeight(){ return 1; }
	public int getWidth(){ return 1; }
	public int getCollideX(){ return 0; }
	public int getCollideZ(){ return 0; }
	public boolean isAnimated(){ return animated; }
	public void setAnimated(final boolean animated){ this.animated=animated; }
}