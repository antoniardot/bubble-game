package main;
import processing.core.PApplet;

public class Bubble extends MovableObject{
	
	int color; //color of the bubble
	float angle;
	int id;
	int age;
	
	//BubbleParameters
	public static final float MIN_RADIUS_BUBBLE = 40;
	public static final float MAX_RADIUS_BUBBLE = 100;
	
	/**
	 * 
	 * @param p parent Application
	 * @param xco x coordination of bubble
	 * @param yco y coordination
	 * @param w width of the bubble
	 * @param h height of the bubble
	 */
	public Bubble(PApplet p, int iD, int agE, float xco, float yco, float radius) {
	
		super(p, xco, xco, radius);
		if(radius < MIN_RADIUS_BUBBLE || radius > MAX_RADIUS_BUBBLE) {
			_radius = _parent.random(MIN_RADIUS_BUBBLE, MAX_RADIUS_BUBBLE);
		}
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		
		id = iD;
		age = agE;
		
		
	}
	
	
	public Bubble(PApplet p, int iD, int agE, float xco, float yco) {
		super(p, xco, xco, 0);
		
		_radius = (MAX_RADIUS_BUBBLE - MIN_RADIUS_BUBBLE) / 2 + MIN_RADIUS_BUBBLE;
		
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		id = iD;
		age = agE;
	}
	
	public void update(float xco, float yco, float radius) {
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		_radius = radius;
	}
	
	public void update(float xco, float yco) {
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
	}
	
	/**
	 * only for testing if mouse is used
	 * @param xco
	 * @param yco
	 */
	public void updateOrignialCoordinates(float xco, float yco) {
		_centroid.x = xco;
		_centroid.y = yco;
		
	}
	
	
	public void display() {
		//calulate radius which is growing and shrinking slightly
//		float d1 = (_parent.sin(angle)*_radius)* 0.3f  + _radius*2;
//		_parent.ellipse(_centroid.x, _centroid.y, d1, d1);
//		_parent.noFill();
		_parent.fill(200, 100);;
		_parent.strokeWeight(4);
		_parent.stroke(0,0, 0);
		_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		
	}
	
	/***
	 * increases or decreases bubble radius by add amount, but not below or above MIN/MAX values
	 * @param added the added amount to the radius
	 */
	public void changeRadius(int added){
		_radius += added;
		
		if(_radius > MAX_RADIUS_BUBBLE){
			_radius = MAX_RADIUS_BUBBLE;
		}
		else if(_radius < MIN_RADIUS_BUBBLE){
			_radius = MIN_RADIUS_BUBBLE;
		}
		
		_parent.println("radius changed: " + _radius);
	}
	
	
	
}
