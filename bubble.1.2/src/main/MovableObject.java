package main;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class MovableObject {

	PApplet _parent;
	
	float _radius;
	PVector _centroid;
	int _index;
	
	float _speedX;
	float _speedY;
	
	/**
	 * 
	 * @param p the parent applet
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param r the radius
	 */
	public MovableObject(PApplet p, float x, float y, float r) {
		_parent = p;
		_centroid = new PVector(x,y);
		_radius = r;
		
		_speedX = 0;
		_speedY = 0;
	}
	
	
	public boolean collidesWith(MovableObject other) {
		float deltaZ = _centroid.dist(other._centroid); 
		double mindistance = _radius + other._radius;
		
		if( deltaZ < mindistance) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * this checks if the moveableObject collides with any other given moveable object
	 * if so, those objects, that have speed will bounce of in the other direction
	 */
	public void bounceOff(ArrayList<MovableObject> others) {
		for(int i = 1 + _index; i < others.size(); i++ ) {
			if (this.collidesWith(others.get(i))){
				
				MovableObject other = others.get(i);
				
				float dx = other._centroid.x - _centroid.x;
				float dy = other._centroid.y - _centroid.y;
				
				float angle = _parent.atan2(dy, dx);
		        float targetX = _centroid.x + _parent.cos(angle) * (_radius + other._radius);
		        float targetY = _centroid.y + _parent.sin(angle) * (_radius + other._radius);
		        float ax = (targetX - other._centroid.x) * 0.005f;
		        float ay = (targetY - other._centroid.y) * 0.005f;
		        _speedX -= ax;
		        _speedY -= ay;
		        other._speedX += ax;
		        other._speedY += ay;
			}
		}
	}
	
	
	public void bounceOff(MovableObject other) {
				
		float dx = other._centroid.x - _centroid.x;
		float dy = other._centroid.y - _centroid.y;
		
		float angle = _parent.atan2(dy, dx);
        float targetX = _centroid.x + _parent.cos(angle) * (_radius + other._radius);
        float targetY = _centroid.y + _parent.sin(angle) * (_radius + other._radius);
        float ax = (targetX - other._centroid.x) * 0.005f;
        float ay = (targetY - other._centroid.y) * 0.005f;
        _speedX -= ax;
        _speedY -= ay;
        other._speedX += ax;
        other._speedY += ay;
	}
}
