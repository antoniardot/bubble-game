package main;
import processing.core.PApplet;

public class Bubble extends MovableObject{
	
	int color; //color of the bubble
	float angle;
	
	
	
	/**
	 * 
	 * @param p parent Application
	 * @param xco x coordination of bubble
	 * @param yco y coordination
	 * @param w width of the bubble
	 * @param h height of the bubble
	 */
	Bubble(PApplet p, float xco, float yco, float radius) {
	
		super(p, xco, xco, radius);
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		
		angle=0;
		
	}
	
	
	Bubble(PApplet p, float xco, float yco) {
		super(p, xco, xco, 75);
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		angle=0;
	}
	
	void update(float xco, float yco, float radius) {
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		_radius = radius;
	}
	
	void update(float xco, float yco) {
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
	}
	
	
	void display() {
		
		
		//calulate radius which is growing and shrinking slightly
//		float d1 = (_parent.sin(angle)*_radius)* 0.3f  + _radius*2;
//		_parent.ellipse(_centroid.x, _centroid.y, d1, d1);
		_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		
		angle += 0.03;
	}
	
	
}
