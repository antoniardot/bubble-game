package old;
import processing.core.*;


public class MainSketchOld extends PApplet {

	TestBall[] balls =  { 
			  new TestBall(this,100, 400, 20), 
			  new TestBall(this,700, 400, 80) 
			};

			public void setup() {
			  size(560, 400);
			}

			public void draw() {
			  background(51);

			  for (TestBall b : balls) {
			    b.update();
			    b.display();
			    b.checkBoundaryCollision();
			  }
			  
			  balls[0].checkCollision(balls[1]);
			}
	
	

	  
	  
	  
	  
	  
}
