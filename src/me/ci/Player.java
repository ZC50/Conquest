package me.ci;

public class Player extends Entity{
	public Player(){ setSprite(new Sprite("Player")); }
	public int getHeight(){ return 2; }
	public int getWidth(){ return 1; }
	public int getCollideX(){ return 0; }
	public int getCollideZ(){ return 1; }
}