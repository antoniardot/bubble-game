package old;
import main.Bubble;
import processing.core.PApplet;
import processing.core.PVector;
import tsps.Rectangle;

public class TspsPerson {

	int _id;
	int _age;
	PVector _centroid;
	Rectangle _boundingRect;
	
	Bubble _bubble;
	
	PApplet _parent;
	
	public TspsPerson(PApplet p, int id, int age, float xCo, float yCo) {
		_id = id;
		_age = age;
		_centroid = new PVector(xCo, yCo);
		_boundingRect = new Rectangle();
		_parent = p;
		
		_bubble = new main.Bubble(p, id, age,xCo, yCo);
	}

	public void updatePos(float centroidx, float centroidy) {
		_centroid.x = centroidx;
		_centroid.y = centroidy;
		
		_bubble.update(centroidx, centroidy);
	}
	
}
