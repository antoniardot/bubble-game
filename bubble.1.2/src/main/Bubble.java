package main;
import org.omg.stub.java.rmi._Remote_Stub;

import processing.core.PApplet;

public class Bubble extends MovableObject{
	
	
	MainSketch _pSketch;
	
	int color; //color of the bubble
	int id;
	int age;
	
	int currentSize;
	
	float sparkDistance = 10; //distance between two bubbles from when they throw sparks
	Boolean _drawSparks = false;
	int numberOfSparks = 100;
	int lengthOfSparks = 10;
	
	ColorScheme colors;
	
	int numTrail = 60;
	float trailX[] = new float[numTrail];
	float trailY[] = new float[numTrail];
	int trailColor[] = new int[numTrail];
	
	
	//BubbleParameters
	public static final float MIN_RADIUS_BUBBLE = 60;
	public static final float MAX_RADIUS_BUBBLE = 100;
	
	private static final int RANGE_MAP_X = 25;
	private static final int RANGE_MAP_Y = 30;
	
	/**
	 * 
	 * @param p parent Application
	 * @param xco x coordination of bubble
	 * @param yco y coordination
	 * @param w width of the bubble
	 * @param h height of the bubble
	 */
	public Bubble(MainSketch p, int iD, int agE, float xco, float yco, float radius, ColorScheme s) {
	
		super(p, xco, yco, radius);
		if(radius < MIN_RADIUS_BUBBLE || radius > MAX_RADIUS_BUBBLE) {
			_radius = _parent.random(MIN_RADIUS_BUBBLE, MAX_RADIUS_BUBBLE);
		}
		_centroid.x = xco * _parent.width;
		_centroid.y = yco * _parent.height;
		
		id = iD;
		age = agE;
		colors = s;
		
		_pSketch = p;
	}
	
	
	public Bubble(PApplet p, int iD, int agE, float xco, float yco, ColorScheme s) {
		super(p, xco, yco, 0);
		
		_radius = (MAX_RADIUS_BUBBLE - MIN_RADIUS_BUBBLE) / 2 + MIN_RADIUS_BUBBLE;
		
		_centroid.x = _parent.map(xco, 0, 1, RANGE_MAP_X, _parent.width - RANGE_MAP_X);
		_centroid.y = _parent.map(yco, 0, 1, RANGE_MAP_Y, _parent.height - RANGE_MAP_Y);
//		_centroid.x = xco * _parent.width;
//		_centroid.y = yco * _parent.height;
		id = iD;
		age = agE;
		colors = s;
		
	}
	
	public void update(float xco, float yco, float radius) {
		_centroid.x = _parent.map(xco, 0, 1, RANGE_MAP_X, _parent.width - RANGE_MAP_X);
		_centroid.y = _parent.map(yco, 0, 1, RANGE_MAP_Y, _parent.height - RANGE_MAP_Y);
//		_centroid.x = xco * _parent.width;
//		_centroid.y = yco * _parent.height;
		_radius = radius;
	}
	
	public void update(float xco, float yco) {
		
		_centroid.x = _parent.map(xco, 0, 1, RANGE_MAP_X, _parent.width - RANGE_MAP_X);
		_centroid.y = _parent.map(yco, 0, 1, RANGE_MAP_Y, _parent.height - RANGE_MAP_Y);
//		_centroid.x = xco * _parent.width;
//		_centroid.y = yco * _parent.height;
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
		
		_parent.stroke(colors._bFill);
		_parent.strokeWeight(10);
		_parent.fill(getCurrentColour());
		_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		
		//if bubble is close to other bubble draw sparks
		if(_drawSparks) {
			drawSparks();
		}
	}
	
	//called during loading, leaves a trail of the bubble
	public void displayCrazy(){
		
//		int color = colors.getRandomColor();
		int color = colors._bSmall;
		
		int which = _parent.frameCount % numTrail;
		
		int colorWhich = _parent.frameCount % 15;
		
		if(colorWhich == 0) {
			color = colors.getRandomColor();
		}
		else {
			int w = which - 1 ;
			color = trailColor[w];
		}
		
		trailX[which] = _centroid.x;
		trailY[which] = _centroid.y;
		trailColor[which] = color;
		
		for(int i = 0; i < numTrail; i++){
			
			int index = (which+1 + i) % numTrail;
			_parent.fill(trailColor[index]);
			_parent.noStroke();
			_parent.ellipse(trailX[index], trailY[index], _radius*2, _radius*2);
		}
		
		_parent.stroke(colors._bFill);
		_parent.strokeWeight(10);
		_parent.fill(color);
		_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		
		//if bubble is close to other bubble draw sparks
		if(_drawSparks) {
			drawSparks();
		}
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
	
	private int getCurrentColour(){
		float range = (MAX_RADIUS_BUBBLE - MIN_RADIUS_BUBBLE) / 3;
		float x = _radius - MIN_RADIUS_BUBBLE;

		if(x < range) {
			return colors._bSmall;
		}
		else if(x < range*2) {
			return colors._bMedium;
		}
		else {
			return colors._bLarge;
		}
	}
	
	
	/**
	 * if the bubble is close to otherB then sparks are draw as well
	 * @param otherB
	 */
	public boolean checkForSparks(MovableObject otherB) {
		
		if(isCloseTo(otherB, sparkDistance)){
			_drawSparks = true;
			return true;
		}
		else {
			_drawSparks = false;
			return false;
		}
		
		
	}
	
	
	/**
	 * draws sparks around the bubble
	 */
	private void drawSparks(){
		_parent.stroke(255);
		_parent.noFill();
		_parent.strokeWeight(2);

		for(int i = 0; i < numberOfSparks; i++) {
			
			//get a random distance close to the bubble
			float r = _parent.random(_radius, _radius + 10);
			float theta = _parent.random(0, 360);
			
			//get start position to draw the line aka the spark
			float x = r * _parent.cos(theta)  + _centroid.x; 
			float y = r * _parent.sin(theta)  + _centroid.y;
			
			_parent.beginShape();
			
			for(int j = 0; j < lengthOfSparks; j++) {
			
				_parent.vertex(x, y);

				x += _parent.noise(x * .02f, y* .02f,j*.02f) * 10 - 5;
			    y += _parent.noise(x * .02f, y* .02f, _parent.frameCount * .01f) * 10 - 5;
			 
			  }
			 
			_parent.endShape();
		}
	}
	
	
	
}
