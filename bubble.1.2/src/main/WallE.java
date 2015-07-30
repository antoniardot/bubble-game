package main;

import processing.core.PApplet;

public class WallE {

	PApplet _p;
	
	float _xPos;
	float _yPos;
	
	int blink;
	boolean isDead;
	
	MovableObject _chargeCircle;
	
	int _currentCharge = 0;
	int _fullCharge = 100;
	
	
	public WallE (PApplet p){
		_p = p;
		
		_xPos = 0;
		_yPos = _p.height/2;
		
		blink = 0;
		isDead = false;
		
		_chargeCircle = new MovableObject(p,_xPos,_yPos,150);
	}
	
	
	
	
	public void display() {
		
		
//		drawBox(75,175,140,70,20);
//		drawBox(150,151,10,15,4);
//		drawBox(100,100,100,50,20);
		drawBox(0,75,140,70,20); //body
		drawBox(75,51,10,15,4);  //neck
		drawBox(25,0,100,50,20); //head
		
		
		//move robot
		if(_xPos < _p.width/2) {
			moveRight();
			
			_p.fill(0);
			_p.stroke(0);
			drawEyes();
		}
		else {
		
			blink++;
			
			if(blink < 100) {
				if(blink % 9 < 5) {
					_p.fill(0);
					_p.stroke(0);
					drawEyes();
				}
				else {
					_p.noFill();
					_p.stroke(0);
					drawEyes();
				}
			}
			else {
				_p.noFill();
				_p.stroke(0);
				drawEyes();
				
				isDead = true;
			}
		}
	}
	
	public void displayCharge(){
		
		_chargeCircle._centroid.x = _xPos + 70;
		_chargeCircle._centroid.y = _yPos + 70;
		
		_xPos = _p.width/2 - 70;
		_yPos = _p.height/2 - 70;
		
		drawBox(0,75,140,70,20); //body
		drawBox(75,51,10,15,4);  //neck
		drawBox(25,0,100,50,20); //head
		
		
		if(isCharged()) {
			_p.fill(0);
			_p.strokeWeight(4);
			_p.stroke(0);
			drawEyes();
		}
		else {
			_p.noFill();
			_p.strokeWeight(4);
			_p.stroke(0);
			drawEyes();
		}
		
		
		_p.noFill();
		_p.stroke(200);
		_p.ellipse(_xPos + 70,_yPos + 70 ,300,300);

		
	}
	
	private void drawBox(int l, int t, int width, int height, int m) {
		float left = l + _xPos;
		float top = t + _yPos;
		int w = width;
		int h = height;
		
		int move = m;
		
		_p.fill(176,146,133);
		_p.stroke(5);
		_p.strokeWeight(4);
		_p.rect(left, top, w, h);
		
		_p.beginShape();
		_p.vertex(left + w, top);
		_p.vertex(left + w + move, top - move);
		_p.vertex(left + move, top - move);
		_p.vertex(left, top);
		_p.endShape();
		
		_p.beginShape();
		_p.vertex(left + 1 + w, top);
		_p.vertex(left + w + move, top - move);
		_p.vertex(left + w + move, top + h - move);
		_p.vertex(left + 1 + w, top + h);
		_p.endShape();
	}
	
	private void drawEyes() {
		
		int l = 50;
		int t = 25;
		
		_p.ellipse(_xPos + l, _yPos + t, 10, 10);
		
		l = 100;
		_p.ellipse(_xPos + l, _yPos + t, 10, 10);
	}
	
	public boolean dead(){
		return isDead;
	}
	
	private void moveRight(){
		_xPos +=1;
		_yPos = _p.sin(_xPos * 0.9f) * 2 + _p.sin(_xPos * 0.05f) * 20 + _p.height/2;
		
		_chargeCircle._centroid.x = _xPos + 70;
		_chargeCircle._centroid.y = _yPos + 70;
		
	}
	
	
	private void moveLeft(){
		_xPos +=1;
		_yPos = _p.sin(_xPos * 0.9f) * 2 + _p.sin(_xPos * 0.05f) * 20 + _p.height/2;
		
		_chargeCircle._centroid.x = _xPos;
		_chargeCircle._centroid.y = _yPos;
		
	}
	
	
	public boolean isCharged(){
		return _currentCharge > _fullCharge;
	}
}
