package main;

import processing.core.PApplet;
import processing.core.PVector;

public class MovableObject {

	PApplet _parent;
	
	float _radius;
	PVector _centroid;
	
	
	
	public MovableObject(PApplet p, float x, float y, float r) {
		_parent = p;
		_centroid = new PVector(x,y);
		_radius = r;
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
	
}
