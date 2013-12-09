package me.ci;

public class IdleNpc extends Entity{
	public IdleNpc(){ setSprite(new Sprite("Player")); }
	public int getHeight(){ return 2; }
	public int getWidth(){ return 1; }
	public int getCollideX(){ return 0; }
	public int getCollideZ(){ return 1; }
}