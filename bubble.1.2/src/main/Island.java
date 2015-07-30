package main;

import java.awt.Color;

import processing.core.PApplet;

public class Island extends MovableObject{

	
//	int _stroke;
	int _fill;
//	int _overlapFill;
	
	ColorScheme _scheme;
	
	float margin = 10;  //to determine the spielraum of fitting size of bubble on an island
	float marginCenter = 10; //sets the spielraum of the position of the bubble on the island
	
	boolean isPopped = false;
	boolean _isOverlapped = false;
	
	
	/**
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @param r
	 * @param s
	 */
	public Island(PApplet p, float x, float y, float r, ColorScheme s) {
		super(p, x, y, r);
		
		_scheme = s;
//		_stroke = getCurrentColour();
		_fill = _parent.color(55,186, 132);
//		_overlapFill = _parent.color(100,100,0);
		
	}
	
	
	public void display(){
		_parent.smooth();
		_parent.stroke(getCurrentColour());
		_parent.fill(_fill);
		
		if(_isOverlapped){
			_parent.strokeWeight(margin);
		}
		else {
			_parent.strokeWeight(5);
		}
		
		if(!isPopped){
			_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		}
	}
	
	/**
	 * checks if a bubble fits the size +- margin of an island, and is centered on that island
	 * @param b the Bubble that is possibly on the island
	 * @return true if the bubble is fillign out the island
	 */
	public boolean bubbleFitsOnIsland(Bubble b){
		
		if(b._radius < _radius + margin && b._radius > _radius - margin){
			
			if(isCentredWith(b, marginCenter) ){ 
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public void setPopped(){
		isPopped = true;
	}
	
	public void setIsOverlapped(boolean bool){
		_isOverlapped = bool;
	}
	
	/**
	 * depending on the radius, the island gets a certain colour fitting to the scheme of the bubble
	 * @return
	 */
	private int getCurrentColour(){
		float range = (Bubble.MAX_RADIUS_BUBBLE - Bubble.MIN_RADIUS_BUBBLE) / 3;
		float x = _radius - Bubble.MIN_RADIUS_BUBBLE;

		if(x < range) {
			return _scheme._bSmall;
		}
		else if(x < range*2) {
			return _scheme._bMedium;
		}
		else {
			return _scheme._bLarge;
		}
	}

}
