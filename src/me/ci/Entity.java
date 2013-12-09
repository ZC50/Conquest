package me.ci;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.ci.PathFinder.DistanceForumula;
import me.ci.PathFinder.Node;
import me.ci.PathFinder.Path;
import me.ci.PathFinder.PathBuilder;

public abstract class Entity{
	private double x;
	private double z;
	private Direction d = Direction.SOUTH;
	private int step = 2;
	private Sprite sprite;
	private int speed = 250;
	private boolean moving = false;
	private static final int STEP_LENGTH = 25;
	public void move(final double x, final double z, final Direction d, final boolean force){
		if(moving)return;
		if(d!=null)this.d=d;
		if(!force
			&&!canMoveTo((int)x, (int)z))return;
		move(x, z);
	}
	private void move(final double nx, final double nz){
		moving=true;
		final double moveX = (nx-x)/STEP_LENGTH;
		final double moveZ = (nz-z)/STEP_LENGTH;
		final int delay = speed/STEP_LENGTH;
		for(int i = 0; i<STEP_LENGTH; i++){
			final int i2 = i;
			Util.runLater(new Runnable(){
				public void run(){
					if(i2%14==0)step=(step+1)%4;
					x+=moveX;
					z+=moveZ;
					if(i2==STEP_LENGTH-1){
						x=Math.round(x);
						z=Math.round(z);
						moving=false;
						step=2;
					}
					OneWish.renderGame();
				}
			}, i*delay);
		}
	}
	public boolean canMoveTo(final int x, final int z){
		try{ return OneWish.getMap().getTiles().get(new Point(x, z)).isWalkable();
		}catch(final Exception exception){ return false; }
	}
	public void pathFindTo(final int x, final int z){
		new Thread(new Runnable(){
			public void run(){
				try{
					final PathFinder pf = new PathFinder();
					pf.setStart(pf.getNodeAt((int)getX(), (int)getZ()));
					pf.setEnd(pf.getNodeAt(x, z));
					pf.setPathBuilder(new PathBuilder(){
						public ArrayList<Node> getOpenPoints(final Node o){
							final ArrayList<Node> nodes = new ArrayList<>();
							quickNode(getNear(o, 1, 0), nodes, 1);
							quickNode(getNear(o, -1, 0), nodes, 1);
							quickNode(getNear(o, 0, 1), nodes, 1);
							quickNode(getNear(o, 0, -1), nodes, 1);
							return nodes;
						}
						private void quickNode(final Node n, final ArrayList<Node> nodes, final int cost){
							if(n==null)return;
							n.setMoveCost(cost);
							nodes.add(n);
						}
						private Node getNear(final Node o, final int offX, final int offZ){
							final Node n = pf.getNodeAt(o.getX()+offX, o.getY()+offZ);
							final Tile tile = OneWish.getTileAt(n.getX(), n.getY());
							if(tile==null)return null;
							if(!tile.isWalkable())return null;
							return n;
						}
					});
					pf.findPath(DistanceForumula.DISTANCE, -1);
					final Path path = pf.getPath();
					path.getNext();
					while(path.hasNext(false)){
						final Node node = path.getNext();
						move(node.getX(), node.getY(), face(node.getX(), node.getY()), false);
						while(moving){
							try{ Thread.sleep(1);
							}catch(final Exception exception){}
						}
					}
				}catch(final Exception exception){
					if(exception.getMessage().equals("No path found!"))return;
					exception.printStackTrace();
				}
			}
			private Direction face(final int x, final int z){
				final int offX = (int)(getX()-x);
				final int offZ = (int)(getZ()-z);
				if(offX==0){
					if(offZ>0)return Direction.NORTH;
					else return Direction.SOUTH;
				}else{
					if(offX>0)return Direction.WEST;
					else return Direction.EAST;
				}
			}
		}).start();
	}
	public void setDirection(final Direction d){ this.d=d; }
	public void setStep(final int step){ this.step=step; }
	public void setSprite(final Sprite sprite){ this.sprite=sprite; }
	public double getX(){ return x; }
	public double getZ(){ return z; }
	public BufferedImage getImage(){ return sprite.getFrame(d, step); }
	public Direction getDirection(){ return d; }
	public int getStep(){ return step; }
	public boolean isMoving(){ return moving; }
	public void setSpeed(final int speed){ this.speed=speed; }
	public int getSpeed(){ return speed; }
	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getCollideX();
	public abstract int getCollideZ();
}