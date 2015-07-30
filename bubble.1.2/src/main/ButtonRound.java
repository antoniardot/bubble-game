package main;
import processing.core.PApplet;

public class ButtonRound extends MovableObject{
	
	int _color; //color of the bubble
	String _value;

	
	/**
	 * 
	 * @param p parent Application
	 * @param xco x coordination of bubble
	 * @param yco y coordination
	 * @param w width of the bubble
	 * @param h height of the bubble
	 */
	public ButtonRound (PApplet p, float xco, float yco, float radius, int color, String value) {
		super(p, xco, yco, radius);
		_color = color;
		_value = value;
	}
	
	
	public void update(float xco, float yco, float radius) {
		_centroid.x = xco;
		_centroid.y = yco;
		_radius = radius;
	}
	
	public void update(float xco, float yco) {
		_centroid.x = xco;
		_centroid.y = yco;
	}
	
	
	public void display() {
		
		int color = _parent.color(255,0,0);
		_parent.fill(_color);
		_parent.stroke(100);
		_parent.ellipse(_centroid.x, _centroid.y, _radius*2, _radius*2);
		
	}
	
	
	
	
	
}
