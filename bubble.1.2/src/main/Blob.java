package main;
import processing.core.PApplet;

public class Blob extends MovableObject{

	
	float speed;
	int direction;
	
	
	public Blob(PApplet parent) {
		super(parent,0,0,0);
		_centroid.x = _parent.random(0, _parent.width);
		_centroid.y = _parent.random(_parent.height);
		_radius = _parent.random(10,30);
		
		direction = 1;
		if(_parent.random(1) > .5) {
			direction = -1;
		}

		speed = _parent.random(0,1) * direction;
	}
	
	
	public void display(){
		_parent.noFill();
		_parent.strokeWeight(4);
		_parent.stroke(255,255, 255, 200);
		_parent.ellipse(_centroid.x, _centroid.y, _radius, _radius);
	}
	
	
	public void move(){
		
		int coin = Math.round(_parent.random(1,3));
		
		if(coin < 2) {
			_centroid.x += speed;
			if (_centroid.x > _parent.width) {
				_centroid.x = -5;
			}
		}
		else {
			_centroid.y += speed;
			if (_centroid.y > _parent.height) {
				_centroid.y = -5;
			}
		}
		
	}
}
