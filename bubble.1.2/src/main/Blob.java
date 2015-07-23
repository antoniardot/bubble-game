package main;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

public class Blob extends MovableObject{

	
	int _index;
//	java.awt.Color _fill = new Color(166,60,95);
	java.awt.Color _fill = new Color(58,60,123);
	java.awt.Color _stroke = new Color(196,97,18);
//	ArrayList<Blob> _otherBlobs;
	
	
	public Blob(PApplet parent, int index) {
		super(parent,0,0,0);
		_centroid.x = _parent.random(0, _parent.width);
		_centroid.y = _parent.random(_parent.height);
		_radius = _parent.random(10,30);
		
		_speedX = _parent.random(-0.5f,0.5f);
		_speedY = _parent.random(-0.5f,0.5f); 
		
//		_otherBlobs = otherBlobs;
		_index = index;
	}
	
	
	public void display(){
//		_parent.noFill();
		_parent.fill(_fill.getRed(),_fill.getGreen(),_fill.getBlue());
		_parent.strokeWeight(2);
		_parent.stroke(_stroke.getRed(),_stroke.getGreen(),_stroke.getBlue());
		_parent.ellipse(_centroid.x, _centroid.y, _radius, _radius);
	}
	
	
	public void pop(){
		
	}
	
	
	public void move(){
		
		_centroid.x += _speedX; 
		_centroid.y += _speedY; 
		
		if (_centroid.y + _radius > _parent.height) {
			_centroid.y = _parent.height - _radius;
			_speedY *= -1;
		}
		if (_centroid.y - _radius < 0) {
			_centroid.y = _radius;
			_speedY *= -1;
		}
		
		if (_centroid.x + _radius > _parent.width) {
//			_centroid.x = -5;
			_centroid.x = _parent.width - _radius;
			_speedX *= -1;
		}
		
		if(_centroid.x - _radius < 0) {
			_centroid.x = _radius;
			_speedX *= -1;
		}
		
	}
	
	/**
	 * this checks if any of the Blobs collide with another Blob, if so, change the angle
	 */
//	public void bounceOff() {
//		for(int i = 1 + _index; i < _otherBlobs.length; i++ ) {
//			if (this.collidesWith(_otherBlobs[i])){
//				
//				Blob other = _otherBlobs[i];
//				
//				float dx = other._centroid.x - _centroid.x;
//				float dy = other._centroid.y - _centroid.y;
//				
//				float angle = _parent.atan2(dy, dx);
//		        float targetX = _centroid.x + _parent.cos(angle) * (_radius + other._radius);
//		        float targetY = _centroid.y + _parent.sin(angle) * (_radius + other._radius);
//		        float ax = (targetX - other._centroid.x) * 0.005f;
//		        float ay = (targetY - other._centroid.y) * 0.005f;
//		        _speedX -= ax;
//		        _speedY -= ay;
//		        other._speedX += ax;
//		        other._speedY += ay;
//			}
//		}
//	}
}
