package old;
import processing.core.PApplet;

public class Stripe {

	PApplet parent;
	float x;     // horizontal location of stripe
	  float speed; // speed of stripe
	  float w;     // width of stripe
	  
	  // A boolean variable keeps track of the object's state.
	  boolean mouse; // state of stripe (mouse is over or not?)

	  public Stripe(PApplet p) {
	    
		 parent = p; 
		 // All stripes start at 0
	    x = 0; 
	    // All stripes have a random positive speed
	    speed = parent.random(1); 
	    w = parent.random(10,30);
	    mouse = false;
	  }

	  // Draw stripe
	  void display() {
	    
	    // Boolean variable determines Stripe color.
	    if (mouse) { 
	      parent.fill(255);
	    } else {
	      parent.fill(0);
	    }
	    
	    parent.noStroke();
	    parent.rect(x,0,w,parent.height);
	  }

	  // Move stripe
	  void move() {
	    x += speed;
	    if (x > parent.width + 20) x = -20;
	  }

	// Check to see if point (mx,my) is inside the Stripe.
	  void rollover(int mx, int my) { 
	    // Left edge is x, Right edge is x + w
	    if (mx > x && mx < x + w) {
	      mouse = true;
	    } else {
	      mouse = false;
	    }
	  }
}
